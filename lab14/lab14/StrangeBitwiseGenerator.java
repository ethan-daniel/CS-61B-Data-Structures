package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int inPeriod) {
        state = 0;
        period = inPeriod;
    }

    public double next() {
        ++state;
        return normalized();
    }

    /** https://stats.stackexchange.com/questions/178626/how-to-normalize-data-between-1-and-1 */
    private double normalized() {
        int weirdState = state & (state >>> 3) % period;
//        weirdState = state & (state >> 3) & (state >> 8) % period;
        weirdState = state & (state >> 7) % period;
        double modState = weirdState % period;
        return 2 * ((modState - 0) / (period - 1 - 0)) - 1;
    }
}
