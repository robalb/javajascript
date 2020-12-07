package com.robalb;

public enum Tokens {


    /**
     * WHITE SPACE
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-white-space">ECMAscript reference</a>
     *
     */
    WHITE_SPACE(Token.TV_IGNORE),

    /**
     * LINE TERMINATOR
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-line-terminators">ECMAscript reference</a>
     *
     */
    LINE_TERMINATOR(Token.TV_IGNORE),

    /**
     * COMMENT
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-comments">ECMAscript reference</a>
     */
    COMMENT(Token.TV_IGNORE),

    /**
     * LITERALS
     * the source code representation of a value of a primitive type
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-ecmascript-language-lexical-grammar-literals">ECMAscript reference</a>
     */
    L_NULL(Token.TV_FIXED, "null"),
    L_BOOLEAN(Token.TV_BOOLEAN),
    L_NUMBER(Token.TV_NUMBER),
    L_STRING(Token.TV_WORD),

    /**
     *  IDENTIFIER
     *  an unlimited-length sequence of letters and digits, the first of which must be a letter, that are not keywords
     */
    IDENTIFIER(Token.TV_WORD),

    /**
     * KEYWORDS
     * particular string literals reserved by the language, that cannot be used as identifiers
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
     * PUNCTUATORS
     * @see <a href="https://www.ecma-international.org/ecma-262/#prod-Punctuator">ECMAscript reference</a>
     */
    ROUND_LEFT(Token.TV_FIXED, "("),
    ROUND_RIGHT(Token.TV_FIXED, ")"),
    SQUARE_LEFT(Token.TV_FIXED, "["),
    SQUARE_RIGHT(Token.TV_FIXED, "]"),
    CURLY_LEFT(Token.TV_FIXED, "{"),
    CURLY_RIGHT(Token.TV_FIXED, "}"),
    SEMICOLON(Token.TV_FIXED, ";"),
    COMMA(Token.TV_FIXED, ","),
    TILDE(Token.TV_FIXED, "~"),

    P_REMAINDER(Token.TV_FIXED, "%"),
    P_REMAINDER_ASSIGN(Token.TV_FIXED, "%="),

    P_B_XOR(Token.TV_FIXED, "^"),
    P_B_XOR_ASSIGN(Token.TV_FIXED, "^="),

    P_PLUS(Token.TV_FIXED, "+"),
    P_INCREMENT(Token.TV_FIXED, "++"),
    P_ADDITION_ASSIGN(Token.TV_FIXED, "+="),

    P_MINUS(Token.TV_FIXED, "-"),
    P_DECREMENT(Token.TV_FIXED, "--"),
    P_SUBTRACTION_ASSIGN(Token.TV_FIXED, "-="),

    P_NOT(Token.TV_FIXED, "!"),
    P_NOT_EQUAL(Token.TV_FIXED, "!="),
    P_NONIDENTITY(Token.TV_FIXED, "!=="),

    P_ASSIGN(Token.TV_FIXED, "="),
    P_EQUAL(Token.TV_FIXED, "=="),
    P_IDENTITY(Token.TV_FIXED, "==="),

    P_MULTIPLICATION(Token.TV_FIXED, "*"),
    P_MULTIPLICATION_ASSIGN(Token.TV_FIXED, "*="),
    P_EXPONENTIAL(Token.TV_FIXED, "**"),
    P_EXPONENTIAL_ASSIGN(Token.TV_FIXED, "**="),

    P_LESS(Token.TV_FIXED, "<"),
    P_LESS_OR_EQUAL(Token.TV_FIXED, "<="),
    P_B_SHIFT_LEFT(Token.TV_FIXED, "<<"),
    P_L_SHIFT_LEFT_ASSIGN(Token.TV_FIXED, "<<="),

    P_GREATER(Token.TV_FIXED, ">"),
    P_GREATER_OR_EQUAL(Token.TV_FIXED, ">="),
    P_B_SHIFT_RIGHT(Token.TV_FIXED, ">>"),
    P_L_SHIFT_RIGHT_ASSIGN(Token.TV_FIXED, ">>="),

    P_B_AND(Token.TV_FIXED, "&"),
    P_B_AND_ASSIGN(Token.TV_FIXED, "&="),
    P_L_AND(Token.TV_FIXED, "&&"),
    P_L_AND_ASSIGN(Token.TV_FIXED, "&&="),

    P_B_OR(Token.TV_FIXED, "|"),
    P_B_OR_ASSIGN(Token.TV_FIXED, "|="),
    P_L_OR(Token.TV_FIXED, "||"),
    P_L_OR_ASSIGN(Token.TV_FIXED, "||="),

    //these punctuators are considered complex, as they conflict with other tokens
    P_DIVISION(Token.TV_FIXED, "/"),
    P_DIVISION_ASSIGN(Token.TV_FIXED, "/="),

    S_DOT(Token.TV_FIXED, "."),
    S_SPREAD(Token.TV_FIXED, "...");



    public int valueType;
    public String fixed;

    Tokens(int valueType) {
        this.valueType = valueType;
    }
    Tokens(int valueType, String fixedValue) {
        this.valueType = valueType;
        this.fixed = fixedValue;
    }
}
