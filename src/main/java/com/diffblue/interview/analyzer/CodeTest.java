package com.diffblue.interview.analyzer;

import java.util.LinkedHashSet;

/**
 * An interface representing a Java test
 */
public interface CodeTest {
    /**
     * @return the name of the test
     */
    String getName();

    /**
     * @return the set of line numbers covered by an execution of this test
     */
    LinkedHashSet<CodeLine> getCoveredLines();
}
