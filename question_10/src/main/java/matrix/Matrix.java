package matrix;

import java.lang.IndexOutOfBoundsException;
import java.util.Arrays;

public class Matrix {
    private Number[][] matrix;

    public Matrix(Number[][] matrix) throws Exception {
        if (!Matrix.isValidMatrix(matrix)) {
            throw new Exception("Invalid matrix");
        }

        this.matrix = matrix;
    }

    public static boolean isValidMatrix(Number[][] m) {
        if (m == null) return false;

        // A matrix must have at least 1 row and 1 column
        if (m.length == 0 || m[0].length == 0) {
            return false;
        }

        // Every row must have the same number of columns
        for (int i = 1; i < m.length; i++) {
            if (m[i].length != m[0].length) {
                return false;
            }
        }

        return true;
    }

    public static Matrix createNewMatrix(int rows, int columns) throws Exception {
        if (rows <= 0 || columns <= 0) {
            throw new Exception("rows and columns must be greater than 0");
        }

        // Like the mathematical definition of a matrix,
        // the first index will be the row, and the second will be the column
        Number[][] matrix = new Number[rows][];

        for (int i = 0; i < rows; i++) {
            matrix[i] = new Number[columns];
        }

        return new Matrix(matrix);
    }

    public int getRowCount() {
        return this.matrix.length;
    }

    public int getColumnCount() {
        return this.matrix[0].length;
    }

    public Number get(int row, int column) throws IndexOutOfBoundsException {
        if (row < 0 || row >= this.getRowCount() || column < 0 || column >= this.getColumnCount()) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        return this.matrix[row][column];
    }

    public void set(int row, int column, Number value) throws IndexOutOfBoundsException {
        if (row < 0 || row >= this.getRowCount() || column < 0 || column >= this.getColumnCount()) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        this.matrix[row][column] = value;
    }

    public Matrix transpose() throws Exception {
        Matrix transposedMatrix = Matrix.createNewMatrix(this.getColumnCount(), this.getRowCount());

        for (int row = 0; row < this.getRowCount(); row++) {
            for (int col = 0; col < this.getColumnCount(); col++) {
                transposedMatrix.set(col, row, this.get(row, col));
            }
        }

        return transposedMatrix;
    }

    @Override
    public String toString() {
        int colSize = 1;

        for (int row = 0; row < this.getRowCount(); row++) {
            for (int col = 0; col < this.getColumnCount(); col++) {
                String str = this.get(row, col).toString();
                if (str.length() > colSize) {
                    colSize = str.length();
                }
            }
        }

        StringBuilder str = new StringBuilder();

        char topLeftCorner = '\u250F';
        char topRightCorner = '\u2513';
        char bottomLeftCorner = '\u2517';
        char bottomRightCorner = '\u251B';
        char verticalPipe = '\u2503';

        String gap = Utils.repeat(' ', (colSize + 2) * this.getColumnCount() - 2);

        str.append(topLeftCorner).append(gap).append(topRightCorner).append('\n');
        for (int row = 0; row < this.getRowCount(); row++) {
            str.append(verticalPipe);
            for (int col = 0; col < this.getColumnCount(); col++) {
                if (col > 0) str.append(", ");
                str.append(Utils.padLeft(this.get(row, col).toString(), colSize));
            }
            str.append(verticalPipe).append('\n');
        }
        str.append(bottomLeftCorner).append(gap).append(bottomRightCorner).append('\n');

        return str.toString();
    }
}

