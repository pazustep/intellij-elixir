// This is a generated file. Not intended for manual editing.
package org.elixir_lang.psi;

import com.ericsson.otp.erlang.OtpErlangObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ElixirQualifiedAlias extends Quotable {

  @NotNull
  List<ElixirAlias> getAliasList();

  @Nullable
  ElixirAtNumericOperation getAtNumericOperation();

  @Nullable
  ElixirAtom getAtom();

  @Nullable
  ElixirAtomKeyword getAtomKeyword();

  @Nullable
  ElixirCaptureNumericOperation getCaptureNumericOperation();

  @Nullable
  ElixirCharListHeredoc getCharListHeredoc();

  @Nullable
  ElixirCharListLine getCharListLine();

  @Nullable
  ElixirCharToken getCharToken();

  @NotNull
  ElixirDotInfixOperator getDotInfixOperator();

  @Nullable
  ElixirEmptyBlock getEmptyBlock();

  @Nullable
  ElixirInterpolatedCharListSigilHeredoc getInterpolatedCharListSigilHeredoc();

  @Nullable
  ElixirInterpolatedCharListSigilLine getInterpolatedCharListSigilLine();

  @Nullable
  ElixirInterpolatedRegexHeredoc getInterpolatedRegexHeredoc();

  @Nullable
  ElixirInterpolatedRegexLine getInterpolatedRegexLine();

  @Nullable
  ElixirInterpolatedSigilHeredoc getInterpolatedSigilHeredoc();

  @Nullable
  ElixirInterpolatedSigilLine getInterpolatedSigilLine();

  @Nullable
  ElixirInterpolatedStringSigilHeredoc getInterpolatedStringSigilHeredoc();

  @Nullable
  ElixirInterpolatedStringSigilLine getInterpolatedStringSigilLine();

  @Nullable
  ElixirInterpolatedWordsHeredoc getInterpolatedWordsHeredoc();

  @Nullable
  ElixirInterpolatedWordsLine getInterpolatedWordsLine();

  @Nullable
  ElixirList getList();

  @Nullable
  ElixirLiteralCharListSigilHeredoc getLiteralCharListSigilHeredoc();

  @Nullable
  ElixirLiteralCharListSigilLine getLiteralCharListSigilLine();

  @Nullable
  ElixirLiteralRegexHeredoc getLiteralRegexHeredoc();

  @Nullable
  ElixirLiteralRegexLine getLiteralRegexLine();

  @Nullable
  ElixirLiteralSigilHeredoc getLiteralSigilHeredoc();

  @Nullable
  ElixirLiteralSigilLine getLiteralSigilLine();

  @Nullable
  ElixirLiteralStringSigilHeredoc getLiteralStringSigilHeredoc();

  @Nullable
  ElixirLiteralStringSigilLine getLiteralStringSigilLine();

  @Nullable
  ElixirLiteralWordsHeredoc getLiteralWordsHeredoc();

  @Nullable
  ElixirLiteralWordsLine getLiteralWordsLine();

  @Nullable
  ElixirMatchedDotRightOperation getMatchedDotRightOperation();

  @Nullable
  ElixirMatchedNonNumericAtLeftOperation getMatchedNonNumericAtLeftOperation();

  @Nullable
  ElixirMatchedNonNumericCaptureLeftOperation getMatchedNonNumericCaptureLeftOperation();

  @Nullable
  ElixirMatchedNonNumericUnaryLeftOperation getMatchedNonNumericUnaryLeftOperation();

  @Nullable
  ElixirNoParenthesesNoArgumentsUnqualifiedCallOrVariable getNoParenthesesNoArgumentsUnqualifiedCallOrVariable();

  @Nullable
  ElixirNumber getNumber();

  @Nullable
  ElixirStringHeredoc getStringHeredoc();

  @Nullable
  ElixirStringLine getStringLine();

  @Nullable
  ElixirUnaryNumericOperation getUnaryNumericOperation();

  @NotNull
  OtpErlangObject quote();

}
