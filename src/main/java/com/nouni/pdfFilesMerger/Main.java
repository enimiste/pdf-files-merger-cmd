package com.nouni.pdfFilesMerger;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            showHelp();
            return;
        }

        try {
            run(args[0], args[1]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("-".repeat(100));
            showHelp();
        }
    }

    private static void showHelp() {
        System.out.println("Usage :");
        System.out.println("java -jar program.jar  inDirPath outDirPath");
        System.out.println("inDirPath : path to the directory containing PDF files to be merged in one PDF file");
        System.out.println("outDirPath : path to the output directory on which the merged PDFs will be saved.");
        System.out.println("Thanks !");
    }

    private static void run(String inFolderPath, String outDirPath) throws Exception {
        System.out.println("Begin");
        Objects.requireNonNull(inFolderPath, "Folder path should be not null");
        File directory = new File(inFolderPath);
        if (!directory.exists())
            throw new IllegalArgumentException("Folder not found");
        if (!directory.isDirectory())
            throw new IllegalArgumentException("You should specify a folder path");

        List<File> files = loadFiles(directory);
        System.out.println(files.size() + " files found");
        mergeFiles(files, outDirPath);
        System.out.println("End");
    }

    private static String mergeFiles(List<File> files, String outDirPath) throws Exception {
        Objects.requireNonNull(files, "Files list shouldn't be null");
        Objects.requireNonNull(outDirPath, "Output directory shouldn't be null");
        if (files.isEmpty())
            throw new IllegalArgumentException("PDF files list is empty");
        showFilesPaths(files);
        File outputFile = new File(outDirPath);
        if (!outputFile.exists() || !outputFile.isDirectory())
            throw new IllegalArgumentException("The output directory doesn't exists.");
        return mergeFilesHelper(files, new File(outputFile, UUID.randomUUID().toString() + ".pdf"));
    }

    private static String mergeFilesHelper(List<File> files, File outputFile) throws Exception {
        OutputStream out = new FileOutputStream(outputFile);
        Document doc = new Document(PageSize.A4);
        PdfCopy writer = new PdfCopy(doc, out);

        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        doc.open();

        for (File file : files) {
            PdfReader in = new PdfReader(file.toURI().toURL());
            try {
                int n = in.getNumberOfPages();
                System.out.println("Begin processing " + n + " pages from the file " + file.getName());
                writer.addDocument(in);
                writer.freeReader(in);
                System.out.println("End processing " + n + " pages from the file " + file.getName());
            } catch (Exception ignored) {
            } finally {
                try {
                    in.close();
                } catch (Exception ignored) {
                }
            }
        }

        try {
            writer.close();
        } catch (Exception ignored) {
        }
        String res = outputFile.getAbsolutePath();
        System.out.println("File saved to : " + res);
        return res;
    }

    private static void showFilesPaths(List<File> files) {
        files.stream()
                .map(File::getAbsolutePath)
                .forEach(System.out::println);
    }

    private static List<File> loadFiles(File directory) {
        return Arrays
                .stream(Objects.requireNonNull(directory.listFiles((dir, name) -> {
                    return name.compareTo(".") != 0
                            && name.compareTo("..") != 0
                            && name.toLowerCase().endsWith(".pdf");
                })))
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.toList());

    }
}
