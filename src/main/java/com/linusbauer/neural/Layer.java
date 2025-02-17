package com.linusbauer.neural;

public class Layer {
    public Matrix output;
    public Matrix biased;
    public Matrix weights;
    private int numberOfNeurons;
    public Layer(int neuronCount) {
        this.numberOfNeurons = neuronCount;
        this.output = new Matrix(neuronCount, 1);
        this.biased = new Matrix(neuronCount, 1);
        this.biased.randomize(0.1f, -0.1f);
    }
    public Layer() {
        this(0);
    }
    Layer nextLayer(int neuronCount) {
        Layer layer = new Layer(neuronCount);
        this.weights = new Matrix(neuronCount, this.output.getRows());
        return layer;
    }
    public static void forward(Layer curr, Layer prev) {
        curr.output = prev.weights.multiply(prev.output).add(curr.biased).sigmoid();
    }
    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Layer[\n");
      sb.append("Output Matrix:\n" + output + "\n");
      sb.append("Biased Matrix:\n" + biased + "\n");
      sb.append("Weights Matrix:\n" +   weights + "\n");
      sb.append("]\n");
      return sb.toString();
    }
}
