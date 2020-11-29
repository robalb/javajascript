package com.robalb;

public enum Tokens {
    /**
     *  IDENTIFIER
     *  an unlimited-length sequence of letters and digits, the first of which must be a letter.
     */
    IDENTIFIER{
        public int valueType(){
            return Token.TT_WORD;
        }
        public String fixed(){
            return "";
        }
    },


    /**
     * KEYWORDS
     * names reserved by the language, that cannot be used as identifiers
     */
    K_BREAK{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "break";
        }
    },
    K_CATCH{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "catch";
        }
    },
    K_CLASS{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "class";
        }
    },
    K_CONST{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "const";
        }
    },
    K_ELSE{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "else";
        }
    },
    K_FUNCTION{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "function";
        }
    },
    K_IF{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "if";
        }
    },
    K_LET{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "let";
        }
    },
    K_NEW{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "new";
        }
    },
    K_RETURN{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "return";
        }
    },
    K_THROW{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "throw";
        }
    },
    K_TRY{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "try";
        }
    },
    K_VAR {
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "var";
        }
    },
    K_WHILE{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "while";
        }
    },


    /**
     * LITERALS
     * the source code representation of a value of a primitive type
     */
    L_NULL{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "null";
        }
    },
    L_NUMBER{
        public int valueType() {
            return Token.TT_NUMBER;
        }
        public String fixed(){
            return "";
        }
    },
    L_STRING{
        public int valueType() {
            return Token.TT_WORD;
        }
        public String fixed(){
            return "";
        }
    },
    L_BOOLEAN{
        public int valueType() {
            return Token.TT_BOOLEAN;
        }
        public String fixed(){
            return "";
        }
    },


    /**
     * SEPARATORS
     * the source code representation of a value of a primitive type
     */
    S_ROUND_LEFT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "(";
        }
    },
    S_ROUND_RIGHT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ")";
        }
    },
    S_SQUARE_LEFT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "[";
        }
    },
    S_SQUARE_RIGHT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "]";
        }
    },
    S_CURLY_LEFT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "{";
        }
    },
    S_CURLY_RIGHT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "}";
        }
    },
    S_SEMICOLON{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ";";
        }
    },
    S_COMMA{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ",";
        }
    },
    S_DOT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ".";
        }
    },
    S_SPREAD{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "...";
        }
    },



    /**
     * OPERATORS
     * symbols or words related to operations
     */
    O_PLUS{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "+";
        }
    },
    O_MINUS{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "-";
        }
    },
    O_DIVISION{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "/";
        }
    },
    O_MULTIPLICATION{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "*";
        }
    },
    O_REMAINDER{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "%";
        }
    },
    O_EXPONENTIAL{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "**";
        }
    },
    O_LESS{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "<";
        }
    },
    O_GREATER{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ">";
        }
    },
    O_LESS_OR_EQUAL{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "<=";
        }
    },
    O_GERATER_OR_EQUAL{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ">=";
        }
    },
    O_EQUAL{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "==";
        }
    },
    O_NOT_EQUAL{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "!=";
        }
    },
    O_IDENTITY{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "===";
        }
    },
    O_NONIDENTITY{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "!==";
        }
    },
    O_B_SHIFT_LEFT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return ">>";
        }
    },
    O_B_SHIFT_RIGHT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "<<";
        }
    },
    O_B_AND{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "&";
        }
    },
    O_B_OR{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "|";
        }
    },
    O_B_XOR{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "^";
        }
    },
    O_L_AND{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "&&";
        }
    },
    O_L_OR{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "||";
        }
    },
    O_INCREMENT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "++";
        }
    },
    O_DECREMENT{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "--";
        }
    },
    O_ASSIGN{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "=";
        }
    },


    /**
     * SEPARATOR / COMMENT
     * tokens ignored by the actual program
     */
    SEPARATOR{
        public int valueType() {
            return Token.TT_FIXED;
        }
        public String fixed(){
            return "";
        }
    },
    COMMENT{
        public int valueType() {
            return Token.TT_WORD;
        }
        public String fixed(){
            return "";
        }
    };

    public abstract int valueType();
    public abstract String fixed();
//    public abstract String description();
}
