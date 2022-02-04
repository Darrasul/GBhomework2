package ru.buzas.client.service;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ChatHistory implements AutoCloseable {

    private static final String FILENAME_PATTERN = "./history/history_%s.txt";

    private final String username;
    private File historyFile;
    private PrintWriter printWriter;

    public ChatHistory(String username) {
        this.username = username;
    }

    public void init() {
        try {
            historyFile = createHistoryFile();
            this.printWriter = new PrintWriter(new BufferedWriter(new FileWriter(historyFile, StandardCharsets.UTF_8, true)));
        } catch (IOException e) {
            System.err.println("Failed to initialize user history");
            e.printStackTrace();
        }
    }

    public void renameInit(String newUsername) {
        try {
            historyFile = createRenamedHistoryFile(newUsername);
            this.printWriter = new PrintWriter(new BufferedWriter(new FileWriter(historyFile, StandardCharsets.UTF_8, true)));
        } catch (IOException e) {
            System.err.println("Failed to initialize user history");
            e.printStackTrace();
        }
    }

    private File createHistoryFile() throws IOException {
        String filePath = String.format(FILENAME_PATTERN, username);
        File file = new File(filePath);
        if(!file.exists()){
            file.getParentFile().mkdir();
            file.createNewFile();
        }
        return file;
    }

    private File createRenamedHistoryFile(String newUsername) throws IOException {
        String filePath = String.format(FILENAME_PATTERN, newUsername);
        File file = new File(filePath);
        if(!file.exists()){
            file.getParentFile().mkdir();
            file.createNewFile();
        }
        return file;
    }

    public void appendText(String text) {
        printWriter.print(text);
        printWriter.flush();
    }

    public String loadLastRows(int rowsNumber) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(historyFile, "r")) {
            long pointer;
            int count = 0;

            for (pointer = randomAccessFile.length() - 1; pointer > 0; pointer--) {
                randomAccessFile.seek(pointer);

                if (randomAccessFile.read() == '\n') {
                    count++;
                }
                if (count == rowsNumber) {
                    break;
                }
            }

            if (pointer >= 0) {
                randomAccessFile.seek(pointer);
            }

            byte[] resultData = new byte[(int) (randomAccessFile.length() - randomAccessFile.getFilePointer())];
            randomAccessFile.read(resultData);
            return new String(resultData, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Unable to read rows from " + historyFile.getAbsoluteFile());
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void close() {
        if (printWriter != null) {
            printWriter.close();
        }
    }
}
