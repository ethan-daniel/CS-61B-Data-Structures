public class OffByN implements CharacterComparator{
    int N;
    OffByN(int n) {
        N = n;
    }

    public boolean equalChars(char x, char y) {
        return x - y == N || y - x == N;
    }
}
