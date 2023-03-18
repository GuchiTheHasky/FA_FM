package guchi.the.hasky.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Manager {

    /**
     * Print count of all files in source directory.
     */
    int countFiles(String path) throws FileNotFoundException;

    /**
     * Print count of all packages in source directory.
     */
    int countDirs(String path) throws FileNotFoundException;

    /**
     * Copy file, from source directory to destination directory.
     */
    void copy(String source, String destination) throws IOException;

    /**
     * Copy all files & packages, from source directory to destination directory.
     */
    void copyAll(File source, File destination) throws IOException;

    /**
     * Copy file, from source directory to destination directory
     * & delete file in source directory.
     */
    void move(String source, String destination) throws IOException;

}
