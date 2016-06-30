package com.b5m.generator.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class FileUtils {

    public static StringBuilder readFile(String fileName) throws Exception {
        StringBuilder content = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName)));
            String readLine = reader.readLine();
            while (readLine != null) {
                content.append(readLine).append(Constant.FILE_LINE);
                readLine = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) reader.close();
        }

        return content;
    }

    public static void writeFile(String fileName, StringBuilder content) throws Exception {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(fileName)));
            String[] writeLines = content.toString().split(Constant.FILE_LINE);
            for (String line : writeLines) {
                writer.write(line);
                writer.newLine();
            }

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) writer.close();
        }
    }

}
