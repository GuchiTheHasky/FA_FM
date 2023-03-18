package guchi.the.hasky.fileanalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyzerTest {

    private FileAnalyzer analyzer;
    @BeforeEach
    void init(){
        analyzer = new FileAnalyzer();
    }

    @Test
    @DisplayName("Test, calculate words count.")
    public void testCalculateWordsCount() throws NoSuchMethodException,
        InvocationTargetException, IllegalAccessException {

        Method method = FileAnalyzer.class.getDeclaredMethod("fileContent", String.class);
        method.setAccessible(true);
        String source = "src/test/java/guchi/the/hasky/fileanalyzer/textfortrests/Hello.txt";
        String content = (String) method.invoke(analyzer, source);

        int expected = 9;
        int actual = analyzer.wordCount(content, "Hello");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test, throw exception message if file not found.")
    public void testThrowExceptionIfInputWrongName() {
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> {
            analyzer.fileContent("SomeFile.txt");
        });
        assertNotNull(thrown.getMessage());

    }








}
