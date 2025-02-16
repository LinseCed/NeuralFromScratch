package com.linusbauer.ui;

import com.linusbauer.neural.NeuralNetwork;

import javax.swing.*;
import java.awt.*;

public class TrainPanel extends JPanel {
    private NeuralNetwork neuralNetwork;
    public TrainPanel(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.setPreferredSize(new Dimension(400, 450));
        this.setBackground(Color.LIGHT_GRAY);
    }
    public String getPanelName() {
        return "TrainPanel";
    }
}
