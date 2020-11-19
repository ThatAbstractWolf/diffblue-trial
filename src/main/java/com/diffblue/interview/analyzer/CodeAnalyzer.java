package com.diffblue.interview.analyzer;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An interface representing code analysis functions
 */
public interface CodeAnalyzer {
    /**
     * Runs the given test and returns the covered lines of code
     * @param test to run
     * @return the covered lines of code
     */
    Set<CodeLine> runTest(CodeTest test);

    /**
     * Run all tests and returns the covered lines of code
     * @param tests to run
     * @return the covered lines of code
     */
    LinkedHashSet<CodeLine> runTestSuite(Set<CodeTest> tests);

    /**
     * Run all tests and returns the names of all tests that cover something not
     * covered by other tests
     * @param tests to run
     * @return the set of unique tests
     */
    Set<CodeTest> uniqueTests(Set<CodeTest> tests);

    /**
     * Get the instance of Code class for this run of the program.
     * @return CodeClass
     */
    CodeClass getCodeClass();

    /**
     * Get the code tests that the program generates once the source file loads.
     * @return the set of code tests that can be passed to other methods
     */
    Set<CodeTest> getCodeTests();
}
