package com.linusbauer.neural;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private final List<Layer> layers;
    private final List<String> outputLabels;
    public NeuralNetwork(List<Integer> config, List<String> outputLabels) {
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

    public void forward(final Matrix input) {
        if (layers.getFirst().output.getCols() != input.getCols() || layers.getFirst().output.getRows() != input.getRows()) {
            throw new IllegalStateException("Size of Input must match the size of first layer output");
        }
        layers.getFirst().output = input;
        for (int i = 1; i < layers.size(); i++) {
            Layer curr = layers.get(i);
            Layer prev = layers.get(i - 1);
            prev.output = prev.output.transpose();
            Layer.forward(curr, prev);
        }
        System.out.println(layers.getLast().output);
    }
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("NeuralNetwork: \n");
      sb.append("Number of Layers: " + layers.size() + "\n");      
      for (Layer layer : this.layers) {
        sb.append(layer);
      }
      return sb.toString();
    }
}
