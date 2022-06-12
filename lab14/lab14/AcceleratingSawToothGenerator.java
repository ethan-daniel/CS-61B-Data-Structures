package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;
    public AcceleratingSawToothGenerator(int inPeriod, double inFactor) {
        state = 0;
        period = inPeriod;
        factor = inFactor;
    }

    public double next() {
        if (state < period) {
            ++state;
            return normalized();
        }

        state = 0;
        ++state;
        period *= factor;
        return normalized();
    }

    /** https://stats.stackexchange.com/questions/178626/how-to-normalize-data-between-1-and-1 */
    private double normalized() {
        double modState = state % period;
        return 2 * ((modState - 0) / (period - 1 - 0)) - 1;
    }
}
