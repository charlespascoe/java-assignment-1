package matrix;

public class Program {
    public static void main(String[] args) {

        try {
            Matrix m = new Matrix(new Number[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
            });

            System.out.println(m.transpose());
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}

