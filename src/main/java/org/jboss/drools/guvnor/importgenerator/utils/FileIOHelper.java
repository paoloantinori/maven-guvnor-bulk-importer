/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.drools.guvnor.importgenerator.utils;

import java.io.*;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

/**
 * File IO helper class for reading/writing files and converting to/from base64
 */
public class FileIOHelper {
    public static final String FORMAT = "utf-8";

    public static void write(String data, File destination) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(destination));
        out.write(data.toString());
        out.flush();
        out.close();
    }

    public static String readAllAsBase64(File file) throws UnsupportedEncodingException {
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file (" + file + ")", e);
        }
        byte[] base64bytes = Base64.encodeBase64(bytes);
        bytes = null;
        System.gc();
        String base64String = new String(base64bytes, FORMAT);
        return base64String;
    }

    public static String toBase64(byte[] b) throws UnsupportedEncodingException {
        System.out.println("Input size: " + b.length);
        byte[] b64 = Base64.encodeBase64(b);
        return new String(b64, FORMAT);
    }

    public static String toBase64(File file) throws IOException {
        InputStream is =     new FileInputStream(file);
        System.gc();
        try {
            return IOUtils.toString(is, FORMAT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }


    public static String fromBase64(byte[] b64) throws UnsupportedEncodingException {
        byte[] b = Base64.decodeBase64(b64);
        return new String(b, FORMAT);
    }

}
