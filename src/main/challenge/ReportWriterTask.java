package challenge;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReportWriterTask implements Runnable {
    private ConcurrentLinkedQueue<ResultDto> queue;
    private String fileName;

    public ReportWriterTask(ConcurrentLinkedQueue<ResultDto> queue, String fileName) {
        this.queue = queue;
        this.fileName = fileName;
    }

    public void run() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ResultDto resultDto = queue.poll();
            while(resultDto != null || !Thread.currentThread().isInterrupted()) {
                 resultDto = queue.poll();
                if (resultDto != null) {
                    printWriter.println(resultDto);
                    printWriter.flush();
                }
            }
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
