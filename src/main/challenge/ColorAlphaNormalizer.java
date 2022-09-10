package challenge;

import java.awt.*;

public class ColorAlphaNormalizer {
    // Most significant byte of RGB int value represents alpha, which can lead to a negative value.
    // In order to have a positive value to use as an array index, we replace alpha with 0.
    public int normalizeAlpha(int rgb) {
        Color color = new Color(rgb);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = 0;
        int normalizedRgb = new Color(red, green, blue, alpha).getRGB();
        return normalizedRgb;
    }
}
