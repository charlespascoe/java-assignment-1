package matrix;

public class Program {
    public static void main(String[] args) {

        try {
            Matrix m = new Matrix(new Number[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
            });

            System.out.println("Input matrix: \n" + m.toString());
            System.out.println("Transposed matrix: \n" + m.transpose().toString());

            Matrix m2 = new Matrix(new Number[][] {
                {1.23, 8},
                {10, -123.4}
            });
            System.out.println("\nInput matrix:\n" + m2.toString());
            System.out.println("Transposed matrix:\n" + m2.transpose().toString());
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}

