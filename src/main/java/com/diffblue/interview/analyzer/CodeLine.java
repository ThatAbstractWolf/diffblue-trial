package com.diffblue.interview.analyzer;

/**
 * An interface representing a line of Java code in a file
 */
public interface CodeLine {
    /**
     * @return the line number of the code under test
     */
    int getLineNumber();

    /**
     * @return the string representation of the this line of code
     */
    String getContents();
}
