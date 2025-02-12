package com.linusbauer.neural;

public class Matrix {
    private int cols;
    private int rows;
    private float[] data;
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
    public int getCols() {
        return cols;
    }
    public int getRows() {
        return rows;
    }
}
