package com.diffblue.interview.analyzer.impl;

import com.diffblue.interview.analyzer.CodeClass;
import com.diffblue.interview.analyzer.CodeLine;

import java.io.File;
import java.util.List;

public final class BaseCodeClass implements CodeClass {

    /** Lines of code */
    private final List<CodeLine> linesOfCode;
    /** File */
    private final File file;

    /**
     * Create a new instance of {@link BaseCodeClass}
     * @param linesOfCode - lines of code.
     * @param file - file.
     */
    public BaseCodeClass(List<CodeLine> linesOfCode, File file) {
        this.linesOfCode = linesOfCode;
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CodeLine> getLinesOfCode() {
        return linesOfCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getFile() {
        return file;
    }
}
