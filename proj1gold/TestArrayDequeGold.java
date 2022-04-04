import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    /** helper functions */



    @Test
    public void testFirstTest() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        ArrayDequeSolution<String> ads2 = new ArrayDequeSolution<>();

        for (int i = 0; i != 1000; ++i) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne > 0.75 && numberBetweenZeroAndOne < 1.0) {
                sad1.addFirst(i);
                ads1.addFirst(i);
                ads2.addLast("addFirst(" + i + ")");
            }
            else if (numberBetweenZeroAndOne > 0.5 && numberBetweenZeroAndOne < 0.75) {
                sad1.addLast(i);
                ads1.addLast(i);
                ads2.addLast("addLast(" + i + ")");
            }
            else if (numberBetweenZeroAndOne > 0.25 && numberBetweenZeroAndOne < 0.75) {
                sad1.removeFirst();
                ads1.removeFirst();
                ads2.addLast("removeFirst()");
            }
            else {
                sad1.removeLast();
                ads1.removeLast();
                ads2.addLast("removeLast()");
            }

            String message = "";
            for (int k = 0; k != ads2.size(); ++k) {
                message += ads2.get(k) + "\n";
            }

            for (int j = 0; j != ads1.size(); ++j) {
                assertEquals(message, ads1.get(j), sad1.get(j));
            }
        }
    }
}
