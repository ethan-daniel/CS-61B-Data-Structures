public class fiboArrayRec {
    public static int[][] fibonacciArray(int n) {
        int[][] results = new int[n][];
        fibArrayHelper1(results, n - 1);
        return results;
    }

    public static void fibArrayHelper1(int[][] something, int k) {
        if (k >= 0) {
            something[k] = new int[k + 1];
            fibArrayHelper2(something[k], k);
            fibArrayHelper1(something, k - 1);
        }
    }

    public static void fibArrayHelper2(int[] array, int k) {
        if (k == 0) {
            array[k] = 1;
        } else if (k == 1) {
            fibArrayHelper2(array, k - 1);
            array[k] = 1;
        } else {
            fibArrayHelper2(array, k - 1);
            fibArrayHelper2(array, k - 2);
            array[k] = array[k - 1] + array[k - 2];
        }
    }

    public static void main(String[] args) {
        fibonacciArray(4);
    }

}
