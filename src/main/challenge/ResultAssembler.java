package challenge;

public class ResultAssembler {
    private static final int NUM_COLORS = 16777216;

    public ResultDto assembleResult(String imageUrl, int[] colorCounts) {
        // Find highest count and color
        int color1 = 0;
        int color1Count = colorCounts[0];
        for (int i = 1; i < NUM_COLORS; i++) {
            if (colorCounts[i] > color1Count) {
                color1 = i;
                color1Count = colorCounts[i];
            }
        }

        // Find second highest count and color
        int color2 = 0;
        int color2Count = Integer.MIN_VALUE;
        for (int i = 0; i < NUM_COLORS; i++) {
            if (colorCounts[i] > color2Count && i != color1) {
                color2 = i;
                color2Count = colorCounts[i];
            }
        }
        // Find third highest count and color
        int color3 = 0;
        int color3Count = Integer.MIN_VALUE;
        for (int i = 0; i < NUM_COLORS; i++) {
            if (colorCounts[i] > color3Count && i != color1 && i != color2) {
                color3 = i;
                color3Count = colorCounts[i];
            }
        }

        return new ResultDto(imageUrl, color1, color2, color3);
    }

    public ResultDto assembleResult(String imageUrl) {
        return new ResultDto(imageUrl);
    }
}
