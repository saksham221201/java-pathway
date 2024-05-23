package multithreading.program;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<String> sharedData = new ArrayList<>();
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        // It manages a pool of threads to execute tasks asynchronously, improving resource management and scalability.
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Executes the file reader task asynchronously using the executor service
        CompletableFuture<Void> readerFuture = CompletableFuture.runAsync(new FileReaderTask(inputFilePath, sharedData), executorService);
        // Executes the file writer task asynchronously using the executor service
        CompletableFuture<Void> writerFuture = CompletableFuture.runAsync(new FileWriterTask(outputFilePath, sharedData), executorService);

        // It combines both the futures to ensure both tasks are completed before proceeding
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(readerFuture, writerFuture);

        // This executes when the both futures are completed, by printing a message and shutting down the executor.
        combinedFuture.thenRun(() -> {
            System.out.println("File read write operations completed");
            executorService.shutdown();
        });

        // Waits for combined future to complete, ensuring the main thread doesn't terminate
        combinedFuture.join();

        /*
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

         */
    }
}
