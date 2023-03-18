package guchi.the.hasky.filemanager;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Objects;

public class FileManager implements Manager {
    public static void main(String[] args) throws FileNotFoundException {
        FileManager manager = new FileManager();
        int result = manager.countFiles("c:/ProjectsToRemove");
        System.out.println(result);
    }

    @Override
    public int countFiles(String path) throws FileNotFoundException {
        File directory = new File(path);
        int count = 0;
        if (directory.exists()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file != null && file.isDirectory()) {
                    count += countFiles(String.valueOf(file));
                }
                if (file != null && file.isFile()) {
                    count++;
                }
            }
            return count;
        } else {
            throw new FileNotFoundException("Error, wrong directory name.");
        }
    }

    @Override
    public int countDirs(String path) throws FileNotFoundException {
        File directory = new File(path);
        if (directory.exists()) {
            int count = 0;
            if (directory.isDirectory()) {
                for (File file : Objects.requireNonNull(directory.listFiles())) {
                    if (file.isDirectory()) {
                        count++;
                        count += countDirs(String.valueOf(file));
                    }
                }
            }
            return count;
        } else {
            throw new FileNotFoundException("Error, wrong directory name.");
        }
    }

    @Override
    public void copy(String source, String destination) throws FileNotFoundException {
        File file = new File(source);
        File dest = new File(destination);
        try (FileInputStream input = new FileInputStream(file);
             FileOutputStream output = new FileOutputStream(dest)) {
            int size = source.length();
            byte[] buffer = new byte[size];
            int length;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Error, wrong directory or source name.");
        }
    }

    @Override
    public void copyAll(String source, String destination) throws FileNotFoundException {
        File from = new File(source);
        File to = new File(destination);
        try {
            FileUtils.copyDirectory(from, to);
        } catch (IOException e) {
            throw new FileNotFoundException("Error, wrong directory or source name.");
        }
    }

    @Override
    public void move(String source, String destination) throws FileNotFoundException {
        File from = new File(source);
        File to = new File(destination);
        try {
            FileUtils.moveFile(from, to);
        } catch (IOException e) {
            throw new FileNotFoundException("Error, wrong directory or source name.");
        }
    }

    @Override
    public void moveAll(String source, String destination) throws FileNotFoundException {
        File from = new File(source);
        File to = new File(destination);
        try {
            FileUtils.moveDirectory(from, to);
        } catch (IOException e) {
            throw new FileNotFoundException("Error, wrong directory or source name.");
        }
    }
}

    /*2: Пишем class FileManager с методами
public class FileManager {
// public static int countFiles(String path) - принимает путь к папке,
// возвращает количество файлов в папке и всех подпапках по пути
public static int countFiles(String path) {
return 0;
}

// public static int countDirs(String path) - принимает путь к папке,
// возвращает количество папок в папке и всех подпапках по пути
public static int countDirs(String path) {
return 0;
}
}

public static void copy(String from, String to) - метод по копированию папок и файлов.
Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
public static void move(String from, String to) - метод по перемещению папок и файлов.
Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.*/


