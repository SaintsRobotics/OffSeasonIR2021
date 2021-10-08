package frc.robot.ML_Utils;

public class Matrix {

    private double[][] data;

    public int x;
    public int y;

    public Matrix(double[][] matrix) {

        this.data = matrix;

        x = matrix[0].length;
        y = matrix.length;

    }

    public double[][] getData() {
        return data;
    }

    public Matrix dot(Matrix other) {

        double[][] result = new double[other.getData().length][1];

        int rowAdd = 0;

        for (int y = 0; y < other.y; y++) {
            for (int x = 0; x < other.x; x++) {
                rowAdd += this.getData()[y][x] * other.getData()[x][y];
            }
            result[y][0] = rowAdd;
        }

        return new Matrix(result);

    }

    public static void main(String[] args) {

        double[][] m = {{1, 0}, {0, 1}};

        Matrix a = new Matrix(m);

        Matrix result = a.dot(a);

        for (double[] row: a.getData())
            System.out.println(row[0]);

    }

}

}
