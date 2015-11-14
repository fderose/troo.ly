package com.trooly.wp.materialize;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of a class that materializes text from a file.
 */
public class MaterializeTextFromFile implements IMaterializeText {
    private final String fileName;
    private final StringBuffer sb = new StringBuffer();

    public MaterializeTextFromFile(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null");
        }
        this.fileName = fileName;
    }

    public String getText() throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader rdr = null;
        try {
            rdr = new BufferedReader(new FileReader(fileName));
            String line;
            while (true) {
                line = rdr.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line + "\n");
            }
        } finally {
            if (rdr != null) {
                rdr.close();
            }
        }
        return sb.toString();
    }
}

