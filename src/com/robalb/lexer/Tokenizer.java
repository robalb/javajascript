package com.robalb.lexer;

import com.robalb.Token;
import com.robalb.Tokens;
import com.robalb.lexer.machines.Machine;
import com.robalb.lexer.machines.SimplePunctuators;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tokenizer {

    private long position;
    private long lastTokenPosition;
    private int line;

    protected BufferedReader buffStream;

    public Tokenizer(BufferedReader buffStream){
        this.buffStream = buffStream;
    }

    public int nextToken(){
        return 0;
    }
    private enum Mark {
        NO,
        BEFORE_READ,
        AFTER_READ
    }
    public ArrayList<Token> getTokens() throws IOException {

        ArrayList<Token> tokenizedInput = new ArrayList<>(); //access using get(index)

        //reset on every newline token
        this.position = 0;
        this.line = 0;
        //when refactoring to use stream, this should be a whole string buffer, or a bufferedreader mark
        this.lastTokenPosition = 0;

        //declare array of ?statemachines? or wathever
        //same as ArrayList<Machine> machines = new ArrayList<>();
        Machine[] machines = {
                new SimplePunctuators()
        };
        //the machine tat we are stepping through at the moment
        int machineIndex = 0;

        int currentC = -2;
        int aheadC;
        aheadC = this.buffStream.read();

        boolean skipUpdate = false;

        //specify how the mark setting should be handled on every iteration:
        //NO = don't mark. otherwise mark before or after the bufferread
        Mark setMark = Mark.BEFORE_READ;
        int MARKBUFFER = 20;//TODO: find if this is a good idea. 20 should be app. the max length of a reserved keyword. //strings or literals should throw parse error when wrong, and not require backtracking

        //iterate every character int the buffStream
        while(currentC != -1){
            //if specified, call mark before the read
            if(setMark == Mark.BEFORE_READ){
                setMark = Mark.NO;
                buffStream.mark(MARKBUFFER);
            }
            //move aheadC into currentC, and read a new char into aheadC
            if(!skipUpdate){
                currentC = aheadC;
                if (currentC != -1) {
                    aheadC = this.buffStream.read();
                }
            }else{
                skipUpdate = false;
            }
            //if specified, call mark after the read
            if(setMark == Mark.AFTER_READ){
                setMark = Mark.NO;
                buffStream.mark(MARKBUFFER);
            }

            //step through the current machine
            int state = machines[machineIndex].step(currentC, aheadC);

            //the machine encountered a parse error
            if(state == Machine.ERROR){
                System.out.println("parse error encountered");
                System.out.println(machines[machineIndex].getError());
                break;
            }
            //the machine require further steps to get a result
//            else if(state == Machine.STEPPING){
//            }

            //the machine found a result
            else if(state == Machine.PERFECTMATCH || state == Machine.ENDMATCH){
                //handle token result
                //TODO: include here ignore for tokens of type ignore, and on newLine token: reset positionInLIne, increment linenumber
                tokenizedInput.add(machines[machineIndex].getToken());

                //handle buffer marks
                if(state == Machine.ENDMATCH){
                    skipUpdate = true;
                    setMark = Mark.BEFORE_READ;
                }else{
                    setMark = Mark.AFTER_READ;
                    //if a perfectmatch just happened, and the lookahead is the eof
                    //stop now
                    if(aheadC == -1){
                        break;
                    }
                }
                //resets
                machines[machineIndex].reset();
                machineIndex = 0;
            }

            //the current machine didn't get a match: backtrack and move to the next machine
            else if(state == Machine.NOMATCH){
                machineIndex++;
                if(machineIndex == machines.length){
                    System.out.println("parse error encountered");
                    System.out.println("Unexpected character '[char]' at [line]:[column]");
                    break;
                }
                machines[machineIndex].reset();
                buffStream.reset();
            }


        }


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

        //iterate every token-thingy in the structure-yet-to-define-thing and advance them by one step.
        //when one of them returns NO_MATCH stop calling it in the iteration loop
        //when the last of them return NO_MATCH
        //  - if it was the last keyword iterating in the list: IT'S A MATCH
        //  -if multiple matches completed at the same time: wtf_think_about_this_case
        //
        //IT'S A MATCH: call the evaluator associated to that keyword, that will push the keyword+optionalValue to the stack.
        //      reset the list of updating Tokens machines
        //      reset the lastTokenPosition


        return tokenizedInput;
    }
}
