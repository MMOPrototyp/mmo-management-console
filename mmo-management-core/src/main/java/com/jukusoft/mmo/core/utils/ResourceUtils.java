package com.jukusoft.mmo.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ResourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    private ResourceUtils() {
        //
    }

    //see also: https://www.logicbig.com/how-to/java/list-all-files-in-resouce-folder.html
    protected static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);

        if (url == null) {
            logger.warn("resource directory does not exists: {}", folder);
            return new File[0];
        }

        String path = url.getPath();
        File resourceDir = new File(path);

        //spring boot
        /*try {
            resourceDir = new ClassPathResource(folder).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        if (!resourceDir.exists()) {
            throw new IllegalStateException("resource dir does not exists: " + resourceDir.getAbsolutePath());
        }

        File[] files = resourceDir.listFiles();

        if (files == null) {
            throw new IllegalStateException("File.listFiles() returned null, maybe an I/O error occured, path: " + path);
        }

        return files;
    }

    public static List<String> getResourceFiles(String path) throws IOException {
        //see also: https://stackoverflow.com/questions/23139331/how-to-get-list-of-classpath-resources-in-nested-jar

        List<String> files = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL dirURL = classLoader.getResource(path);

        if (dirURL == null) {
            return new ArrayList<>();
        }

        if (dirURL.getProtocol().equals("jar")) {
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));

            try (final JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(path + "/") && !name.endsWith("/")) {
                        files.add(name);
                    }
                }
            }

            return files;
        } else {
            return Arrays.stream(getResourceFolderFiles(path)).map(file -> file.getName()).collect(Collectors.toList());
        }
    }

    /**
     * Reads given resource file as a string.
     * <p>
     * see also: https://stackoverflow.com/questions/6068197/utils-to-read-resource-text-file-to-string-java
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws IOException if read fails for any reason
     */
    public static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;

            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
