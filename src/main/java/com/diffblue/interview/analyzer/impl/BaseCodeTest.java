package com.diffblue.interview.analyzer.impl;

import com.diffblue.interview.analyzer.CodeLine;
import com.diffblue.interview.analyzer.CodeTest;

import java.util.LinkedHashSet;
import java.util.Set;

public final class BaseCodeTest implements CodeTest {

    /** Name of this test. */
    private final String name;
    /** Covered lines for this test */
    private final LinkedHashSet<CodeLine> coveredLines;

    /**
     * Create a new instance of {@link BaseCodeTest}
     * @param name - name of the test.
     * @param coveredLines - covered lines for this test.
     */
    public BaseCodeTest(String name, LinkedHashSet<CodeLine> coveredLines) {
        this.name = name;
        this.coveredLines = coveredLines;
    }

    /**
     * @inheritDocs
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @inheritDocs
     */
    @Override
    public LinkedHashSet<CodeLine> getCoveredLines() {
        return coveredLines;
    }
}
