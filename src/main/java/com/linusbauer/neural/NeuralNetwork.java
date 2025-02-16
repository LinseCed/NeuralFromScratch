package com.linusbauer.neural;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private final List<Layer> layers;
    private final List<String> outputLabels;
    private final float learningRate;
    private final int numberOfNeurons;
    public NeuralNetwork(List<Integer> config, List<String> outputLabels) {
        this.learningRate = 0.05f;
        this.numberOfNeurons = config.stream().mapToInt(i -> i).sum();
        this.layers = new ArrayList<>();
        this.outputLabels = outputLabels;
        for (int i = 0; i < config.size(); i++) {
            int neuronCount = config.get(i);
            if (i == 0) {
                layers.add(new Layer(neuronCount));
            } else {
                Layer previousLayer = layers.get(i - 1);
                layers.add(previousLayer.nextLayer(neuronCount));
            }
        }
        for (Layer layer : layers) {
           if (layer.weights != null) {
              layer.weights.randomize(-0.5f, 0.5f);
           }
        }
    }

    public String forward(final Matrix input) {
        if (layers.getFirst().output.getCols() != input.getCols() || layers.getFirst().output.getRows() != input.getRows()) {
            throw new IllegalStateException("Size of Input must match the size of first layer output: " + layers.getFirst().output.getCols() + " != " + input.getCols() + ", " + layers.getFirst().output.getRows() + " !=" + input.getRows());
        }
        layers.getFirst().output = input;
        for (int i = 1; i < layers.size(); i++) {
            Layer curr = layers.get(i);
            Layer prev = layers.get(i - 1);
            Layer.forward(curr, prev);
        }
        int result = 0;
        float highest = 0.0f;
        for (int i = 0; i < layers.getLast().output.getRows(); i++) {
            float value = layers.getLast().output.at(i, 0);
            if (value > highest) {
                highest = value;
                result = i;
            }
        }
        return outputLabels.get(result);
    }

    public float cost(Matrix expected) {
        Matrix actual = layers.getLast().output;
        return actual.subtract(expected).square().sum() / actual.getRows();
    }

    public void backprop(Matrix expected) {
        Matrix output = layers.getLast().output;
        if (output.getCols() != expected.getCols() || output.getRows() != expected.getRows()) {
            throw new IllegalArgumentException("output and expected are not the same size");
        }
        Matrix delta = output.subtract(expected);
        for (int i = layers.size() - 1; i > 0; i--) {
            Layer curr = layers.get(i);
            Layer prev = layers.get(i - 1);
            curr.biased = (curr.biased.subtract(delta.multiply(learningRate)));
            prev.weights = (prev.weights.subtract((prev.output.multiply(delta.transpose())).multiply(learningRate)));
            delta = (delta.transpose().multiply(prev.weights.transpose()).transpose().multiplyInplace(sigmoidDerivative(prev.output)));
        }
    }

    public Matrix sigmoidDerivative(Matrix a) {
        Matrix one = new Matrix(a.getRows(), a.getCols(), 1);
        return (a.multiplyInplace(one.subtract(a)));
    }

    public int numberOfLayers() {
        return layers.size();
    }

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("NeuralNetwork: \n");
      sb.append("Number of Layers: ").append(layers.size()).append("\n");
      for (Layer layer : this.layers) {
        sb.append(layer);
      }
      return sb.toString();
    }
}
