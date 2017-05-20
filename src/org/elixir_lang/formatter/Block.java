package org.elixir_lang.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.Function;
import org.elixir_lang.ElixirLanguage;
import org.elixir_lang.psi.ElixirTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static com.intellij.openapi.util.Pair.pair;

/**
 * @note MUST implement {@link BlockEx} or language-specific indent settings will NOT be used and only the generic ones
 *   will be used.
 */
public class Block extends AbstractBlock implements BlockEx {
    private static final TokenSet WHITESPACE_TOKEN_SET =
            TokenSet.create(ElixirTypes.EOL, TokenType.WHITE_SPACE, ElixirTypes.SIGNIFICANT_WHITE_SPACE);

    private final SpacingBuilder spacingBuilder;
    private final Indent indent;
    @Nullable
    private final Wrap childrenWrap;

    public Block(@NotNull ASTNode node,
                 @Nullable Wrap wrap,
                 @Nullable Alignment alignment,
                 @NotNull SpacingBuilder spacingBuilder) {
        this(node, wrap, alignment, spacingBuilder, Indent.getNoneIndent());
    }

    public Block(@NotNull ASTNode node,
                 @Nullable Wrap wrap,
                 @Nullable Alignment alignment,
                 @NotNull SpacingBuilder spacingBuilder,
                 @NotNull Indent indent) {
        this(node, wrap, alignment, spacingBuilder, indent, null);
    }

    public Block(@NotNull ASTNode node,
                 @Nullable Wrap wrap,
                 @Nullable Alignment alignment,
                 @NotNull SpacingBuilder spacingBuilder,
                 @NotNull Indent indent,
                 @Nullable Wrap childrenWrap) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
        this.indent = indent;
        this.childrenWrap = childrenWrap;
    }

    @Override
    protected List<com.intellij.formatting.Block> buildChildren() {
        List<com.intellij.formatting.Block> blocks;
        // shared so that children are all aligned as alignment is shared based on sharing same alignment instance
        IElementType elementType = myNode.getElementType();

        if (elementType == ElixirTypes.STAB_OPERATION) {
            blocks = buildStabOperationChildren(myNode, childrenWrap);
        } else if (isOperationElementType(elementType)) {
            blocks = buildOperationChildren();
        } else if (isUnmatchedCallElementType(elementType)) {
            blocks = buildUnmatchedCallChildren();
        } else {
            final Alignment childrenAlignment = Alignment.createAlignment();

            blocks = buildChildren(
                    myNode,
                    (childBlockListPair) -> {
                        ASTNode child = childBlockListPair.first;
                        List<com.intellij.formatting.Block> lambdaBlocks = childBlockListPair.second;

                        IElementType childElementType = child.getElementType();

                        if (childElementType == ElixirTypes.END_OF_EXPRESSION) {
                            lambdaBlocks.addAll(
                                    buildEndOfExpressionChildren(child, childrenAlignment, Indent.getNoneIndent())
                            );
                        } else if (childElementType == ElixirTypes.STAB) { // child of ANONYMOUS_FUNCTION
                            lambdaBlocks.addAll(buildStabChildren((CompositeElement) child));
                        } else {
                            Block block = new Block(
                                    child,
                                    Wrap.createWrap(WrapType.NONE, false),
                                    childrenAlignment,
                                    spacingBuilder
                            );
                            lambdaBlocks.add(block);
                        }

                        return lambdaBlocks;
                    }
            );
        }

        return blocks;
    }

    @NotNull
    @Override
    public Indent getIndent() {
        return indent;
    }

    /**
     * Must be set or Elixir settings won't be used
     * @return current block's language (is used to decide on what code style settings should be used for it)
     */
    @NotNull
    @Override
    public Language getLanguage() {
        return ElixirLanguage.INSTANCE;
    }

    /**
     * Returns a spacing object indicating what spaces and/or line breaks are added between two
     * specified children of this block.
     *
     * @param child1 the first child for which spacing is requested;
     *               <code>null</code> if given <code>'child2'</code> block is the first document block
     * @param child2 the second child for which spacing is requested.
     * @return the spacing instance, or null if no special spacing is required. If null is returned,
     * the formatter does not insert or delete spaces between the child blocks, but may insert
     * a line break if the line wraps at the position between the child blocks.
     * @see Spacing#createSpacing(int, int, int, boolean, int)
     * @see Spacing#getReadOnlySpacing()
     */
    @Nullable
    @Override
    public Spacing getSpacing(@Nullable com.intellij.formatting.Block child1,
                              @NotNull com.intellij.formatting.Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    /**
     * Returns true if the specified block may not contain child blocks. Used as an optimization
     * to avoid building the complete formatting model through calls to {@link #getSubBlocks()}.
     *
     * @return true if the block is a leaf block and may not contain child blocks, false otherwise.
     */
    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    @NotNull
    private List<com.intellij.formatting.Block> buildOperationChildren() {
        return buildChildren(
                myNode,
                (childBlockListPair) -> {
                    ASTNode child = childBlockListPair.first;
                    List<com.intellij.formatting.Block> blocks = childBlockListPair.second;
                    /* Move the operator token ASTNode up, out of the operator rule ASTNode as the operator rule ASTNode
                       is only there to consume EOLs around the operator token ASTNode and EOLs will ignored */
                    if (isOperatorRuleElementType(child.getElementType())) {
                        blocks.addAll(buildOperatorRuleChildren(child));
                    } else {
                        blocks.add(buildChild(child, Alignment.createAlignment()));
                    }

                    return blocks;
                }
        );
    }

    private @NotNull List<com.intellij.formatting.Block> buildChildren(
            @NotNull ASTNode node,
            @NotNull Function<Pair<ASTNode, List<com.intellij.formatting.Block>>,
                              List<com.intellij.formatting.Block>> buildChildBlocks) {
        List<com.intellij.formatting.Block> blocks = new ArrayList<>();

        ASTNode child = node.getFirstChildNode();

        while (child != null) {
            if (shouldBuildBlock(child)) {
                blocks = buildChildBlocks.fun(pair(child, blocks));
            }

            child = child.getTreeNext();
        }

        return blocks;
    }

    @NotNull
    private List<com.intellij.formatting.Block> buildOperatorRuleChildren(ASTNode operatorRuleNode) {
        return buildChildren(
                operatorRuleNode,
                (childBlockListPair) -> {
                    ASTNode child = childBlockListPair.first;
                    List<com.intellij.formatting.Block> blocks = childBlockListPair.second;

                    blocks.add(buildChild(child, Alignment.createAlignment()));

                    return blocks;
                }
        );
    }

    @NotNull
    private Block buildChild(@NotNull ASTNode child, @NotNull Alignment alignment) {
        return buildChild(child, alignment, Indent.getNoneIndent());
    }

    @NotNull
    private Block buildChild(@NotNull ASTNode child, @NotNull Alignment alignment, @NotNull Indent indent) {
        return buildChild(child,
                Wrap.createWrap(WrapType.NONE, false),
                alignment,
                indent
        );
    }

    @NotNull
    private Block buildChild(@NotNull ASTNode child,
                             @NotNull Wrap wrap,
                             @NotNull Alignment alignment,
                             @NotNull Indent indent) {
        return buildChild(
                child,
                wrap,
                alignment,
                indent,
                null
        );
    }

    @NotNull Block buildChild(@NotNull ASTNode child,
                              @NotNull Wrap wrap,
                              @NotNull Alignment alignment,
                              @NotNull Indent indent,
                              @Nullable Wrap childrenWrap) {
        return new Block(
                child,
                wrap,
                alignment,
                spacingBuilder,
                indent,
                childrenWrap
        );
    }

    @NotNull
    private List<com.intellij.formatting.Block> buildUnmatchedCallChildren() {
        return buildChildren(
                myNode,
                (childBlockListPair) -> {
                    ASTNode child = childBlockListPair.first;
                    List<com.intellij.formatting.Block> blocks = childBlockListPair.second;

                    /* the elements in the doBlock.stab must be direct children of the call, so that they can be
                       indented relative to parent */
                    if (child.getElementType() == ElixirTypes.DO_BLOCK) {
                        blocks.addAll(buildDoBlockChildren(child));
                    } else {
                        blocks.add(buildChild(child, Alignment.createAlignment()));
                    }

                    return blocks;
                }
        );
    }

    private static boolean shouldBuildBlock(@NotNull ASTNode child) {
        return shouldBuildBlock(child.getElementType());
    }

    private static boolean shouldBuildBlock(@NotNull IElementType childElementType) {
        return !WHITESPACE_TOKEN_SET.contains(childElementType);
    }

    /**
     * Builds doBlock DO, stab.*, and END as siblings, so they can all be indented relative to the parent unmatched call
     * from {@link #buildUnmatchedCallChildren()}
     *
     * @param doBlock doBlock that is a child of an unmatched call, but needs to be flattened for formatting
     * @return the flattened children of the doBlock: DO, stab.*, and END.
     */
    @NotNull
    private List<com.intellij.formatting.Block> buildDoBlockChildren(@NotNull ASTNode doBlock) {
        return buildChildren(
                doBlock,
                (childBlockListPair) -> {
                    ASTNode child = childBlockListPair.first;
                    IElementType childElementType = child.getElementType();
                    List<com.intellij.formatting.Block> blocks = childBlockListPair.second;

                    if (childElementType == ElixirTypes.END_OF_EXPRESSION) {
                        blocks.addAll(
                                buildEndOfExpressionChildren(
                                        child,
                                        Alignment.createAlignment(),
                                        Indent.getNoneIndent()
                                )
                        );
                    } else if (childElementType == ElixirTypes.STAB) {
                        blocks.addAll(buildStabChildren((CompositeElement) child));
                    } else {
                        Alignment childAlignment;

                        if (childElementType == ElixirTypes.END) {
                            childAlignment = myAlignment;
                        } else {
                            childAlignment = Alignment.createAlignment();
                        }

                        blocks.add(buildChild(child, childAlignment));
                    }

                    return blocks;
                }
        );
    }

    /**
     * Builds endOfExpression.*.  Importantly, it separates out the EOLs, which are whitespace from the comments that
     * may be interlaced
     */
    @NotNull
    private List<com.intellij.formatting.Block> buildEndOfExpressionChildren(@NotNull ASTNode endOfExpression,
                                                                             @NotNull Alignment childAlignment,
                                                                             @NotNull Indent childIndent) {
        return buildChildren(
                endOfExpression,
                childBlockListPair -> {
                    ASTNode child = childBlockListPair.first;
                    List<com.intellij.formatting.Block> blocks = childBlockListPair.second;

                    blocks.add(buildChild(child, childAlignment, childIndent));

                    return blocks;
                }
        );
    }

    /**
     * Builds stab.stabBody.*
     *
     * @param stabBody a child of a `stab` in `doBlock`
     * @param childWrap wrap for all child blocks in `stabBody`
     * @param childAlignment alignment to use for all child blocks of `stabBody`
     * @param childIndent indent to use for all child blocks of the `stabBody`
     * @return children of stabBody that should be aligned together with the same indent
     */
    @NotNull
    private List<com.intellij.formatting.Block> buildStabBodyChildren(@NotNull ASTNode stabBody,
                                                                      @NotNull Wrap childWrap,
                                                                      @NotNull Alignment childAlignment,
                                                                      @NotNull Indent childIndent) {
        return buildChildren(
                stabBody,
                (childBlockListPair) -> {
                    ASTNode child = childBlockListPair.first;
                    IElementType childElementType = child.getElementType();
                    List<com.intellij.formatting.Block> blockList = childBlockListPair.second;

                    if (childElementType == ElixirTypes.END_OF_EXPRESSION) {
                        blockList.addAll(buildEndOfExpressionChildren(child, childAlignment, childIndent));
                    } else {
                        blockList.add(buildChild(child, childWrap, childAlignment, childIndent));
                    }

                    return blockList;
                }
        );
    }

    /**
     * Builds stab.*
     *
     * @param stab a child of a `doBlock`
     * @return children of stab that should be aligned together and indented normal relative to the call with the
     *   doBlock.
     */
    @NotNull
    private List<com.intellij.formatting.Block> buildStabChildren(@NotNull CompositeElement stab) {
        /* all children share the same alignment as expressions inside a doBlock above the stab are assumed to be
           aligned on the left-side */
        Alignment childAlignment = Alignment.createAlignment();

        Indent childIndent = Indent.getNormalIndent(true);
        WrapType stabOperationWrapType;

        if (stab.countChildren(TokenSet.create(ElixirTypes.STAB_OPERATION)) > 1) {
            stabOperationWrapType = WrapType.ALWAYS;
        } else {
            stabOperationWrapType = WrapType.NORMAL;
        }

        Wrap stabOperationWrap = Wrap.createWrap(stabOperationWrapType, true);
        Wrap stabBodyChildrenWrap = Wrap.createChildWrap(stabOperationWrap, WrapType.CHOP_DOWN_IF_LONG, true);

        return buildChildren(
                stab,
                (childBlockListPair) -> {
                    ASTNode child = childBlockListPair.first;
                    IElementType childElementType = child.getElementType();
                    List<com.intellij.formatting.Block> blocks = childBlockListPair.second;

                    if (childElementType == ElixirTypes.END_OF_EXPRESSION) {
                        blocks.addAll(buildEndOfExpressionChildren(child, childAlignment, childIndent));
                    } else if (childElementType == ElixirTypes.STAB_BODY) {
                        blocks.addAll(
                                buildStabBodyChildren(
                                        child,
                                        Wrap.createWrap(WrapType.ALWAYS, true),
                                        childAlignment,
                                        childIndent
                                )
                        );
                    } else {
                        blocks.add(
                                buildChild(child, stabOperationWrap, childAlignment, childIndent, stabBodyChildrenWrap)
                        );
                    }

                    return blocks;
                }
        );
    }

    @NotNull
    private List<com.intellij.formatting.Block> buildStabOperationChildren(
            @NotNull ASTNode stabOperation,
            @NotNull Wrap stabBodyChildrenWrap
    ) {
       return buildChildren(
               stabOperation,
               (childBlockListPair) -> {
                   ASTNode child = childBlockListPair.first;
                   IElementType childElementType = child.getElementType();
                   List<com.intellij.formatting.Block> blockList = childBlockListPair.second;

                   if (childElementType == ElixirTypes.STAB_BODY) {
                       blockList.addAll(
                               buildStabBodyChildren(
                                       child,
                                       stabBodyChildrenWrap,
                                       Alignment.createAlignment(),
                                       Indent.getNormalIndent(true)
                               )
                       );
                   } else if (childElementType == ElixirTypes.STAB_INFIX_OPERATOR) {
                       blockList.addAll(buildOperatorRuleChildren(child));
                   } else {
                       blockList.add(
                               buildChild(
                                       child,
                                       Alignment.createAlignment()
                               )
                       );
                   }

                   return blockList;
               }
       );
    }

    private static final Map<IElementType, Boolean> isOperationByElementType = new IdentityHashMap<>();

    static {
        isOperationByElementType.put(ElixirTypes.MATCHED_ADDITION_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.MATCHED_COMPARISON_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.MATCHED_IN_MATCH_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.MATCHED_MATCH_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.MATCHED_MULTIPLICATION_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.MATCHED_RELATIONAL_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.MATCHED_UNARY_NON_NUMERIC_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNARY_NUMERIC_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_ADDITION_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_COMPARISON_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_IN_MATCH_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_MATCH_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_MULTIPLICATION_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_RELATIONAL_OPERATION, true);
        isOperationByElementType.put(ElixirTypes.UNMATCHED_UNARY_NON_NUMERIC_OPERATION, true);
    }

    private static boolean isOperationElementType(IElementType elementType) {
        return isOperationByElementType.containsKey(elementType);
    }

    private static final Map<IElementType, Boolean> isOperatorRuleByElementType = new IdentityHashMap<>();

    static {
        isOperatorRuleByElementType.put(ElixirTypes.ADDITION_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.COMPARISON_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.IN_MATCH_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.MATCH_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.MULTIPLICATION_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.RELATIONAL_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.STAB_INFIX_OPERATOR, true);
        isOperatorRuleByElementType.put(ElixirTypes.UNARY_PREFIX_OPERATOR, true);
    }

    private static boolean isOperatorRuleElementType(IElementType elementType) {
        return isOperatorRuleByElementType.containsKey(elementType);
    }

    private static final Map<IElementType, Boolean> isUnmatchedCallByElementType = new IdentityHashMap<>();

    static {
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_AT_UNQUALIFIED_NO_PARENTHESES_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_DOT_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_QUALIFIED_NO_ARGUMENTS_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_QUALIFIED_NO_PARENTHESES_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_QUALIFIED_PARENTHESES_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_UNQUALIFIED_NO_ARGUMENTS_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_UNQUALIFIED_NO_PARENTHESES_CALL, true);
        isUnmatchedCallByElementType.put(ElixirTypes.UNMATCHED_UNQUALIFIED_PARENTHESES_CALL, true);
    }

    private static boolean isUnmatchedCallElementType(IElementType elementType) {
        return isUnmatchedCallByElementType.containsKey(elementType);
    }
}
