package com.itranlin.hexagon.core.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Classes scanner.
 */
public class ClassesScanner extends SimpleClassScanner {

    private final static Logger log = LoggerFactory.getLogger(ClassesScanner.class);

    /**
     * 执行扫描.
     *
     * @param originFile  原始文件
     * @param scanPath    扫描路径
     * @param classLoader class loader
     * @return the list
     */
    public static List<Class<?>> doScan(File originFile, String scanPath,
                                        ClassLoader classLoader) {
        List<Class<?>> classes = new ArrayList<>();
        File scanFile = new File(originFile, scanPath.replaceAll("\\.", "/"));
        doScanClasses(classes, scanFile, scanPath, classLoader);
        return classes;
    }

    /**
     * Do scan classes.
     *
     * @param list        原始文件
     * @param scanFile    扫描路径
     * @param scanPath    扫描路径
     * @param classLoader class loader
     */
    public static void doScanClasses(List<Class<?>> list, File scanFile, String scanPath,
                                     ClassLoader classLoader) {

        File[] files = scanFile.listFiles();

        if (files == null) {
            log.warn("---->> files is null, scanFile = {}", scanFile);
            return;
        }
        try {
            Files.walkFileTree(scanFile.toPath(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileName = file.toAbsolutePath().toString();
                    String fullClassName = String.join(".", scanPath, fileName.substring(scanFile.getAbsolutePath().length() + 1, fileName.length() - 6).replaceAll("/", "."));
                    try {
                        listAddClass(list, classLoader, fullClassName);
                    } catch (ClassNotFoundException e) {
                        log.warn("load class error", e);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            log.warn("---->> walkFileTree error, scanFile = {}", scanFile);
        }
    }
}
