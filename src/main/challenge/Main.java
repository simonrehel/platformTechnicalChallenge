package challenge;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/***
    Platform Technical Challenge

    High-level process

    - The input file is read, and for each image URL, a color extraction task is submitted
    - A pool of threads is responsible of executing the color extraction tasks and pushing the results in a common queue
    - As soon as a thread finishes one task, it picks up a new one
    - In parallel, an additional thread is responsible of picking the results from the queue and writing the report output file

    Determining the number of threads to maximize memory use

    - The pool of threads has a fixed number of them
    - For each image we store the counts in a fixed-length array of integers, where the index is the color int value, and the array values are the corresponding counts
    - Also, when loading the image, we make sure to reduce it to a predefined maximum dimension, so we can predict the additional memory needed for that
    - Therefore we can predict the number of parallel threads that could run safely at all time within the memory limitation

    Note 1: Gray/white/black RGB values were discarded to get more meaningful results.

    Note 2: There are duplicate URLs in the input file. If it's something that could really happen, we should improve the program so it keeps track of already processed URLs and does it only once.
***/
public class Main {
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";
    private static final int NUMBER_OF_THREADS = 7;

    private static ResultAssembler resultAssembler = new ResultAssembler();
    private static ColorAlphaNormalizer colorAlphaNormalizer = new ColorAlphaNormalizer();
    private static ColorCounter colorCounter = new ColorCounter(colorAlphaNormalizer);
    private static ImageLoader imageLoader = new ImageLoader();

    private static ExecutorService colorExtractionExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static ConcurrentLinkedQueue<ResultDto> queue = new ConcurrentLinkedQueue<>();

    private static ReportWriterTask reportWriterTask = new ReportWriterTask(queue, OUTPUT_FILE);
    private static ExecutorService reportWriterTaskExecutorService = Executors.newFixedThreadPool(1);

    static {
        ImageIO.setUseCache(false);
    }

    public static void main(String[] args) {
        submitReportWritingTask();
        submitColorExtractionTaskForEachImage();
        waitForColorExtractionTasksCompletion();
        shutdownReportWritingTask();
    }

    private static void submitReportWritingTask() {
        reportWriterTaskExecutorService.submit(reportWriterTask);
    }

    private static void submitColorExtractionTaskForEachImage() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            while (reader.ready()) {
                String imageUrl = reader.readLine();
                colorExtractionExecutorService.submit(new ColorExtractorTask(queue, imageUrl, resultAssembler, colorCounter, imageLoader));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void waitForColorExtractionTasksCompletion() {
        colorExtractionExecutorService.shutdown();
        try {
            colorExtractionExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void shutdownReportWritingTask() {
        reportWriterTaskExecutorService.shutdownNow();
    }
}