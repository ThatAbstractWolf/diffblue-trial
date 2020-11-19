package com.diffblue.interview.analyzer.impl;

import com.diffblue.interview.analyzer.CodeLine;

public final class BaseCodeLine implements CodeLine {

    /** Line number */
    private final int lineNumber;
    /** Contents */
    private final String contents;

    /**
     * Create a new instance of {@link BaseCodeLine}
     * @param lineNumber - line number.
     * @param contents - contents.
     */
    public BaseCodeLine(int lineNumber, String contents) {
        this.lineNumber = lineNumber;
        this.contents = contents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContents() {
        return contents;
    }
}
