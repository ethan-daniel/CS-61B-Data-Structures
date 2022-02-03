public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        while (x < 10) {
            y += x;
            System.out.print(y + " ");
            x = x + 1;
        }
        System.out.println();
        System.out.println(5 + "10");
        System.out.println(5 + 10);
    }
}