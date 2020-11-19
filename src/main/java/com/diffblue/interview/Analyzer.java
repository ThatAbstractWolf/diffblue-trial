package com.diffblue.interview;

import com.diffblue.interview.analyzer.CodeAnalyzer;
import com.diffblue.interview.analyzer.CodeClass;
import com.diffblue.interview.analyzer.CodeLine;
import com.diffblue.interview.analyzer.CodeTest;
import com.diffblue.interview.analyzer.impl.*;
import com.diffblue.interview.util.HashUtil;
import com.diffblue.interview.util.TestUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Analyzer class to analyze the code from the Java source.
 */
public class Analyzer implements CodeAnalyzer {

    /** This is the {@link CodeClass} being ran by the analyzer. */
    private CodeClass codeClass;

    /** Code tests built for this specific Java source file. */
    private final Set<CodeTest> codeTests;

    /**
     * Create a new instance of {@link Analyzer}
     * @param file - the Java source file.
     */
    public Analyzer(File file) {

        // Lines of code.
        List<CodeLine> linesOfCode = Lists.newArrayList();
        codeTests = Sets.newHashSet();

        // BufferedReader to read each line of the Java file.
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            // Line
            String line;

            // Line number
            int lineNum = 1;
            while ((line = bufferedReader.readLine()) != null) {
                // Prints line then adds an instance of CodeLine to the linesOfCode list.
                linesOfCode.add(new BaseCodeLine(lineNum, line));
                lineNum += 1;
            }

            // Create an instance of BaseCodeClass to store the lines of code and the file they came from.
            codeClass = new BaseCodeClass(linesOfCode, file);
            System.out.println("Created a new code class with " + linesOfCode.size() + " lines and based on " + file.getAbsolutePath().replace("\\", "\\\\"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build appropriate tests
        if (codeClass != null) {

            // Get the lines per method in this code class.
            Map<Integer, LinkedHashSet<CodeLine>> methodLines = TestUtil.getMethodLines(codeClass);

            // Loop through the methods and generate appropriate tests.
            methodLines.forEach((key, value) -> {
                // Store the name as Test %index + 1%
                String testName = "Test " + (key + 1);
                codeTests.add(new BaseCodeTest(testName, value));
            });

            // Print all lines and indicate tested lines..
            codeClass.getLinesOfCode().forEach(codeLine -> {
                // Checks if a code test exists for this line so it can print out the appropriate test name.
                CodeTest test = TestUtil.isTestedLine(codeTests, codeLine);
                System.out.println(codeLine.getLineNumber() + ": " + codeLine.getContents() + (test != null ? " (" + test.getName() + ")" : ""));
            });
        }
    }

    /**
     * @inheritDocs
     */
    @Override
    public Set<CodeLine> runTest(CodeTest test) {
        // Return the covered lines for this specific test.
        return test.getCoveredLines();
    }

    /**
     * @inheritDocs
     */
    @Override
    public LinkedHashSet<CodeLine> runTestSuite(Set<CodeTest> tests) {
        LinkedHashSet<CodeLine> lines = Sets.newLinkedHashSet();
        // Loop through each tests lines and add all distinct (unique) lines.
        tests.forEach(test -> lines.addAll(test.getCoveredLines().stream().filter(line -> line.getLineNumber() != -1).distinct().collect(Collectors.toList())));
        return lines;
    }

    /**
     * @inheritDocs
     */
    @Override
    public Set<CodeTest> uniqueTests(Set<CodeTest> tests) {
        Set<CodeTest> uniqueTests = Sets.newHashSet();

        // All the hashes for all the tests
        Map<CodeTest, String> uniqueHashes = Maps.newHashMap();

        // Loop through all passed tests
        tests.forEach(test -> {

            String hash = HashUtil.getHash(TestUtil.getFullTest(test));

            if (!uniqueHashes.containsValue(hash)) {
                uniqueTests.add(test);
            }

            uniqueHashes.put(test, hash);
        });
        
        // Unique tests
        System.out.println(uniqueTests.size());
        return uniqueTests;
    }

    /**
     * @inheritDocs
     */
    @Override
    public CodeClass getCodeClass() {
        return codeClass;
    }

    /**
     * @inheritDocs
     */
    @Override
    public Set<CodeTest> getCodeTests() {
        return codeTests;
    }
}
