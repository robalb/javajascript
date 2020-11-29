package com.robalb.lexer;

import com.robalb.Token;
import com.robalb.Tokens;
import com.robalb.lexer.machines.Machine;
import com.robalb.lexer.machines.Separator;

import java.util.ArrayList;

public class Tokenizer {

    private long position;
    private long lastTokenPosition;
    private int line;

    public int nextToken(){
        return 0;
    }
    public ArrayList<Token> tokenize(String input){

        this.position = 0;
        this.lastTokenPosition = 0;
        this.line = 0;
        //declare array of ?statemachines? or wathever
        //same as ArrayList<Machine> machines = new ArrayList<>();
        var machines = new ArrayList<Machine>();
        machines.add(Separator.START);

        int currentMachine = 0;
        while(position < input.length()){

            /*
             * linkedlist of enums, all implementing same interface
             * take the first one, set its state to initial, then call it's step method and look at the return:
             * if it's error: halt everything, print error
             * if it's nomatch: go to the next enumorclass
             * if it's anything else: set the enum state to that, and call step again
             * rupdate position, lastinput, reset everything
             */


            //update state machines. when they reach a no-match, the should enter the state ->just_closed. in the following iterations it will remain->closed
            //when all the state machines are just_closed or closed
            //  instead of iterating again, call the evaluator for the keyword associated to the statemachine that is just_closed (possible cases for multiple just_closed)
            //  reset the statemachine thing, update lastTokenPosition to position and start again
            position++;
        }
        //iterate every token-thingy in the structure-yet-to-define-thing and advance them by one step.
        //when one of them returns NO_MATCH stop calling it in the iteration loop
        //when the last of them return NO_MATCH
        //  - if it was the last keyword iterating in the list: IT'S A MATCH
        //  -if multiple matches completed at the same time: wtf_think_about_this_case
        //
        //IT'S A MATCH: call the evaluator associated to that keyword, that will push the keyword+optionalValue to the stack.
        //      reset the list of updating Tokens machines
        //      reset the lastTokenPosition

        ArrayList<Token> tokenizedInput = new ArrayList<>(); //access using get(index)
        tokenizedInput.add(new Token(Tokens.IDENTIFIER, "_identifier_test"));

        return tokenizedInput;
    }
    // private void scan(String[]input){
    // }
    // private void evaluate(String[] defineThisThing){
    // }
}
