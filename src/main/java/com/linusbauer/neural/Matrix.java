package com.linusbauer.neural;

public class Matrix {
    private final int cols;
    private final int rows;
    private final float[] data;
    public Matrix(int cols, int rows, float val) {
        this.cols = cols;
        this.rows = rows;
        this.data = new float[cols*rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                this.data[i*rows+j] = val;
            }
        }
    }
    public float at(int row, int col) {
        return this.data[row*rows+col];
    }
    public Matrix fill(float val) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                this.data[i*rows+j] = val;
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
        if (this.cols != other.cols || this.rows != other.rows) {
            throw new IllegalArgumentException("Matrix does not have the same number of columns and rows");
        }
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                this.data[i*rows+j] += other.data[i*rows+j];
            }
        }
        return this;
    }
    public Matrix randomize(float max, float min) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                this.data[i*rows*j] = (float) Math.random() * (max - min) + min;
            }
        }
        return this;
    }
    public Matrix transpose() {
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
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (data[i*rows+j] != matrix.data[i*rows+j]) return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                sb.append(data[i*rows+j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}