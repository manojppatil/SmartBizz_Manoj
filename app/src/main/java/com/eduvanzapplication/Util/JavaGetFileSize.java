package com.eduvanzapplication.Util;


import java.io.File;

public class JavaGetFileSize {

    static final String FILE_NAME = "/Users/vijay/Downloads/file.pdf";

    public static void main(String[] args) {
        File file = new File(FILE_NAME);
        if (!file.exists() || !file.isFile()) return;

        System.out.println(getFileSizeBytes(file));
        System.out.println(getFileSizeKiloBytes(file));
        System.out.println(getFileSizeMegaBytes(file));
    }

//    public static String getFileSizeMegaBytes(File file) {
//        return String.valueOf((double) file.length() / (1024 * 1024));
//    }
//
//    public static String getFileSizeKiloBytes(File file) {
//        return String.valueOf((double) file.length() / 1024);
//    }
//
//    public static String getFileSizeBytes(File file) {
//        return String.valueOf(file.length());
//    }

    public static String getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024) + " mb";
    }

    public static String getFileSizeKiloBytes(File file) {
        return (double) file.length() / 1024 + "  kb";
    }

    public static String getFileSizeBytes(File file) {
        return file.length() + " bytes";
    }
}