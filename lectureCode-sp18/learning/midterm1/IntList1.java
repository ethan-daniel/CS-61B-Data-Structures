public class IntList1{
    public int first;
    public IntList1 rest;

    public IntList1 (int f, IntList1 r) {
        first = f;
        rest = r;
    }
    public static void addAdjacent(IntList1 L) {
        IntList1 p = L;
        while (p.rest != null) {
            if (p.first == p.rest.first) {
                p.rest = p.rest.rest;
                p.first *= 2;
                addAdjacent(L);
                break;
            }
            p = p.rest;
        }
    }

    public static void main(String[] args) {
        IntList1 L = new IntList1(1, null);
        L = new IntList1(1, L);
        L = new IntList1(1, L);
        L = new IntList1(1, L);

        addAdjacent(L);
    }
}