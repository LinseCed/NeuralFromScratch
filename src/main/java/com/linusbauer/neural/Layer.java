package com.linusbauer.neural;

public class Layer {
    Matrix output;
    Matrix biased;
    Matrix weights;
    public Layer(int neuronCount) {
        this.output = new Matrix(1, neuronCount);
        this.biased = new Matrix(1, neuronCount);
    }
    public Layer() {
        this(0);
    }
    Layer nextLayer(int neuronCount) {
        Layer layer = new Layer();
        layer.output = new Matrix(1, neuronCount);
        layer.biased = new Matrix(1, neuronCount);
        this.weights = new Matrix(this.output.getCols(), layer.output.getCols());
        return layer;
    }
    public void forward() {
        // TODO
    }
}
