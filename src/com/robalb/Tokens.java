package com.robalb;

public enum Tokens {
    /**
     *  IDENTIFIER
     *  an unlimited-length sequence of letters and digits, the first of which must be a letter.
     */
    IDENTIFIER(Token.TT_WORD, ""),

    /**
     * KEYWORDS
     * names reserved by the language, that cannot be used as identifiers
     */
    K_BREAK(Token.TT_FIXED, "break"),
    K_CATCH(Token.TT_FIXED, "catch"),
    K_CLASS(Token.TT_FIXED, "class"),
    K_CONST(Token.TT_FIXED, "const"),
    K_ELSE(Token.TT_FIXED, "else"),
    K_FUNCTION(Token.TT_FIXED, "function"),
    K_IF(Token.TT_FIXED, "if"),
    K_LET(Token.TT_FIXED, "let"),
    K_NEW(Token.TT_FIXED, "new"),
    K_RETURN(Token.TT_FIXED, "return"),
    K_THROW(Token.TT_FIXED, "throw"),
    K_TRY(Token.TT_FIXED, "try"),
    K_VAR (Token.TT_FIXED, "var"),
    K_WHILE(Token.TT_FIXED, "while"),

    /**
     * LITERALS
     * the source code representation of a value of a primitive type
     */
    L_NULL(Token.TT_FIXED, "null"),
    L_NUMBER(Token.TT_NUMBER, ""),
    L_STRING(Token.TT_WORD, ""),
    L_BOOLEAN(Token.TT_BOOLEAN, ""),

    /**
     * SEPARATORS
     * the source code representation of a value of a primitive type
     */
    S_ROUND_LEFT(Token.TT_FIXED, "("),
    S_ROUND_RIGHT(Token.TT_FIXED, ")"),
    S_SQUARE_LEFT(Token.TT_FIXED, "["),
    S_SQUARE_RIGHT(Token.TT_FIXED, "]"),
    S_CURLY_LEFT(Token.TT_FIXED, "{"),
    S_CURLY_RIGHT(Token.TT_FIXED, "}"),
    S_SEMICOLON(Token.TT_FIXED, ";"),
    S_COMMA(Token.TT_FIXED, ","),
    S_DOT(Token.TT_FIXED, "."),
    S_SPREAD(Token.TT_FIXED, "..."),

    /**
     * OPERATORS
     * symbols or words related to operations
     */
    O_PLUS(Token.TT_FIXED, "+"),
    O_MINUS(Token.TT_FIXED, "-"),
    O_DIVISION(Token.TT_FIXED, "/"),
    O_MULTIPLICATION(Token.TT_FIXED, "*"),
    O_REMAINDER(Token.TT_FIXED, "%"),
    O_EXPONENTIAL(Token.TT_FIXED, "**"),
    O_LESS(Token.TT_FIXED, "<"),
    O_GREATER(Token.TT_FIXED, ">"),
    O_LESS_OR_EQUAL(Token.TT_FIXED, "<="),
    O_GERATER_OR_EQUAL(Token.TT_FIXED, ">="),
    O_EQUAL(Token.TT_FIXED, "=="),
    O_NOT_EQUAL(Token.TT_FIXED, "!="),
    O_IDENTITY(Token.TT_FIXED, "==="),
    O_NONIDENTITY(Token.TT_FIXED, "!=="),
    O_B_SHIFT_LEFT(Token.TT_FIXED, ">>"),
    O_B_SHIFT_RIGHT(Token.TT_FIXED, "<<"),
    O_B_AND(Token.TT_FIXED, "&"),
    O_B_OR(Token.TT_FIXED, "|"),
    O_B_XOR(Token.TT_FIXED, "^"),
    O_L_AND(Token.TT_FIXED, "&&"),
    O_L_OR(Token.TT_FIXED, "||"),
    O_INCREMENT(Token.TT_FIXED, "++"),
    O_DECREMENT(Token.TT_FIXED, "--"),
    O_ASSIGN(Token.TT_FIXED, "="),

    /**
     * SEPARATOR / COMMENT
     * tokens ignored by the actual program
     */
    SEPARATOR(Token.TT_FIXED, ""),
    COMMENT(Token.TT_WORD, "");

    public final int valueType;
    public final String fixed;
    Tokens(int valueType, String fixedValue) {
        this.valueType = valueType;
        this.fixed = fixedValue;
    }
}
