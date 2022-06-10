/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    private static final int NUMDIGITSINASCII = 256;
    private static int maxR;
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        maxR = asciis[0].length();
        String[] arr = new String[asciis.length];

        for (int i = 0; i != asciis.length; ++i) {
            arr[i] = asciis[i];
            if (asciis[i].length() > maxR) {
                maxR = asciis[i].length();
            }
        }

        for (int i = 0; i != arr.length; ++i) {
            while (arr[i].length() < maxR) {
                arr[i] += (char) 0;
            }
        }


        for (int d = maxR - 1; d >= 0; --d) {
            sortHelperLSD(arr, d);
        }

        int j = 0;
        for (int i = 0; i != arr.length; ++i) {
            String toAdd = new String();
            while (arr[i].charAt(j) != (char) 0 && j != arr[i].length() - 1) {
                toAdd += arr[i].charAt(j);
                ++j;
            }
            arr[i] = toAdd;
            j = 0;
        }



        return arr;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        String[] sorted = new String[asciis.length];
        //int[] conversion = new int[asciis.length];
        int[] counts = new int[NUMDIGITSINASCII];

        for (int i = 0; i != asciis.length; ++i) {
            int val = asciis[i].charAt(index);
            ++counts[val];
        }

        for (int i = 0; i != counts.length - 1; ++i) {
            counts[i + 1] = counts[i] + counts[i + 1];
        }

        for (int i = asciis.length - 1; i >= 0; --i) {
//            int place = counts[asciis[i].charAt(index)];
            --counts[asciis[i].charAt(index)];
            sorted[counts[asciis[i].charAt(index)]] = asciis[i];
        }


        for (int i = 0; i != sorted.length; ++i) {
            asciis[i] = sorted[i];
        }

        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }


    public static void main(String[] args) {
//        String[] arr = {"qwe", "asd", "zxc", "rty", "fgh", "vbn", "qew", "ads", "zcx"
//                ,"123", "456", "321", "342", "p", "fwfecedsa", "aa", "s3234", "00l"};

        String[] tinyArr = {"ba", "cb", "ad", "dc", "er"};

        String[] solutionTiny;
        String[] solutionMedium;

//        solutionMedium = sort(arr);
        solutionTiny = sort(tinyArr);

        for (int i = 0; i != tinyArr.length; ++i) {
            System.out.print(solutionTiny[i] + " ");
        }
        System.out.println();

//        for (int i = 0; i != arr.length; ++i) {
//            System.out.print(solutionMedium[i] + " ");
//        }


    }
}
