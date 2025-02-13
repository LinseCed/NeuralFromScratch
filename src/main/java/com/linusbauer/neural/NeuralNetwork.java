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
            layer.weights.randomize(-0.5f, 0.5f);
        }
    }

    public void forward() {

    }
}
