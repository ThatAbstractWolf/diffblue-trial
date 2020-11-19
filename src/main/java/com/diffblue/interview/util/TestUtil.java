package com.diffblue.interview.util;

import com.diffblue.interview.analyzer.impl.BaseCodeLine;
import com.diffblue.interview.analyzer.CodeClass;
import com.diffblue.interview.analyzer.CodeLine;
import com.diffblue.interview.analyzer.CodeTest;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility used for most test methods and being able to accurately pull data without code replication
 */
public class TestUtil {

    /** Regex pattern to detect method contents */
    private final static Pattern METHOD_PATTERN = Pattern.compile("(?:(?:public|private|protected|static|final|native|synchronized|abstract|transient)+\\s+)+[$_\\w<>\\[\\]\\s]*\\s+[\\$_\\w]+\\([^\\)]*\\)?\\s*\\{?([^\\}]*)\\}?", Pattern.MULTILINE);

    /**
     * @param clazz - clazz
     * @return Get the lines from each method in a specific class
     */
    public static Map<Integer, LinkedHashSet<CodeLine>> getMethodLines(CodeClass clazz) {

        // Store all the lines per method
        Map<Integer, LinkedHashSet<CodeLine>> methodLines = Maps.newHashMap();

        // Generate the full class in a string
        String fullClass = getFullClass(clazz);
        // Analyse the generated class string and pull all methods located.
        Matcher methodMatcher = METHOD_PATTERN.matcher(fullClass);

        // Store an index of which method is being reviewed from Regex
        AtomicInteger index = new AtomicInteger(0);

        while (methodMatcher.find()) {

            // Loop through each find..
            LinkedHashSet<CodeLine> lines = Sets.newLinkedHashSet();

            // Get the lines for each method found by Regex.
            String[] methodContent = methodMatcher.group(1).split("\n");

            // This finds the lines only in a specific method, however, could use some work as it could be inaccurate in some cases for line number.
            Arrays.stream(methodContent).forEach(line -> lines.add(new BaseCodeLine(getLineNumberByLine(clazz.getLinesOfCode(), line), line)));

            // Add to the index and store appropriate lines
            methodLines.put(index.getAndIncrement(), lines);
        }

        // Return the method lines
        return methodLines;
    }

    /**
     * @param clazz - clazz
     * @return Reconstruct the class based on the lines present
     */
    private static String getFullClass(CodeClass clazz) {
        StringBuilder clazzBuilder = new StringBuilder();
        // Take the class lines and append to a string with a new line after each.
        clazz.getLinesOfCode().forEach(line -> clazzBuilder.append(line.getContents()).append("\n"));
        return clazzBuilder.toString();
    }

    /**
     * @param clazz - clazz
     * @return Reconstruct the class based on the lines present
     */
    public static String getFullTest(CodeTest clazz) {
        StringBuilder clazzBuilder = new StringBuilder();
        // Take the covered lines and append to a string with a new line after each.
        clazz.getCoveredLines().forEach(line -> clazzBuilder.append(line.getContents()).append("\n"));
        return clazzBuilder.toString();
    }

    /**
     * @param lines - lines in this set
     * @param line - line that is being looked for
     * @return Get key from a map by its value
     */
    private static int getLineNumberByLine(List<CodeLine> lines, String line) {
        // Get the line number by finding first instance, this may need to be tweaked once it has been reviewed to find it by method rather
        // Than in total.
        CodeLine codeLine = lines.stream().filter(foundLine -> foundLine.getContents().equals(line)).findFirst().orElse(null);
        return (codeLine != null ? codeLine.getLineNumber() : -1);
    }

    /**
     * @param tests - tests
     * @param line - line
     * @return Is a line that is being tested
     */
    public static CodeTest isTestedLine(Set<CodeTest> tests, CodeLine line) {
        // Loop through each code test that has been generated for this source file.
        for (CodeTest codeTest : tests) {
            // Get the covered lines for this CodeTest
            for (CodeLine coveredLine : codeTest.getCoveredLines()) {
                // Make sure the line is not empty
                if (coveredLine.getContents().length() > 1) {
                    // Check to make sure the line number and the line content is the same.
                    if (coveredLine.getLineNumber() == line.getLineNumber() && coveredLine.getContents().equals(line.getContents())) {
                        return codeTest;
                    }
                }
            }
        }

        // Return null if it cannot locate a CodeTest
        return null;
    }
}
