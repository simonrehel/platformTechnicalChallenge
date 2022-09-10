package challenge;

import java.awt.*;

public class ResultDto {
    private String imageUrl;
    private Integer color1;
    private Integer color2;
    private Integer color3;

    public ResultDto(String imageUrl, int color1, int color2, int color3) {
        this.imageUrl = imageUrl;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public ResultDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getColor1() {
        return color1;
    }

    public Integer getColor2() {
        return color2;
    }

    public Integer getColor3() {
        return color3;
    }

    public String toString() {
        if (color1 != null && color2 != null && color3 != null) {
            return String.format("%s,%s,%s,%s",
                    imageUrl,
                    formatHex(color1),
                    formatHex(color2),
                    formatHex(color3));
        } else {
            return String.format("%s,cannot load image", imageUrl);
        }
    }

    private String formatHex(int rgb) {
        Color color = new Color(rgb);
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
}