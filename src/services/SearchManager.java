package services;

public class SearchManager {
    public static int levDiv(int i, int j, String s1, String s2, int[][] matrix)
    {
        if (i == 0 && j == 0)
            return 0;
        if (i > 0 && j == 0)
            return i;
        if (j > 0 && i == 0)
            return j;
        int m = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;

        int min = matrix[i][j - 1] + 1;
        if (matrix[i - 1][j] + 1 < min)
            min = matrix[i - 1][j] + 1;
        if (matrix[i - 1][j - 1] + m < min)
            min = matrix[i - 1][j - 1] + m;
        return min;
    }

    public static int levenshteinDistance(String desired, String received) {
        int[][] matrix = new int[desired.length() + 1][received.length() + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = levDiv(i, j, desired, received, matrix);
            }
        }
        return matrix[desired.length()][received.length()];
    }
}
