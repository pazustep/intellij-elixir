// This is a generated file. Not intended for manual editing.
package org.elixir_lang.psi;

import com.ericsson.otp.erlang.OtpErlangObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElixirCharToken extends Quotable {

  @Nullable
  ElixirEscapedCharacter getEscapedCharacter();

  @Nullable
  ElixirEscapedEOL getEscapedEOL();

  @Nullable
  ElixirQuoteHexadecimalEscapeSequence getQuoteHexadecimalEscapeSequence();

  @NotNull
  OtpErlangObject quote();

}
