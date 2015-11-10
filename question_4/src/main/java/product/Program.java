package product;

public class Program {
    public static void main(String[] args) {
        System.out.printf("For loop: %s%n", Program.productUsingForLoop(11, 20));
        System.out.printf("While loop: %s%n", Program.productUsingForLoop(11, 20));
        System.out.printf("Do While loop: %s%n", Program.productUsingForLoop(11, 20));
    }

    private static long productUsingForLoop(long start, long end) {
        long prod = 1;

        for (long i = start; i <= end; i++) {
            prod *= i;
        }

        return prod;
    }

    private static long productUsingWhileLoop(long start, long end) {
        long prod = 1;
        long i = start;

        while (i <= end) {
            prod *= i;
            i++;
        }

        return prod;
    }

    private static long productUsingDoWhileLoop(long start, long end) {
        long prod = 1;
        long i = start;

        do {
            prod *= i;
            i++;
        } while (i <= end);

        return prod;
    }
}

