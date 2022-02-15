public class fibo {
    public static int fib(int n){
        if (n <= 1){
            return n;
        }
        else {
            return fib(n - 1) + fib (n - 2);
        }
    }

    public static int fib2(int n, int k, int f0, int f1){
        if (n == k) {
            return f0;
        }
        else {
            return fib2(n, k + 1, f1, f0 + f1);
        }
    }

    public static int fib3(int n){
        int prev = 0, next = 1, temp = 0;

        int index = 0;
        while (index != n){
            temp = prev;
            prev = next;
            next = next + temp;
            ++index;
        }

        return prev;
    }

    public static int fib4(int n, int k, int f0, int f1) {
        if (n == 0){
            return f0;
        }
        return fib4(n-1, k = f0, f0 = f1, f1 = f1 + k);
    }

    public static void main(String[] args) {
        System.out.println(fib(10));
        System.out.println(fib2(10, 0, 0, 1));
        System.out.println(fib3(10));
        System.out.println(fib4(10, 0, 0 , 1));
    }
}
