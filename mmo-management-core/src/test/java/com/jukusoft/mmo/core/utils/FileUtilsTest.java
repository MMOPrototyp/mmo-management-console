package com.jukusoft.mmo.core.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileUtilsTest {

    @Test
    public void testConstructor() {
        new FileUtils();
    }

    @Test(expected = FileNotFoundException.class)
    public void testListFilesNotExistentDir() throws IOException {
        FileUtils.listFiles(new File("not-existent-file.txt"), (file, relFilePath) -> {
            //don't do anything here
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListFilesFileInsteadOfDir() throws IOException {
        FileUtils.listFiles(new File("../data/junit/test.txt"), (file, relFilePath) -> {
            //don't do anything here
        });
    }

    @Test
    public void testListFiles() throws IOException {
        List<String> relPaths = FileUtils.listFiles(new File("../data/zones"));

        assertEquals(true, relPaths.size() >= 4);

        assertEquals(true, relPaths.contains(".keep"));
        assertEquals(true, relPaths.contains("default-zone/zone.json"));
        assertEquals(true, relPaths.contains("default-zone/region1/region.json"));
        assertEquals(true, relPaths.contains("default-zone/region1/map1/map.json"));
    }

    @Test
    public void testRemoveDoubleDotInDir() {
        assertEquals("/test/test2/", FileUtils.removeDoubleDotInDir("/test/test3/../test2/"));
        assertEquals("C:/Users/test/testdir/.game/terrain.png", FileUtils.removeDoubleDotInDir("C:/Users/test/testdir/.game/cache/../terrain.png"));
    }

}
