package multithreading.program;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterTask implements Runnable{
    private final String filePath;
    private final List<String> dataList;

    public FileWriterTask(String filePath, List<String> dataList) {
        this.filePath = filePath;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            synchronized (dataList) {
                for (String line : dataList) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Exception" + e.getMessage());
        }
    }
}
