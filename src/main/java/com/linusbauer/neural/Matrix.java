package com.linusbauer.neural;

public class Matrix {
    private int cols;
    private int rows;
    private final float[] data;
    public Matrix(int rows, int cols, float val) {
        this.cols = cols;
        this.rows = rows;
        this.data = new float[rows*cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i*cols+j] = val;
            }
        }
    }
    public Matrix(int rows, int cols) {
        this(rows, cols, 0);
    }
    public float at(int row, int col) {
        return this.data[row*cols+col];
    }
    public Matrix fill(float val) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i*cols+j] = val;
            }
        }
        return this;
    }
    public Matrix set(int row, int col, float val) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            throw new IndexOutOfBoundsException();
        }
        this.data[row * cols + col] = val;
        return this;
    }
    public Matrix add(Matrix other) {
        if (other == null) {
            return this;
        }
        if (this.cols != other.cols || this.rows != other.rows) {
            throw new IllegalArgumentException("Matrix does not have the same number of columns and rows");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i*cols+j] += other.data[i*cols+j];
            }
        }
        return this;
    }
    public Matrix subtract(Matrix other) {
        if (other == null) {
            return this;
        }
        if (this.cols != other.cols || this.rows != other.rows) {
            throw new IllegalArgumentException("Matrix does not have the same number of columns and rows");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i*cols+j] -= other.data[i*cols+j];
            }
        }
        return this;
    }
    public Matrix multiply(Matrix other) {
        if (other == null) {
            return this;
        }
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Cannot multiply matrix with different number of columns and rows");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        int n = this.cols;
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < result.getCols(); c++) {
                float val = 0;
                for (int i = 0; i < n; i++) {
                    val += this.at(r, i) * other.at(i, c);
                }
                result.set(r, c, val);
            }
        }
        return result;
    }
    public Matrix square() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i*cols+j] *= this.data[i*cols+j];
            }
        }
        return this;
    }
    public Matrix multiply(float val) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i*cols+j] *= val;
            }
        }
        return this;
    }
    public Matrix randomize(float max, float min) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i*cols+j] = (float) Math.random() * (max - min) + min;
            }
        }
        return this;
    }
    public Matrix transpose() {
        Matrix result = new Matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.set(j, i, at(i, j));
            }
        }
        return result;
    }
    private float sigmoid(float val) {
        return (float) (1 / (1 + Math.exp(-val)));
    }
    public Matrix sigmoid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.set(i, j, this.sigmoid(this.at(i, j)));
            }
        }
        return this;
    }
    public float[] getData() {
        return this.data;
    }
    public int getCols() {
        return cols;
    }
    public int getRows() {
        return rows;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        if (cols != matrix.cols) return false;
        if (rows != matrix.rows) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (data[i*cols+j] != matrix.data[i*cols+j]) return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rows: "+ rows + " Cols: " + cols + "[\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i*cols+j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
