package com.diffblue.interview.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class inheriting {@link ClassLoader} used to convert bytes to a class
 *
 * I have kept this in to explain the first approach I took to this problem
 */
public final class ByteClassLoader extends ClassLoader {

    /**
     * Define a class by its name and the bytes relevant to it
     * @param file - file
     * @return Class
     */
    public final Class<?> defineClass(File file) {

        // Get the name of the file and remove the file extension.
        String fileName = file.getName();
        String sourceFileName = fileName.substring(0, fileName.lastIndexOf('.'));

        // Get the source file path.
        Path sourceFilePath = Paths.get(file.toURI());

        // Store empty variables for the error message and bytes for the file.
        String message;
        byte[] bytes = null;

        try {
            // Store the byte array from the source file prior to running any more code.
            bytes = Files.readAllBytes(sourceFilePath);
        } catch (IOException e) { /* Ignored */ }

        // If bytes failed to be declared, return null.
        if (bytes == null) return null;

        try {
            // Try to declare the name, if correct it should just return the correct class.
            return defineClass(sourceFileName, bytes, 0, bytes.length);
        } catch (NoClassDefFoundError e) {
            // Store the error message if the package errored.
            message = e.getMessage();
        }

        // If the message was null, return null as we need the message to grab the package name.
        if (message == null) return null;

        // Use getClassPackage to grab the package name.
        return defineClass(getClassPackage(message), bytes, 0, bytes.length);
    }

    /**
     * Get the package if not defined by the user already?
     * @param error - error message thrown that shows the correct package
     * @return package declaration
     */
    private String getClassPackage(String error) {

        // Start at the beginning of the error message (test/package/name/class (wrong name: class))
        int startIndex = 0;
        // Get first instance of a space (which will be after the class.
        int endIndex = error.indexOf(" ");

        // Return the package minus the (wrong name part) with dots rather than slashes.
        return error.substring(startIndex, endIndex).replace('/', '.');
    }
}
