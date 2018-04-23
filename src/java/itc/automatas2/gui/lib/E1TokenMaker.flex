package itc.automatas2.gui.lib;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;

%%

%public
%class E1TokenMaker
%extends AbstractJFlexCTokenMaker
%unicode
%type org.fife.ui.rsyntaxtextarea.Token


%{


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public E1TokenMaker() {
		super();
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { "//", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = Token.NULL;
		switch (initialTokenType) {
			default:
				state = Token.NULL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 */
	private boolean zzRefill() {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}


%}


/* C1.1 - Line terminators. */
NewlineCharacter						= ([\n])

/* C.1.2 - Whitespace. */
Whitespace							= ([\t ]+)

/* C.1.3 - Comments */
InputCharacter							= ([^\n])
InputCharacters						= ({InputCharacter}+)

SingleLineComment						= (("//"|"#")([^/]{InputCharacters}?)?)


/* C.1.5 - Unicode character escape sequences. */
UnicodeEscape1							= ("\\u"{HexDigit}{HexDigit}{HexDigit}{HexDigit})
UnicodeEscape2							= ("\\U"{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit})
UnicodeEscapeSequence					= ({UnicodeEscape1}|{UnicodeEscape2})

/* C1.6 - Identifiers. */
LetterCharacter						= ([A-Za-z])  /* Not accurate - many more Unicode letters, Unicode escapes */
/*
CombiningCharacter						= ()
*/
DecimalDigitCharacter					= ([0-9])
ConnectingCharacter						= ([_\$])
/*
FormattingCharacter						= ()
*/
/*
IdentifierPartCharacter					= ({LetterCharacter}|{DecimalDigitCharacter}|{ConnectingCharacter}|{CombiningCharacter}|{FormattingCharacter})
*/
IdentifierPartCharacter					= ({LetterCharacter}|{DecimalDigitCharacter}|{ConnectingCharacter})
IdentifierPartCharacters					= ({IdentifierPartCharacter}+)
IdentifierStartCharacter					= ({LetterCharacter}|[_\$])
IdentifierOrKeyword						= ({IdentifierStartCharacter}{IdentifierPartCharacters}?)
Identifier							= ("@"?{IdentifierOrKeyword})
/* NOTE:  The two below aren't from the C# spec, but we add them so we can */
/* highlight errors.                                                       */
NonSeparator		= (([^\t\f\r\n\ \(\)\{\}\[\]\;\,\.\=\>\<\!\~\?\:\+\-\*\/\&\|\^\%\"\']|"#"|"\\"))
ErrorIdentifier						= ({NonSeparator}+)

/* C1.8 - Literals. */
BooleanLiteral							= ("true"|"false")
DecimalDigit							= ([0-9])
DecimalDigits							= ({DecimalDigit}+)
IntegerTypeSuffix						= (([uU][lL]?)|([lL][uU]?))
DecimalIntegerLiteral					= ({DecimalDigits}{IntegerTypeSuffix}?)
HexDigit								= ([0-9A-Fa-f])
HexDigits								= ({HexDigit}+)
HexadecimalIntegerLiteral				= ("0"[xX]{HexDigits}{IntegerTypeSuffix}?)
Sign									= ([+\-])
ExponentPart							= ([eE]{Sign}?{DecimalDigits})
RealTypeSuffix							= ([fFdDmM])
RealHelper1							= ({DecimalDigits}"."{DecimalDigits}{ExponentPart}?{RealTypeSuffix}?)
RealHelper2							= ("."{DecimalDigits}{ExponentPart}?{RealTypeSuffix}?)
RealHelper3							= ({DecimalDigits}{ExponentPart}{RealTypeSuffix}?)
RealHelper4							= ({DecimalDigits}{RealTypeSuffix})
RealLiteral							= ({RealHelper1}|{RealHelper2}|{RealHelper3}|{RealHelper4})
ErrorNumberFormat						= (({DecimalIntegerLiteral}|{HexadecimalIntegerLiteral}|{RealLiteral}){NonSeparator}+)

SimpleEscapeSequence					= ("\\"[\'\"\\0abfnrtv])
HexadecimalEscapeSequence				= ("\\x"{HexDigit}{HexDigit}?{HexDigit}?{HexDigit}?)


SingleRegularStringLiteralCharacter		= ([^\"\\\n])
RegularStringLiteralCharacter				= ({SingleRegularStringLiteralCharacter}|{SimpleEscapeSequence}|{HexadecimalEscapeSequence}|{UnicodeEscapeSequence})
RegularStringLiteralCharacters			= ({RegularStringLiteralCharacter}+)
RegularStringLiteral					= ([\"]{RegularStringLiteralCharacters}?[\"])
UnclosedRegularStringLiteral				= ([\"]([\\].|[^\\\"])*[^\"]?)
ErrorRegularStringLiteral				= ({UnclosedRegularStringLiteral}[\"])

/* C.1.9 - Operators and Punctuators. */
OOPHelper1							= (":")
OOPHelper2							= ("+"|"-"|"*"|"/"|"!")
OOPHelper3							= ("="|"<"|">"|"++"|"--")
OOPHelper4							= ("=="|"!="|"<="|">="|"+="|"-="|"*="|"/="|"**=")
OperatorOrPunctuator					= ({OOPHelper1}|{OOPHelper2}|{OOPHelper3}|{OOPHelper4})
/* NOTE:  We distinguish between operators and separators (punctuators), but */
/* the C# spec doesn't, so the stuff below isn't in the spec.                */
Separator								= ([\{\}\[\]\(\)])
Separator2							= ([,;])

%%

<YYINITIAL> {

	/* Keywords */
	"if" |
   	"else" | 
	"while" | 
	"for" |
	"in" |
	"func" | 
	"return" | 
	"null"								{ addToken(Token.RESERVED_WORD); }

	/* Data types. */
	"int" | 
	"real" | 
	"bool" | 
	"string"							{ addToken(Token.DATA_TYPE); }
	
	/* Functions */   
   	"print" | 
   	"println" | 
   	"read"      						{ addToken(Token.FUNCTION); }

	{NewlineCharacter}					{ addNullToken(); return firstToken; }

	{BooleanLiteral}					{ addToken(Token.LITERAL_BOOLEAN); }

	{Identifier}						{ addToken(Token.IDENTIFIER); }

	{Whitespace}						{ addToken(Token.WHITESPACE); }

	/* String/Character Literals. */
	{RegularStringLiteral}				{ addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
	{UnclosedRegularStringLiteral}		{ addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken; }
	{ErrorRegularStringLiteral}			{ addToken(Token.ERROR_STRING_DOUBLE); }

	/* Comments. */
	{SingleLineComment}					{ addToken(Token.COMMENT_EOL); addNullToken(); return firstToken; }

	/* Separators. */
	{Separator}						{ addToken(Token.SEPARATOR); }
	{Separator2}						{ addToken(Token.IDENTIFIER); }

	/* Operators. */
	{OperatorOrPunctuator}				{ addToken(Token.OPERATOR); }

	/* Numbers */
	{DecimalIntegerLiteral}				{ addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
	{RealLiteral}						{ addToken(Token.LITERAL_NUMBER_FLOAT); }
	{ErrorNumberFormat}					{ addToken(Token.ERROR_NUMBER_FORMAT); }


	/* Pretty-much anything else. */
	{ErrorIdentifier}					{ addToken(Token.ERROR_IDENTIFIER); }

	/* Ended with a line not in a string or comment. */
	<<EOF>>							{ addNullToken(); return firstToken; }

	/* Catch any other (unhandled) characters and flag them as bad. */
	.								{ addToken(Token.ERROR_IDENTIFIER); }

}
