package com.diffblue.interview;

import com.diffblue.interview.analyzer.CodeLine;
import com.diffblue.interview.analyzer.CodeTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnalyzerTest {

    /** {@link Analyzer} instance */
    private static Analyzer analyzer;

    @BeforeClass
    public static void runSetup() {

        // Demo source to run this program on.
        File demoFile = new File("./example/", "Message.java");

        // Create a new Analyzer instance.
        analyzer = new Analyzer(demoFile);
    }

    @Test
    public void runTest() {

        // Runs each test and returns appropriate lines.
        System.out.println("=================[ All Tests (" + analyzer.getCodeTests().size() + ") ]=================");

        analyzer.getCodeTests().forEach((codeTest -> {
            Set<CodeLine> lineSet = analyzer.runTest(codeTest);

            System.out.println(" ");
            System.out.println("=================[ " + codeTest.getName() + " ]=================");
            lineSet.forEach(codeLine -> System.out.println(codeLine.getContents()));
            System.out.println("=================[ " + codeTest.getName() + " ]=================");
            System.out.println(" ");
        }));

        System.out.println("=================[ All Tests (" + analyzer.getCodeTests().size() + ") ]=================");
        System.out.println(" ");
    }

    @Test
    public void runTestSuite() {

        // Code lines returned by the Analyzer#runTestSuite method.
        Set<CodeLine> suiteLineSet = analyzer.runTestSuite(analyzer.getCodeTests());

        System.out.println("=================[ Suite Lines ]=================");
        suiteLineSet.forEach(suiteLine -> System.out.println(suiteLine.getContents()));
        System.out.println("=================[ Suite Lines ]=================");
        System.out.println(" ");
    }

    @Test
    public void uniqueTests() {

        // Unique tests returned by the Analyzer#uniqueTests method.
        Set<CodeTest> uniqueTestSet = analyzer.uniqueTests(analyzer.getCodeTests());

        System.out.println("=================[ Unique Tests ]=================");
        uniqueTestSet.forEach(uniqueTest -> System.out.println("Unique test \"" + uniqueTest.getName() + "\" has " + uniqueTest.getCoveredLines().size() + " lines."));
        System.out.println("=================[ Unique Tests ]=================");
        System.out.println(" ");
    }
}