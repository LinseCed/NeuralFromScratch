package com.linusbauer.neural;

public class Layer {
    Matrix output;
    Matrix biased;
    Matrix weights;
    private int numberOfNeurons;
    public Layer(int neuronCount) {
        this.numberOfNeurons = neuronCount;
        this.output = new Matrix(neuronCount, 1);
        this.biased = new Matrix(neuronCount, 1);
    }
    public Layer() {
        this(0);
    }
    Layer nextLayer(int neuronCount) {
        Layer layer = new Layer(neuronCount);
        this.weights = new Matrix(this.output.getRows(), layer.output.getRows());
        return layer;
    }
    public static void forward(Layer curr, Layer prev) {
        Matrix transposedPrev = prev.output.transpose();
        curr.output = transposedPrev.multiply(prev.weights).transpose().add(curr.biased).sigmoid();
    }
    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Layer[\n");
      sb.append(output);
      sb.append(biased);
      sb.append(weights);
      sb.append("]\n");
      return sb.toString();
    }
}
