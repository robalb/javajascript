package com.robalb.lexer.machines;

import com.robalb.Token;
import com.robalb.Tokens;

//https://2ality.com/2012/03/displaying-numbers.html
public class NumericLiteral implements Machine {

    String error;

    Token token;

    /**
     * the current internal state
     */
    protected States state;
    /**
     * all the internal states
     */
    private enum States {
        _START,
        _END,
        ZERO, INITIAL_DOT, NONZERO, FRACTIONAL, EXPONENT, NONDEC,
    }

    //variables used internally
    //TODO: add to reset
    StringBuilder integerStr = new StringBuilder();
    StringBuilder fractionalStr = new StringBuilder();
    StringBuilder exponentStr = new StringBuilder();
    byte exponentSign = 0;//0:nosign 1: + -1: -
    byte integerStrRadix = 10;//one of: 2, 8, 10, 16
    boolean isBig = false;

    @Override
    public int step(int intC) throws IllegalStateException {
        if(state == States._END){
            throw new IllegalStateException("cannot use step after the machine reaches the _END state");
        }

        final boolean nonZeroDigit = intC > '0' && intC <= '9';
        final boolean decimalDigit = nonZeroDigit || intC == '0';

        switch (state){
            case _START -> {
               if(intC == '0'){
                   state = States.ZERO;
               }
               else if(intC == '.'){
                   state = States.INITIAL_DOT;
               }
               else if(nonZeroDigit){
                   integerStr.append(intC);
                   state = States.NONZERO;
               }
               else{
                   state = States._END;
                   return Machine.NOMATCH;
               }
               return Machine.STEPPING;
            }

            case ZERO -> {
                if(intC == 'x' || intC == 'X'){
                    state = States.NONDEC;
                    integerStrRadix = 16;
                }
                else if(intC == 'o' || intC == 'O'){
                    state = States.NONDEC;
                    integerStrRadix = 8;
                }
                else if(intC == 'b' || intC == 'B'){
                    state = States.NONDEC;
                    integerStrRadix = 2;
                }
                else if(decimalDigit){
                    //legacy features required in web browsers, and disabled in strict mode
                    //implementing this will require a lot of other new states and sbattimenti
                    error = "legacy octal literals are not allowed";
                    state = States._END;
                    return Machine.ERROR;
                }
                else if(intC == '.'){
                    integerStr.append('0');
                    state = States.FRACTIONAL;
                }
                else if(intC == 'n'){
                    isBig = true;
                    integerStr.append('0');
                    state = States._END;
                    return processToken(Machine.PERFECTMATCH);
                }
                else{
                    state = States._END;
                    integerStr.append('0');
                    return processToken(Machine.ENDMATCH);
                }
                return Machine.STEPPING;
            }

            case NONZERO -> {
                if(decimalDigit){
                    integerStr.append('0');
                }
                else if(intC == '.'){
                    state = States.FRACTIONAL;
                }
                else if(intC == 'e' || intC == 'E'){
                    state = States.EXPONENT;
                }
                else if(intC == 'n'){
                    isBig = true;
                    state = States._END;
                    return processToken(Machine.PERFECTMATCH);
                }
                else{
                    state = States._END;
                    return processToken(Machine.ENDMATCH);
                }
                return Machine.STEPPING;
            }

            case INITIAL_DOT ->{
                if(decimalDigit){
                    integerStr.append('0');
                    fractionalStr.append(intC);
                    state = States.FRACTIONAL;
                    return Machine.STEPPING;
                }
                else{
                    //not a number, it's some other token starting with a dot
                    state = States._END;
                    return Machine.NOMATCH;
                }
            }

            case FRACTIONAL -> {
                if(decimalDigit) {
                    fractionalStr.append(intC);
                }
                else if(intC == 'e' || intC == 'E'){
                    state = States.EXPONENT;
                }
                else{
                    state = States._END;
                    return processToken(Machine.ENDMATCH);
                }
                return Machine.STEPPING;
            }

            case EXPONENT -> {
                if(decimalDigit){
                    exponentStr.append(intC);
                }
                else if(intC == '+' || intC == '-'){
                    if(exponentSign == 0){
                        if(intC == '-') exponentSign = -1;
                        else exponentSign = 1;
                    }else{
                        //[number] used to differentiate similar error messages during debug
                        //this problem should have been avoided one step before using a lookahead.
                        //this should work anyway because a number followed by the letter 'e' is always illegal
                        error = "Unexpected or invalid token [1]";
                        return Machine.ERROR;
                    }
                }
                else{
                    if(exponentStr.length() > 0){
                        state = States._END;
                        return processToken(Machine.ENDMATCH);
                    }
                    else{
                        //this problem should have been avoided one step before using a lookahead.
                        //this should work anyway because a number followed by the letter 'e' is always illegal
                        error = "Unexpected or invalid token [2]";
                        return Machine.ERROR;
                    }
                }
                return Machine.STEPPING;
            }

            case NONDEC ->{
                final boolean octalDigit = intC >= '0' && intC <= '7';
                final boolean hexDigit = decimalDigit || ( intC >= 'a' && intC <= 'f' ) || ( intC >= 'A' && intC <= 'F' );
                final boolean binDigit = (intC == '1' || intC == '0');
                if((integerStrRadix == 2 && binDigit) || (integerStrRadix == 8 && octalDigit) || (integerStrRadix == 16 && hexDigit)){
                    integerStr.append(intC);
                }
                else if(intC == 'n'){
                    if(integerStr.length() > 0){
                        isBig = true;
                        state = States._END;
                        return processToken(Machine.PERFECTMATCH);
                    }
                    else{
                        //same lookahead problem
                        //this should work anyway because a number followed by 'bn' is always illegal
                        error = "Unexpected or invalid token [3]";
                        return Machine.ERROR;
                    }
                }
                else{
                    if(integerStr.length() > 0){
                        state = States._END;
                        return processToken(Machine.ENDMATCH);
                    }
                    else{
                        //same lookahead problem
                        //this should work anyway because a number followed by 'b' is always illegal
                        error = "Unexpected or invalid token [4]";
                        return Machine.ERROR;
                    }
                }

                return Machine.STEPPING;
            }

            default -> throw new IllegalStateException("Unexpected value: " + state);
        }

    }

    private int processToken(int returnIfOk) {
        //TODO
        //number creation process in summary:
        //if isBig=true, a different token may be useful. only the integerStr is needed
        //Otherwise, the length of the stringbuffers will reveal if there are fractional or exponential parts
        //there will always be an integer part, but the radix may vary according to integerStrRadix
        //careful with numbers too big
        token = new Token(Tokens.L_NUMBER, 420);
        return returnIfOk;
    }

    public NumericLiteral(){
        reset();
    }

    @Override
    public void reset() {
        state = States._START;
        exponentSign = 0;//one of: 0(nosign), 1, -1
        integerStrRadix = 10;//one of: 2, 8, 10, 16
        isBig = false;
        integerStr.setLength(0);
        fractionalStr.setLength(0);
        exponentStr.setLength(0);
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public Token getToken() {
        return token;
    }
}
