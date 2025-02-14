package com.linusbauer.neural;

public class Layer {
    Matrix output;
    Matrix biased;
    Matrix weights;
    public Layer(int neuronCount) {
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
        System.out.println("prev.output: " + prev.output + "prev.weights: " +  prev.weights + " curr.biased " + curr.biased);
        curr.output = prev.output.multiply(prev.weights).transpose().add(curr.biased).sigmoid();
    }
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Layer[\n");
      sb.append(output);
      sb.append(biased);
      sb.append(weights);
      return sb.toString();
    }
}
