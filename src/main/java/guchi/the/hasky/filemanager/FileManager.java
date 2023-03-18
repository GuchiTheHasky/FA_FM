package guchi.the.hasky.filemanager;

import java.io.*;
import java.util.Objects;

public class FileManager implements Manager {

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
    public void copy(String source, String destination) throws IOException {
        File file = new File(source);
        File dest = new File(destination);
        if (!file.isFile()) {
            throw new FileNotFoundException("File:\n" + source + "\ndoesn't exist.");
        } else if (!dest.isFile()) {
            copyAndCreateNewFile(source, destination);
        } else {
            copyIfFileExist(source, destination);
        }
    }

    @Override
    public void copyAll(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }
            String[] files = source.list();
            if (files == null) {
                return;
            }
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                copyAll(srcFile, destFile);
            }
        } else {
            ioFile(source, destination);
        }
    }

    @Override
    public void move(String source, String destination) throws IOException {
        File from = new File(source);
        File to = new File(destination);
        if (!to.exists()) {
            to.mkdirs();
        }
        copy(source, destination);
        from.delete();
    }

    private void copyIfFileExist(String source, String destination) throws IOException {
        File file = new File(source);
        File dest = new File(destination);
        ioFile(file, dest);
    }

    private void copyAndCreateNewFile(String source, String destination) throws IOException {
        File file = new File(source);
        String fileName = file.getName();
        File dest = new File(destination + File.separator + fileName);
        dest.createNewFile();
        ioFile(file, dest);
    }

    private static void ioFile(File file, File dest) throws IOException {
        try (FileInputStream input = new FileInputStream(file);
             FileOutputStream output = new FileOutputStream(dest)) {
            int size = (int) file.length();
            byte[] buffer = new byte[size];
            int length;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
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


