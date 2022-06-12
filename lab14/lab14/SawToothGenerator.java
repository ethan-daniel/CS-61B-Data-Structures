package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {

    private int period;
    private int state;

    public SawToothGenerator(int inPeriod) {
        state = 0;
        period = inPeriod;
    }

    public double next() {
        ++state;
        return normalized();
    }

    /** https://stats.stackexchange.com/questions/178626/how-to-normalize-data-between-1-and-1 */
    private double normalized() {
        double modState = state % period;
        return 2 * ((modState - 0) / (period - 1 - 0)) - 1;
    }

}
