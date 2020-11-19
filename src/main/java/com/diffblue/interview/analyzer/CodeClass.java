package com.diffblue.interview.analyzer;

import java.io.File;
import java.util.List;

/**
 * An interface representing an entire Java compilation unit in a file
 */
public interface CodeClass {
    /**
     * @return the contents of the code as an array of {link CodeLine}'s
     */
    List<CodeLine> getLinesOfCode();

    /**
     * @return the {@link File} containing the code under test
     */
    File getFile();
}
