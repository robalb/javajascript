package com.robalb;

/*
this could be useful to integrate in the future
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Operator_Precedence
 */
public enum Tokens {


    /**
     * WHITE SPACE
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-white-space">ECMAscript reference</a>
     *
     */
    WHITE_SPACE(Token.TV_IGNORE),

    /**
     * LINE TERMINATOR
     * Although not considered to be tokens, l.terminators are not ignored, as they guide the process of automatic semicolon insertion
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-line-terminators">ECMAscript reference</a>
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-lexical-and-regexp-grammars">ECMAScript reference</a>
     *
     */
    LINE_TERMINATOR(Token.TV_FIXED, "<line_terminator>"),

    /**
     * COMMENT
     * if a comment is of type multiline and it has one or more lineterminators in it, it will result in a
     * MULTILINE_COMMENT token, otherwise it will be a COMMENT token.
     * Comment tokens are ignored and not inserted in the token streams, multiline comments are replaced by a single
     * lineterminator token. By ecma standard:
     * 'if a MultiLineComment contains one or more line terminators, then it is replaced by a single
     * line terminator, which becomes part of the stream of input elements for the syntactic grammar.'
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-comments">ECMAscript reference</a>
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-lexical-and-regexp-grammars">ECMAScript reference</a>
     *
     */
    COMMENT(Token.TV_IGNORE),
    MUTLILINE_COMMENT(Token.TV_FIXED, "<multiline_comment>"),

    /**
     * LITERALS
     * the source code representation of a value of a primitive type
     * @see <a href="https://www.ecma-international.org/ecma-262/#sec-ecmascript-language-lexical-grammar-literals">ECMAscript reference</a>
     */
    L_NULL(Token.TV_FIXED, "null"),
    L_BOOLEAN(Token.TV_BOOLEAN),
    //https://2ality.com/2012/03/displaying-numbers.html
    L_NUMBER(Token.TV_NUMBER),
    L_BIGINT(Token.TV_BIGINT),
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
    //TODO update to https://www.ecma-international.org/ecma-262/#prod-ReservedWord
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
     * P_ : punctuator
     * L_ : logical
     * B_ : bitwise
     * @see <a href="https://www.ecma-international.org/ecma-262/#prod-Punctuator">ECMAscript reference</a>
     */
    ROUND_LEFT(Token.TV_FIXED, "("),
    ROUND_RIGHT(Token.TV_FIXED, ")"),
    SQUARE_LEFT(Token.TV_FIXED, "["),
    SQUARE_RIGHT(Token.TV_FIXED, "]"),
    CURLY_LEFT(Token.TV_FIXED, "{"),
    CURLY_RIGHT(Token.TV_FIXED, "}"),
    P_SEMICOLON(Token.TV_FIXED, ";"),
    P_COMMA(Token.TV_FIXED, ","),
    P_COLON(Token.TV_FIXED, ":"),
    P_B_NOT(Token.TV_FIXED, "~"),

    P_REMAINDER(Token.TV_FIXED, "%"),
    P_REMAINDER_ASSIGN(Token.TV_FIXED, "%="),

    P_B_XOR(Token.TV_FIXED, "^"),
    P_B_XOR_ASSIGN(Token.TV_FIXED, "^="),

    P_QUESTION_MARK(Token.TV_FIXED, "?"),
    P_OPTIONAL_CHAINING(Token.TV_FIXED, "?."),
    P_NULLISH_COALESCING(Token.TV_FIXED, "??"),
    P_L_NULLISH_ASSIGN(Token.TV_FIXED, "??="),


    P_PLUS(Token.TV_FIXED, "+"),
    P_INCREMENT(Token.TV_FIXED, "++"),
    P_ADDITION_ASSIGN(Token.TV_FIXED, "+="),

    P_MINUS(Token.TV_FIXED, "-"),
    P_DECREMENT(Token.TV_FIXED, "--"),
    P_SUBTRACTION_ASSIGN(Token.TV_FIXED, "-="),

    P_L_NOT(Token.TV_FIXED, "!"),
    P_L_NOT_EQUAL(Token.TV_FIXED, "!="),
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
    P_B_SHIFT_LEFT_ASSIGN(Token.TV_FIXED, "<<="),

    P_GREATER(Token.TV_FIXED, ">"),
    P_GREATER_OR_EQUAL(Token.TV_FIXED, ">="),
    P_B_SHIFT_RIGHT(Token.TV_FIXED, ">>"),
    P_B_UNSIGNED_SHIFT_RIGHT(Token.TV_FIXED, ">>>"),
    P_B_UNSIGNED_SHIFT_RIGHT_ASSIGN(Token.TV_FIXED, ">>>="),
    P_B_SHIFT_RIGHT_ASSIGN(Token.TV_FIXED, ">>="),

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

    P_DOT(Token.TV_FIXED, "."),
    P_SPREAD(Token.TV_FIXED, "...");



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
