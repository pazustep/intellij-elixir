// This is a generated file. Not intended for manual editing.
package org.elixir_lang.psi.impl;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.elixir_lang.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElixirNoParenthesesManyPositionalAndMaybeKeywordsArgumentsImpl extends ASTWrapperPsiElement implements ElixirNoParenthesesManyPositionalAndMaybeKeywordsArguments {

  public ElixirNoParenthesesManyPositionalAndMaybeKeywordsArgumentsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ElixirVisitor) ((ElixirVisitor)visitor).visitNoParenthesesManyPositionalAndMaybeKeywordsArguments(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ElixirAlias getAlias() {
    return findChildByClass(ElixirAlias.class);
  }

  @Override
  @Nullable
  public ElixirAtNumericOperation getAtNumericOperation() {
    return findChildByClass(ElixirAtNumericOperation.class);
  }

  @Override
  @Nullable
  public ElixirAtom getAtom() {
    return findChildByClass(ElixirAtom.class);
  }

  @Override
  @Nullable
  public ElixirAtomKeyword getAtomKeyword() {
    return findChildByClass(ElixirAtomKeyword.class);
  }

  @Override
  @Nullable
  public ElixirCaptureNumericOperation getCaptureNumericOperation() {
    return findChildByClass(ElixirCaptureNumericOperation.class);
  }

  @Override
  @Nullable
  public ElixirCharListHeredoc getCharListHeredoc() {
    return findChildByClass(ElixirCharListHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirCharListLine getCharListLine() {
    return findChildByClass(ElixirCharListLine.class);
  }

  @Override
  @Nullable
  public ElixirCharToken getCharToken() {
    return findChildByClass(ElixirCharToken.class);
  }

  @Override
  @Nullable
  public ElixirEmptyBlock getEmptyBlock() {
    return findChildByClass(ElixirEmptyBlock.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedCharListSigilHeredoc getInterpolatedCharListSigilHeredoc() {
    return findChildByClass(ElixirInterpolatedCharListSigilHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedCharListSigilLine getInterpolatedCharListSigilLine() {
    return findChildByClass(ElixirInterpolatedCharListSigilLine.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedRegexHeredoc getInterpolatedRegexHeredoc() {
    return findChildByClass(ElixirInterpolatedRegexHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedRegexLine getInterpolatedRegexLine() {
    return findChildByClass(ElixirInterpolatedRegexLine.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedSigilHeredoc getInterpolatedSigilHeredoc() {
    return findChildByClass(ElixirInterpolatedSigilHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedSigilLine getInterpolatedSigilLine() {
    return findChildByClass(ElixirInterpolatedSigilLine.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedStringSigilHeredoc getInterpolatedStringSigilHeredoc() {
    return findChildByClass(ElixirInterpolatedStringSigilHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedStringSigilLine getInterpolatedStringSigilLine() {
    return findChildByClass(ElixirInterpolatedStringSigilLine.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedWordsHeredoc getInterpolatedWordsHeredoc() {
    return findChildByClass(ElixirInterpolatedWordsHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirInterpolatedWordsLine getInterpolatedWordsLine() {
    return findChildByClass(ElixirInterpolatedWordsLine.class);
  }

  @Override
  @Nullable
  public ElixirList getList() {
    return findChildByClass(ElixirList.class);
  }

  @Override
  @Nullable
  public ElixirLiteralCharListSigilHeredoc getLiteralCharListSigilHeredoc() {
    return findChildByClass(ElixirLiteralCharListSigilHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirLiteralCharListSigilLine getLiteralCharListSigilLine() {
    return findChildByClass(ElixirLiteralCharListSigilLine.class);
  }

  @Override
  @Nullable
  public ElixirLiteralRegexHeredoc getLiteralRegexHeredoc() {
    return findChildByClass(ElixirLiteralRegexHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirLiteralRegexLine getLiteralRegexLine() {
    return findChildByClass(ElixirLiteralRegexLine.class);
  }

  @Override
  @Nullable
  public ElixirLiteralSigilHeredoc getLiteralSigilHeredoc() {
    return findChildByClass(ElixirLiteralSigilHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirLiteralSigilLine getLiteralSigilLine() {
    return findChildByClass(ElixirLiteralSigilLine.class);
  }

  @Override
  @Nullable
  public ElixirLiteralStringSigilHeredoc getLiteralStringSigilHeredoc() {
    return findChildByClass(ElixirLiteralStringSigilHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirLiteralStringSigilLine getLiteralStringSigilLine() {
    return findChildByClass(ElixirLiteralStringSigilLine.class);
  }

  @Override
  @Nullable
  public ElixirLiteralWordsHeredoc getLiteralWordsHeredoc() {
    return findChildByClass(ElixirLiteralWordsHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirLiteralWordsLine getLiteralWordsLine() {
    return findChildByClass(ElixirLiteralWordsLine.class);
  }

  @Override
  @Nullable
  public ElixirMatchedDotLeftOperation getMatchedDotLeftOperation() {
    return findChildByClass(ElixirMatchedDotLeftOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedDotRightOperation getMatchedDotRightOperation() {
    return findChildByClass(ElixirMatchedDotRightOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedHatLeftOperation getMatchedHatLeftOperation() {
    return findChildByClass(ElixirMatchedHatLeftOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedHatRightOperation getMatchedHatRightOperation() {
    return findChildByClass(ElixirMatchedHatRightOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedMultiplicationRightOperation getMatchedMultiplicationRightOperation() {
    return findChildByClass(ElixirMatchedMultiplicationRightOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedNonNumericAtLeftOperation getMatchedNonNumericAtLeftOperation() {
    return findChildByClass(ElixirMatchedNonNumericAtLeftOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedNonNumericAtRightOperation getMatchedNonNumericAtRightOperation() {
    return findChildByClass(ElixirMatchedNonNumericAtRightOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedNonNumericCaptureLeftOperation getMatchedNonNumericCaptureLeftOperation() {
    return findChildByClass(ElixirMatchedNonNumericCaptureLeftOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedNonNumericCaptureRightOperation getMatchedNonNumericCaptureRightOperation() {
    return findChildByClass(ElixirMatchedNonNumericCaptureRightOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedNonNumericUnaryLeftOperation getMatchedNonNumericUnaryLeftOperation() {
    return findChildByClass(ElixirMatchedNonNumericUnaryLeftOperation.class);
  }

  @Override
  @Nullable
  public ElixirMatchedNonNumericUnaryRightOperation getMatchedNonNumericUnaryRightOperation() {
    return findChildByClass(ElixirMatchedNonNumericUnaryRightOperation.class);
  }

  @Override
  @NotNull
  public List<ElixirNoParenthesesExpression> getNoParenthesesExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ElixirNoParenthesesExpression.class);
  }

  @Override
  @Nullable
  public ElixirNoParenthesesKeywords getNoParenthesesKeywords() {
    return findChildByClass(ElixirNoParenthesesKeywords.class);
  }

  @Override
  @Nullable
  public ElixirNoParenthesesNoArgumentsCall getNoParenthesesNoArgumentsCall() {
    return findChildByClass(ElixirNoParenthesesNoArgumentsCall.class);
  }

  @Override
  @Nullable
  public ElixirNoParenthesesNoArgumentsUnqualifiedCallOrVariable getNoParenthesesNoArgumentsUnqualifiedCallOrVariable() {
    return findChildByClass(ElixirNoParenthesesNoArgumentsUnqualifiedCallOrVariable.class);
  }

  @Override
  @Nullable
  public ElixirNumber getNumber() {
    return findChildByClass(ElixirNumber.class);
  }

  @Override
  @Nullable
  public ElixirQualifiedAlias getQualifiedAlias() {
    return findChildByClass(ElixirQualifiedAlias.class);
  }

  @Override
  @Nullable
  public ElixirStringHeredoc getStringHeredoc() {
    return findChildByClass(ElixirStringHeredoc.class);
  }

  @Override
  @Nullable
  public ElixirStringLine getStringLine() {
    return findChildByClass(ElixirStringLine.class);
  }

  @Override
  @Nullable
  public ElixirUnaryNumericOperation getUnaryNumericOperation() {
    return findChildByClass(ElixirUnaryNumericOperation.class);
  }

  @NotNull
  public OtpErlangObject[] quoteArguments() {
    return ElixirPsiImplUtil.quoteArguments(this);
  }

}
