package multithreading.program;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> sharedData = new ArrayList<>();
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        FileReaderTask readerTask = new FileReaderTask(inputFilePath, sharedData);
        FileWriterTask writerTask = new FileWriterTask(outputFilePath, sharedData);

        Thread readerThread = new Thread(readerTask);
        Thread writerThread = new Thread(writerTask);

        readerThread.start();
        // Wait for the reader thread to finish before starting writer thread
        try {
            readerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Exception occurred in joining reader " + e.getMessage());
        }
        writerThread.start();
        // Wait for the writer thread to finish
        try {
            writerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Exception occurred in joining writer " + e.getMessage());
        }

        System.out.println("File read and write operation executed successfully");
    }
}
