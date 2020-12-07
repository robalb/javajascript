package com.robalb.lexer.machines;

import com.robalb.Tokens;
import com.robalb.Token;

import java.util.EnumMap;

/**
 * machine for all the simple punctuators that can be described in a char transition table.
 * the punctuators covered by this class are all the [OtherPunctuator] except [.] and [...] and including [rightBracePunctuator]
 * @see <a href="https://www.ecma-international.org/ecma-262/#prod-OtherPunctuator">ECMAscript specs</a>
 */
public class SimplePunctuators implements Machine {

    /**
     * the current internal state
     */
    protected States state;

    /**
     * a table containig the transitions associated to each state
     */
    protected final EnumMap<States, T[]> table = new EnumMap<>(States.class);

    /**
     * when the machine reaches a stop, this is the final transition class, holding all the associated data
     */
    protected T finalTransition;

    /**
     * all the internal states
     */
    private enum States {
        _START,
        _END,

        REMAINDER,
        XOR,
        PLUS,
        MINUS,
        NOT,
        NOT_1,
        ASSIGN,
        ASSIGN_1,
        MULT,
        MULT_1,
        LESS,
        LESS_1,
        GREATER,
        GREATER_1,
        AND,
        AND_1,
        OR,
        OR_1,
    }


    public SimplePunctuators(){
        //initialize the machine
        this.reset();

        //initialize the transition table, associating one or more transition to each internal state
        this.table.put(States._START, new T[] {
                //0 states elements: these elements are identified immediately
                new T('(', Machine.PERFECTMATCH, Tokens.ROUND_LEFT),
                new T(')', Machine.PERFECTMATCH, Tokens.ROUND_RIGHT),
                new T('[', Machine.PERFECTMATCH, Tokens.SQUARE_LEFT),
                new T(']', Machine.PERFECTMATCH, Tokens.SQUARE_RIGHT),
                new T('{', Machine.PERFECTMATCH, Tokens.CURLY_LEFT),
                new T('}', Machine.PERFECTMATCH, Tokens.CURLY_RIGHT),
                new T(';', Machine.PERFECTMATCH, Tokens.SEMICOLON),
                new T(',', Machine.PERFECTMATCH, Tokens.COMMA),
                new T('~', Machine.PERFECTMATCH, Tokens.TILDE),

                new T('%', States.REMAINDER),
                new T('^', States.XOR),
                new T('+', States.PLUS),
                new T('-', States.MINUS),
                new T('!', States.NOT),
                new T('=', States.ASSIGN),
                new T('*', States.MULT),
                new T('<', States.LESS),
                new T('>', States.GREATER),
                new T('&', States.AND),
                new T('|', States.OR),
                //the last Transition in every array is reserved to the 'ELSE' case, so the char is irrelevant. It will be called when none of the
                //Transitions declared above will match
                new T('x'),
        });

        //1 state elements
        this.table.put(States.REMAINDER, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_REMAINDER_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_REMAINDER),
        });
        this.table.put(States.XOR, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_B_XOR_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_B_XOR),
        });
        this.table.put(States.PLUS, new T[]{
                new T('+', Machine.PERFECTMATCH, Tokens.P_INCREMENT),
                new T('=', Machine.PERFECTMATCH, Tokens.P_ADDITION_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_PLUS),
        });
        this.table.put(States.MINUS, new T[]{
                new T('-', Machine.PERFECTMATCH, Tokens.P_DECREMENT),
                new T('=', Machine.PERFECTMATCH, Tokens.P_SUBTRACTION_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_MINUS),
        });

        //1-2 state elements
        this.table.put(States.NOT, new T[]{
                new T('=', States.NOT_1),
                new T('x', Machine.ENDMATCH, Tokens.P_NOT),
        });
        this.table.put(States.NOT_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_NONIDENTITY),
                new T('x', Machine.ENDMATCH, Tokens.P_NOT_EQUAL),
        });

        this.table.put(States.ASSIGN, new T[]{
                new T('=', States.ASSIGN_1),
                new T('x', Machine.ENDMATCH, Tokens.P_ASSIGN),
        });
        this.table.put(States.ASSIGN_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_IDENTITY),
                new T('x', Machine.ENDMATCH, Tokens.P_EQUAL),
        });

        this.table.put(States.MULT, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_MULTIPLICATION_ASSIGN),
                new T('*', States.MULT_1),
                new T('x', Machine.ENDMATCH, Tokens.P_MULTIPLICATION),
        });
        this.table.put(States.MULT_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_EXPONENTIAL_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_EXPONENTIAL),
        });

        this.table.put(States.LESS, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_LESS_OR_EQUAL),
                new T('<', States.LESS_1),
                new T('x', Machine.ENDMATCH, Tokens.P_LESS),
        });
        this.table.put(States.LESS_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_L_SHIFT_LEFT_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_B_SHIFT_LEFT),
        });

        this.table.put(States.GREATER, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_GREATER_OR_EQUAL),
                new T('>', States.GREATER_1),
                new T('x', Machine.ENDMATCH, Tokens.P_GREATER),
        });
        this.table.put(States.GREATER_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_L_SHIFT_RIGHT_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_B_SHIFT_RIGHT),
        });

        this.table.put(States.AND, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_B_AND_ASSIGN),
                new T('&', States.AND_1),
                new T('x', Machine.ENDMATCH, Tokens.P_B_AND),
        });
        this.table.put(States.AND_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_L_AND_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_L_AND),
        });

        this.table.put(States.OR, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_B_OR_ASSIGN),
                new T('|', States.OR_1),
                new T('x', Machine.ENDMATCH, Tokens.P_B_OR),
        });
        this.table.put(States.OR_1, new T[]{
                new T('=', Machine.PERFECTMATCH, Tokens.P_L_OR_ASSIGN),
                new T('x', Machine.ENDMATCH, Tokens.P_L_OR),
        });
    }

    @Override public void reset() {
        this.state = States._START;
    }

    @Override public String getError() {
        return finalTransition.getError();
    }

    @Override public Token getToken() {
        return new Token(finalTransition.getToken());
    }


    /**
     * core of the machine
     */
    @Override public int step(char c) throws RuntimeException {
        if(state == States._END){
            throw new RuntimeException("cannot use step after the machine reaches the _END state");
        }
        //get the array of transitions for the current state
        T[] current = this.table.get(this.state);
        for(int i=0; i< current.length; i++){
            //if the char matches the current transition, or this is the last transition of the array(else transition)
            if(current[i].matches(c) || i==current.length-1){
                //update the internal state
                state = current[i].getState();
                //check if the machine reached an end
                if(state == States._END){
                    finalTransition = current[i];
                }
                break;
            }
        }
        return finalTransition.getPublicState();

    }


    /**
     * transitions class, that can be associated to every internal state
     */
    static class T {
        protected char c;
        protected States state;
        protected int publicState = Machine.STEPPING;
        protected Tokens token;
        protected String error;

        /**
         * intermediary case: the specified char cause the transition to the specified internal state
         * @param c the char that will cause this state change
         * @param state the state to change to
         */
        T(char c, States state){
            this.c = c;
            this.state = state;
        }

        /**
         * End case: the specified char cause the machine to end, returning the token found, and one of these possible public states:
         * PEFECTMATCH or ENDMATCH
         * @param c the char that will cause a state change
         * @param publicState either Machine.PERFECTMATCH or Machine.ENDMATCH
         * @param token the token found by this machine
         */
        T(char c, int publicState, Tokens token) {
            //validate the publicState constant (NOTE: this could be avoided by using enums. refactor?)
            if(publicState != Machine.ENDMATCH && publicState != Machine.PERFECTMATCH){
                throw new RuntimeException("invalid state transition table configuration");
            }
            this.c = c;
            this.state = States._END;
            this.publicState = publicState;
            this.token = token;
        }

//        /**
//         * End case: the specified char causes the machine to end, returning the specified error string and the public state ERROR
//         * @param c the char that will cause this state change
//         * @param error a string descriving the error found
//         */
//        T(char c, String error) {
//            this.c = c;
//            this.state = States._END;
//            this.error = error;
//            this.publicState = Machine.ERROR;
//        }

        /**
         * End case: the specified char causes the machine to end, returning the public state NOMATCH
         * @param c the char that will cause this state change
         */
        T(char c) {
            this.c = c;
            this.state = States._END;
            this.publicState = Machine.NOMATCH;
        }

//        /**
//         * void case: use this at the end of a state transitions array when you dont want an action associated to the 'ELSE' case
//         */
//        T(){
//
//        }

        public Tokens getToken() {
            return token;
        }

        public String getError() {
            return error;
        }

        public int getPublicState() {
            return publicState;
        }

        public States getState() {
            return state;
        }
        public boolean matches(char c){
            return this.c == c;
        }
    }

}

