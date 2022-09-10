package challenge;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorCounter {
    private static final int NUM_COLORS = 16777216;

    private ColorAlphaNormalizer colorAlphaNormalizer;

    public ColorCounter(ColorAlphaNormalizer colorAlphaNormalizer) {
        this.colorAlphaNormalizer = colorAlphaNormalizer;
    }

    // Returns the color counts of an image in an array where:
    // - the index is the color integer representation
    // - the value is the number of pixels of this color
    public int[] countColors(BufferedImage image) {
        int[] colorCounts = new int[NUM_COLORS];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                //Retrieving contents of a pixel
                int rgb = image.getRGB(x, y);
                if (!isGray(rgb)) {
                    int rgbWithoutAlpha = colorAlphaNormalizer.normalizeAlpha(rgb);
                    colorCounts[rgbWithoutAlpha] = colorCounts[rgbWithoutAlpha] + 1;
                }
            }
        }

        return colorCounts;
    }

    private boolean isGray(int rgb) {
        Color color = new Color(rgb);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int redGreenDiff = red - green;
        int redBlueDiff = red - blue;
        int greenBlueDiff = green - blue;
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        return Math.abs(redGreenDiff) < tolerance
                && Math.abs(greenBlueDiff) < tolerance
                && Math.abs(redBlueDiff) < tolerance;
    }
}