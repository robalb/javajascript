package com.robalb;

public enum Tokens {
    /**
     *  IDENTIFIER
     *  an unlimited-length sequence of letters and digits, the first of which must be a letter.
     */
    IDENTIFIER(Token.TV_WORD, ""),

    /**
     * KEYWORDS
     * names reserved by the language, that cannot be used as identifiers
     */
    K_BREAK(Token.TV_FIXED, "break"),
    K_CATCH(Token.TV_FIXED, "catch"),
    K_CLASS(Token.TV_FIXED, "class"),
    K_CONST(Token.TV_FIXED, "const"),
    K_ELSE(Token.TV_FIXED, "else"),
    K_FUNCTION(Token.TV_FIXED, "function"),
    K_IF(Token.TV_FIXED, "if"),
    K_LET(Token.TV_FIXED, "let"),
    K_NEW(Token.TV_FIXED, "new"),
    K_RETURN(Token.TV_FIXED, "return"),
    K_THROW(Token.TV_FIXED, "throw"),
    K_TRY(Token.TV_FIXED, "try"),
    K_VAR (Token.TV_FIXED, "var"),
    K_WHILE(Token.TV_FIXED, "while"),

    /**
     * LITERALS
     * the source code representation of a value of a primitive type
     */
    L_NULL(Token.TV_FIXED, "null"),
    L_NUMBER(Token.TV_NUMBER, ""),
    L_STRING(Token.TV_WORD, ""),
    L_BOOLEAN(Token.TV_BOOLEAN, ""),

    /**
     * SEPARATORS
     * the source code representation of a value of a primitive type
     */
    S_ROUND_LEFT(Token.TV_FIXED, "("),
    S_ROUND_RIGHT(Token.TV_FIXED, ")"),
    S_SQUARE_LEFT(Token.TV_FIXED, "["),
    S_SQUARE_RIGHT(Token.TV_FIXED, "]"),
    S_CURLY_LEFT(Token.TV_FIXED, "{"),
    S_CURLY_RIGHT(Token.TV_FIXED, "}"),
    S_SEMICOLON(Token.TV_FIXED, ";"),
    S_COMMA(Token.TV_FIXED, ","),
    S_DOT(Token.TV_FIXED, "."),
    S_SPREAD(Token.TV_FIXED, "..."),

    /**
     * OPERATORS
     * symbols or words related to operations
     */
    O_PLUS(Token.TV_FIXED, "+"),
    O_MINUS(Token.TV_FIXED, "-"),
    O_DIVISION(Token.TV_FIXED, "/"),
    O_MULTIPLICATION(Token.TV_FIXED, "*"),
    O_REMAINDER(Token.TV_FIXED, "%"),
    O_EXPONENTIAL(Token.TV_FIXED, "**"),
    O_LESS(Token.TV_FIXED, "<"),
    O_GREATER(Token.TV_FIXED, ">"),
    O_LESS_OR_EQUAL(Token.TV_FIXED, "<="),
    O_GERATER_OR_EQUAL(Token.TV_FIXED, ">="),
    O_EQUAL(Token.TV_FIXED, "=="),
    O_NOT_EQUAL(Token.TV_FIXED, "!="),
    O_IDENTITY(Token.TV_FIXED, "==="),
    O_NONIDENTITY(Token.TV_FIXED, "!=="),
    O_B_SHIFT_LEFT(Token.TV_FIXED, ">>"),
    O_B_SHIFT_RIGHT(Token.TV_FIXED, "<<"),
    O_B_AND(Token.TV_FIXED, "&"),
    O_B_OR(Token.TV_FIXED, "|"),
    O_B_XOR(Token.TV_FIXED, "^"),
    O_L_AND(Token.TV_FIXED, "&&"),
    O_L_OR(Token.TV_FIXED, "||"),
    O_INCREMENT(Token.TV_FIXED, "++"),
    O_DECREMENT(Token.TV_FIXED, "--"),
    O_ASSIGN(Token.TV_FIXED, "="),

    /**
     * SEPARATOR / COMMENT
     * tokens ignored by the actual program
     */
    SEPARATOR(Token.TV_FIXED, ""),
    COMMENT(Token.TV_WORD, "");

    public final int valueType;
    public final String fixed;
    Tokens(int valueType, String fixedValue) {
        this.valueType = valueType;
        this.fixed = fixedValue;
    }
}
