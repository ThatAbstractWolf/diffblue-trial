package com.diffblue.interview;

import com.diffblue.interview.analyzer.CodeLine;
import com.diffblue.interview.analyzer.CodeTest;

import java.io.File;
import java.util.Set;

/**
 * Initial class to start the program up.
 */
public class AnalyzerProgram {

    /**
     * Main method used to boot the Java program.
     * @param args - arguments.
     */
    public static void main(String[] args) {

        // If no arguments exist show an error.
        if (args.length < 1) {
            System.out.println("Please enter a path to the class file you want to read..");
            System.out.println("Format: java -jar program.jar path");
            return;
        }

        // Get the source file path from the jar arguments.
        File sourceFile = new File(args[0]);

        // Check if the java file exists.
        if (!sourceFile.exists()) {
            System.out.println("Could not locate your source file.. (" + sourceFile.getPath() + ")");
            return;
        }

        // Start up the analyzer.
        Analyzer analyzer = new Analyzer(sourceFile);

        // Runs each test and returns appropriate lines.
        System.out.println("=================[ All Tests ]=================");
        analyzer.getCodeTests().forEach((codeTest -> {
            Set<CodeLine> lineSet = analyzer.runTest(codeTest);
            System.out.println("=================[ " + codeTest.getName() + " ]=================");
            lineSet.forEach(codeLine -> System.out.println(codeLine.getContents()));
            System.out.println("=================[ " + codeTest.getName() + " ]=================");
        }));
        System.out.println("=================[ All Tests ]=================");

        System.out.println(" ");

        // Code lines returned by the Analyzer#runTestSuite method.
        Set<CodeLine> suiteLineSet = analyzer.runTestSuite(analyzer.getCodeTests());
        // Unique tests returned by the Analyzer#uniqueTests method.
        Set<CodeTest> uniqueTestSet = analyzer.uniqueTests(analyzer.getCodeTests());

        System.out.println("=================[ Suite Lines ]=================");
        suiteLineSet.forEach(suiteLine -> System.out.println(suiteLine.getContents()));
        System.out.println("=================[ Suite Lines ]=================");

        System.out.println(" ");

        System.out.println("=================[ Unique Tests ]=================");
        uniqueTestSet.forEach(uniqueTest -> System.out.println("Unique test \"" + uniqueTest.getName() + "\" has " + uniqueTest.getCoveredLines().size() + " lines."));
        System.out.println("=================[ Unique Tests ]=================");
    }
}
