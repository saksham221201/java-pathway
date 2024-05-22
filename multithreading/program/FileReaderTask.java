package multithreading.program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileReaderTask implements Runnable{
    private final String filePath;
    private final List<String> dataList;

    public FileReaderTask(String filePath, List<String> dataList) {
        this.filePath = filePath;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            synchronized (dataList) {
                while ((line = reader.readLine()) != null) {
                    dataList.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught " + e.getMessage());
        }
    }
}
