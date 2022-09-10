package challenge;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class ImageLoader {
    private static final int MAX_WIDTH = 500;
    private static final int MAX_HEIGHT = 500;

    // Loads an image from an URL and subsample it if it exceeds predefined maximum dimensions.
    // That way, we can predict the maximum memory needed.
    public BufferedImage loadImage(String imageUrl) throws IOException {
        URLConnection urlConnection = openConnection(imageUrl);

        try (InputStream inputStream = urlConnection.getInputStream();
             ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

            if (!readers.hasNext()) {
                throw new IOException("No reader available for supplied image stream.");
            }

            ImageReader reader = readers.next();

            ImageReadParam imageReaderParams = reader.getDefaultReadParam();
            reader.setInput(imageInputStream);

            Dimension originalDimension = new Dimension(reader.getWidth(0), reader.getHeight(0));
            Dimension maxDimension = new Dimension(MAX_WIDTH, MAX_HEIGHT);
            int subsampling = (int) scaleSubsamplingMaintainAspectRatio(originalDimension, maxDimension);
            imageReaderParams.setSourceSubsampling(subsampling, subsampling, 0, 0);

            return reader.read(0, imageReaderParams);
        }
    }

    private long scaleSubsamplingMaintainAspectRatio(Dimension d1, Dimension d2) {
        long subsampling = 1;

        if (d1.getWidth() > d2.getWidth()) {
            subsampling = Math.round(d1.getWidth() / d2.getWidth());
        } else if (d1.getHeight() > d2.getHeight()) {
            subsampling = Math.round(d1.getHeight() / d2.getHeight());
        }

        return subsampling;
    }

    private URLConnection openConnection(String imageUrl) {
        try {
            URL url = new URL(imageUrl.replace("http:", "https:"));
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(1000);
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
