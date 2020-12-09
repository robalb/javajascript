package com.robalb.lexer.machines;

import com.robalb.Token;

/**
 * define the public interface for all the parser machines
 */
public interface Machine {
    /**
     * constant representing the public state the machine is currently in:
     * -- no match found by the current machine
     */
    int NOMATCH = 101;

    /**
     * constant representing the public state the machine is currently in:
     * -- the machine is stil stepping internally
     */
    int STEPPING = 102;

    /**
     * constant representing the public state the machine is currently in:
     * -- an error was encountered during the parsing
     */
    int ERROR = 103;

    /**
     * constant representing the public state the machine is currently in:
     * -- a token was found, and the last char passed to step is the last char of that token
     */
    int PERFECTMATCH = 104;

    /**
     * constant representing the public state the machine is currently in:
     * -- a token was found, and the last char passed to step is NOT part of that token
     */
    int ENDMATCH = 105;

    /**
     * initialize or reset the internal state of the machine.
     * -- this method should be called by the constructor
     */
    void reset();

    /**
     * move the parsing machine one step.
     * implementation note: if the returned state is Machine.NOMATCH the code iterating the collection of these
     *                      machines should backtrack and call the next machine.
     * @param currentChar the current char being inspected in the program string
     * @return int - the machine public state - must be one of the constants defined in Machine interface
     */
    int step(int intC, int intLookAhead);

    /**
     * if the step method returned Machine.ERROR this method will return the error string
     * @return a string describing the error encountered during the parsing
     */
    String getError();

    /**
     * if the step method returned Machine.PERFECTMACTH or ENDMATCH this method will return the Token
     * @return the token found and generated during the parse steps
     */
    Token getToken();
}
