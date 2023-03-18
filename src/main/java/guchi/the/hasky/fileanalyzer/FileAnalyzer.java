package guchi.the.hasky.fileanalyzer;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileAnalyzer {
    private static final String SENTENCES_DELIM = String.valueOf(Pattern.compile("[.?!]"));
    private static final String WORDS_DELIM = String.valueOf(Pattern.compile(" \n"));

    public static void main(String[] args) throws FileNotFoundException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        fileAnalyzer.analyze(args[0], args[1]);
    }

    public void analyze(String text, String word) throws FileNotFoundException {
        System.out.println("Search word: " + word + '.');
        String content = fileContent(text);
        int wordCount = wordCount(content, word);
        List<String> allSentences = sentences(content);
        List<String> filterSentences = filterSentences(allSentences, word);
        System.out.println("Words count: " + wordCount);
        printSentences(filterSentences);
    }

    public int wordCount(String text, String word) {
        StringTokenizer tokenizer = new StringTokenizer(text, WORDS_DELIM);
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            if (tokenizer.nextToken().contains(word)) {
                count++;
            }
        }
        return count;
    }
    @VisibleForTesting
    String fileContent(String fileName) throws FileNotFoundException {
        Path path = Path.of(fileName);
        File file = new File(path.toUri());
        try (InputStream input = new FileInputStream(file)) {
            int size = Math.toIntExact(file.length());
            byte[] buffer = new byte[size];
            while (input.read(buffer) != -1) {
                return new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Error code 404:\nWrong file name.");
        }
        return null;
    }

    private List<String> sentences(String content) {
        StringTokenizer tokenizer = new StringTokenizer(content, SENTENCES_DELIM);
        List<String> sentences = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            sentences.add(tokenizer.nextToken());
        }
        return sentences;
    }

    private List<String> filterSentences(List<String> content, String word) {
        List<String> filterSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filterSentences.add(sentence);
            }
        }
        return filterSentences;
    }
    private void printSentences(List<String> content) {
        System.out.println("Sentences:");
        for (String sentences : content) {
            System.out.println(sentences);
        }
    }
}
/*Используем классы FileInputStream, FileOutputStream, File
Практика:
1: Написать программу FileAnalyzer, которая в консоли принимает 2 параметра:
1) путь к файлу
2) слово
Usage:
java FileAnalyzer C:/test/story.txt duck

Выводит:
1) Кол-во вхождений искомого слова в файле
2) Все предложения содержащие искомое слово(предложение заканчивается символами ".", "?", "!").
Каждое предложение выводится с новой строки.
*/
