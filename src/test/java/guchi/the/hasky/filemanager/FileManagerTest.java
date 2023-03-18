package guchi.the.hasky.filemanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {


    FileManager manager = new FileManager();
    private static final List<File> FILE_LIST = new ArrayList<>();
    private static final List<File> DIR_LIST = new ArrayList<>();
    private static final String WRONG_PATH = "x:/it/is/wrong/path";


    private final File firstTxt = new File("src/test/java/guchi/the/hasky/filemanager/fortest/first.txt");
    private final File secondTxt = new File("src/test/java/guchi/the/hasky/filemanager/fortest/second.txt");
    private final File thirdTxt = new File("src/test/java/guchi/the/hasky/filemanager/fortest/third.txt");
    private final File fourthTxt =
            new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/fourth.txt");
    private final File fifthTxt =
            new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/dir2/dir3/fifth.txt");
    private final File sixthTxt =
            new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/dir2/dir3/sixth.txt");
    private final File seventhTxt =
            new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/dir2/dir4/seventh.txt");

    private final File firstDir = new File("src/test/java/guchi/the/hasky/filemanager/fortest");
    private final File secondDir = new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1");
    private final File thirdDir = new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/dir2");
    private final File fourthDir =
            new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/dir2/dir3");
    private final File fifthDir =
            new File("src/test/java/guchi/the/hasky/filemanager/fortest/dir1/dir2/dir4");


    @BeforeEach
    public void init() throws IOException {
        setSource();
        createDirs();
        createFiles();
        makeContent(firstTxt);
        createContentForMoveTest();
    }

    @AfterEach
    public void delete() {
        setSource();
        deleteFiles();
        deleteDirs();
        deleteContentForMoveTest();
    }

    @Test
    @DisplayName("Test, count all files in directory;")
    public void testCountFilesInSourceDirectory() throws IOException {
        String path = "src/test/java/guchi/the/hasky/filemanager/fortest";
        int actual = manager.countFiles(path);
        assertEquals(7, actual);
    }

    @Test
    @DisplayName("Test, throw exception in count files method if (!directory.exist());")
    public void testCountFilesInSourceDirectoryAndThrowException() {
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> {
            manager.countFiles(WRONG_PATH);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, count all packages in source directory.")
    public void testCountPackagesInSourceDirectory() throws FileNotFoundException {
        String path = "src/main/java";
        int actual = manager.countDirs(path);
        assertEquals(5, actual);
    }

    @Test
    @DisplayName("Test, count all packages in source directory and throw exception.")
    public void testCountPackagesInSourceDirectoryAndThrowException() {
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> {
            manager.countDirs(WRONG_PATH);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, copy file to directory: true")
    public void testCopySourceFileToSomeDirectoryTrue() throws IOException {
        Path sourcePath = Path.of(String.valueOf(firstTxt));
        Path destinationPath = Path.of(String.valueOf(secondTxt));

        manager.copy(sourcePath.toString(), destinationPath.toString());

        File file = new File(String.valueOf(destinationPath));
        assertTrue(file.length() > 0);

        String expectedContent = getContent(firstTxt);
        String actualContent = getContent(secondTxt);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Test, copy file to directory and throw exception if (!source.exist).")
    public void testCopyFileIfSourceDirectoryFalse() {
        Path destinationPath = Path.of(String.valueOf(secondTxt));
        Throwable thrown = assertThrows(IOException.class, () -> {
            manager.copy(WRONG_PATH, destinationPath.toString());
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, copy file to directory and throw exception if (!destination.exist).")
    public void testCopyFileIfDestinationDirectoryFalse() {
        Path sourcePath = Path.of(String.valueOf(firstTxt));
        Throwable thrown = assertThrows(IOException.class, () -> {
            manager.copy(sourcePath.toString(), WRONG_PATH);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, move file from source directory & check content.")
    public void testMoveFileFromSourceDirectory() throws IOException {
        String source = "src/test/java/guchi/the/hasky/filemanager/dirfrom/text.txt";
        String destination = "src/test/java/guchi/the/hasky/filemanager/dirto";

        File fileFrom = new File(source);
        String contentBeforeMove = getContent(fileFrom);

        assertTrue(fileFrom.exists());

        manager.move(source, destination);

        assertFalse(fileFrom.exists());

        File newFile = new File("src/test/java/guchi/the/hasky/filemanager/dirto/text.txt");
        assertTrue(newFile.exists());

        String contentAfterMove = getContent(newFile);

        assertEquals(contentBeforeMove, contentAfterMove);
    }


    private void setSource() {
        Collections.addAll(DIR_LIST, firstDir, secondDir, thirdDir, fourthDir, fifthDir);
        Collections.addAll(FILE_LIST, firstTxt, secondTxt, thirdTxt, fourthTxt, fifthTxt, sixthTxt, seventhTxt);
    }

    private void createFiles() throws IOException {
        for (File file : FILE_LIST) {
            file.createNewFile();
        }
    }

    private void createContentForMoveTest() throws IOException {
        new File("src/test/java/guchi/the/hasky/filemanager/dirfrom").mkdir();
        File text = new File("src/test/java/guchi/the/hasky/filemanager/dirfrom/text.txt");
        text.createNewFile();
        makeContent(text);
        new File("src/test/java/guchi/the/hasky/filemanager/dirto").mkdir();
    }

    private void deleteContentForMoveTest() {
        new File("src/test/java/guchi/the/hasky/filemanager/dirfrom").delete();
        new File("src/test/java/guchi/the/hasky/filemanager/dirto").delete();
        new File("src/test/java/guchi/the/hasky/filemanager/dirto/text.txt").delete();
        new File("src/test/java/guchi/the/hasky/filemanager/dirto").delete();
    }


    private void createDirs() {
        for (File file : DIR_LIST) {
            file.mkdirs();
        }
    }

    private void deleteFiles() {
        for (File file : FILE_LIST) {
            file.delete();
        }
    }

    private void deleteDirs() {
        int counter = DIR_LIST.size() - 1;
        while (counter != -1) {
            DIR_LIST.get(counter).delete();
            counter--;
        }
    }

    private void makeContent(File file) throws IOException {
        String content = "Hello java!";
        String s = "";
        int rnd = new Random().nextInt(10);

        try (OutputStream outputStream = new FileOutputStream(file)) {
            while (rnd != -1) {
                outputStream.write(content.getBytes());
                outputStream.write("\n".getBytes());
                rnd--;
            }
        }
    }

    private String getContent(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String content = "";
            while ((content = reader.readLine()) != null) {
                sb.append(content);
            }
        }
        return sb.toString();
    }
}
