package com.robalb.fileStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * this class adds a new method, markCurrent, that behaves similarly as mark:
 * example: iterating the following string, with code that sets a mark when 'd' is reached and
 * calls a reset when 'h' is reached
 *   abcdefghijklmnopqrstuvwxyz
 *      ^   ^
 *  we have the following result
 *   abcdefghEf...
 *  using markCurrent instead of mark, we get the following result:
 *   abcdefghDe...
 *
 */
public class ExtendedBuffReader extends BufferedReader {
    private boolean cMarkSet = false;
    private boolean reset = false;

    private int lastC = -2;
    private int storedC;
    public ExtendedBuffReader(Reader in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        /*
           Hijack the read method so that when reset is called after setting a currentMark,
          the char stored at the time currentmark got called can be returned.
          all the other times, this will work as usual
         */
        int result;
        if (reset && cMarkSet) {
            reset = false;
            result = storedC;
        } else {
            reset = false;
            lastC = super.read();
            result = lastC;
        }

        return result;
    }

    @Override
    public void reset() throws IOException {
        reset = true;
        super.reset();
    }

    @Override
    public void mark(int readAheadLimit) throws IOException {
        cMarkSet = false;
        super.mark(readAheadLimit);
    }

    /**
     * similar to mark(), but one character in the past.
     * if read has never been called, will behave the same as mark();
     * @param readAheadLimit same as mark(readAheadLimit)
     * @throws IOException same as mark()
     */
    public void markCurrent(int readAheadLimit) throws IOException {
        if(lastC != -2){
            cMarkSet = true;
            storedC = lastC;
        }
        super.mark(readAheadLimit);
    }
}
