package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override

    public long countFilesInDirectory(String path) {
        long countFiles = 0;
        File directory = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(path)).getFile());
        if (directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                countFiles += countFilesInDirectory(path + "/" + file.getName());
            }
        } else {
            countFiles++;
        }
        return countFiles;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long countFiles = 0;
        File directory = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(path)).getFile());
        if (directory.isDirectory()) {
            countFiles++;
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                countFiles += countDirsInDirectory(path + "/" + file.getName());
            }
        }
        return countFiles;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File[] files = new File(from).listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    Files.copy(file.toPath(), Paths.get(to).resolve(file.getName()), REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File directory = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(path)).getFile());
        try {
            return new File(directory, name).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(directory, name).exists();
    }


    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.readAllLines(Paths.get(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                    .getResource(fileName)).toURI())).forEach(stringBuilder::append);
            return stringBuilder.toString();
        } catch (IOException | URISyntaxException e) {
            return null;
        }
    }
}
