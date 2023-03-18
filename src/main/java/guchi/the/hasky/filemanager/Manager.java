package guchi.the.hasky.filemanager;

import java.io.FileNotFoundException;

public interface Manager {

    /**
     * Print count of all files in source directory,
     * if (!path.exist()) throw new NullPointException().
     */
    int countFiles(String path) throws FileNotFoundException;

    /**
     * Print count of all packages in source directory,
     * if (!directory.exist()) throw new NullPointException().
     */
    int countDirs(String path) throws FileNotFoundException;

    /**
     * Copy file, from source directory to destination directory,
     * if (!source.exist || !destination.exist) throw new FileNotFoundException().
     */
    void copy(String source, String destination) throws FileNotFoundException;

    /**
     * Copy all files & packages, from source directory to destination directory,
     * if (!source.exist || !destination.exist) throw new FileNotFoundException().
     */
    void copyAll(String source, String destination) throws FileNotFoundException;

    /**
     * Copy file, from source directory to destination directory
     * & delete file in source directory;
     * if (!source.exist || !destination.exist) throw new FileNotFoundException().
     */
    void move(String source, String destination) throws FileNotFoundException;

    /**
     * Copy all files & packages, from source directory to destination directory
     * & delete file with packages in source directory;
     * if (!source.exist || !destination.exist) throw new FileNotFoundException().
     */
    void moveAll(String source, String destination) throws FileNotFoundException;


}
