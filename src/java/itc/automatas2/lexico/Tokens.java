package itc.automatas2.lexico;

/**
 * Catálogo de tokens e IDs.
 */
public class Tokens {

    //Palabras reservadas
    public static final int T_IF = 1;
    public static final int T_ELSE = 2;
    public static final int T_WHILE = 3;
    public static final int T_FOR = 4;
    public static final int T_IN = 5;
    public static final int T_FUNC = 6;
    public static final int T_RETURN = 7;
    public static final int T_TRUE = 8;
    public static final int T_FALSE = 9;
    public static final int T_NULL = 10;
    public static final int T_INT = 11;
    public static final int T_REAL = 12;
    public static final int T_BOOL = 13;
    public static final int T_STRING = 14;
    public static final int T_PRINT = 15;
    public static final int T_PRINTLN = 16;
    public static final int T_READ = 17;

    //Identificadores y constantes
    public static final int T_VAR = 18;
    public static final int T_FUN = 19;
    public static final int T_INT_CONST = 20;
    public static final int T_REAL_CONST = 21;
    public static final int T_BOOL_CONST = 22;
    public static final int T_STR_CONST = 23;

    //Símbolos y operadores
    public static final int T_COLON = 24;
    public static final int T_SEMICOLON = 25;
    public static final int T_LPAREN = 26;
    public static final int T_RPAREN = 27;
    public static final int T_LBRACE = 28;
    public static final int T_RBRACE = 29;
    public static final int T_LBRACKET = 30;
    public static final int T_RBRACKET = 31;
    public static final int T_LTE = 32;
    public static final int T_GTE = 33;
    public static final int T_NE = 34;
    public static final int T_LT = 35;
    public static final int T_GT = 36;
    public static final int T_EQ = 37;
    public static final int T_ASSIGN = 38;
    public static final int T_DEC = 39;
    public static final int T_INC = 40;
    public static final int T_NOT = 41;
    public static final int T_PLUS = 42;
    public static final int T_MINUS = 43;
    public static final int T_STAR = 44;
    public static final int T_DIV = 45;
    public static final int T_RANGE = 46;
    public static final int T_COMMA = 47;

    public static final String[] nombres = {
            "UNDEFINED",
            "T_IF",
            "T_ELSE",
            "T_WHILE",
            "T_FOR",
            "T_IN",
            "T_FUNC",
            "T_RETURN",
            "T_TRUE",
            "T_FALSE",
            "T_NULL",
            "T_INT",
            "T_REAL",
            "T_BOOL",
            "T_STRING",
            "T_PRINT",
            "T_PRINTLN",
            "T_READ",
            "T_VAR",
            "T_FUN",
            "T_INT_CONST",
            "T_REAL_CONST",
            "T_BOOL_CONST",
            "T_STR_CONST",
            "T_COLON",
            "T_SEMICOLON",
            "T_LPAREN",
            "T_RPAREN",
            "T_LBRACE",
            "T_RBRACE",
            "T_LBRACKET",
            "T_RBRACKET",
            "T_LTE",
            "T_GTE",
            "T_NE",
            "T_LT",
            "T_GT",
            "T_EQ",
            "T_ASSIGN",
            "T_DEC",
            "T_INC",
            "T_NOT",
            "T_PLUS",
            "T_MINUS",
            "T_STAR",
            "T_DIV",
            "T_RANGE",
            "T_COMMA"
    };
}