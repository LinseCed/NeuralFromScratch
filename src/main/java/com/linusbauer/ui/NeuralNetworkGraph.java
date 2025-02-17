package com.linusbauer.ui;

import com.linusbauer.neural.Matrix;
import com.linusbauer.neural.NeuralNetwork;

import javax.swing.*;
import java.awt.*;

public class NeuralNetworkGraph extends JPanel {
    NeuralNetwork network;
    NeuralNetworkGraph(NeuralNetwork network) {
        this.network = network;
        setLayout(new BorderLayout());
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                drawNeuralNetwork(g2d, network);
            }
        };
        graphPanel.setPreferredSize(calculatePreferredSize());
        JScrollPane scrollPane = new JScrollPane(graphPanel);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMinimumSize(new Dimension(400, 400));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Dimension calculatePreferredSize() {
        int layerSpacing = 200;
        int maxNeurons = 0;
        int numLayers = network.numberOfLayers();

        for (int i = 0; i < numLayers; i++) {
            maxNeurons = Math.max(maxNeurons, network.getLayers().get(i).getNumberOfNeurons());
        }

        int width = layerSpacing * numLayers + 100;
        int height = 100 * maxNeurons + 100;

        return new Dimension(width, height);
    }

    private void drawNeuralNetwork(Graphics2D g2d, NeuralNetwork network) {
        int width = 400;
        int height = 400;
        int neuronRadius = 20;
        int numLayers = network.numberOfLayers();
        int layerSpacing = width / (numLayers + 1);
        for (int i = 0; i < numLayers; i++) {
            int numNeurons = network.getLayers().get(i).getNumberOfNeurons();
            for (int j = 0; j < numNeurons; j++) {
                int x = layerSpacing * (i + 1);
                int y = layerSpacing * (j + 1);
                float outputValue = network.getLayers().get(i).output.at(j, 0);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x - neuronRadius, y - neuronRadius, 2 * neuronRadius, 2 * neuronRadius);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x - neuronRadius, y - neuronRadius, 2 * neuronRadius, 2 * neuronRadius);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                String text = String.format("%.2f", outputValue);
                int textWidth = g2d.getFontMetrics().stringWidth(text);
                int textHeight = g2d.getFontMetrics().getHeight();
                g2d.drawString(text, x - textWidth / 2, y - textHeight / 4);
                if (i < numLayers - 1) {
                    int nextLayerNeurons = network.getLayers().get(i + 1).getNumberOfNeurons();
                    for (int k = 0; k < nextLayerNeurons; k++) {
                        int nextX = layerSpacing * (i + 2);
                        int nextY = layerSpacing * (k + 1);
                        float weight = network.getLayers().get(i).weights.at(k, j);
                        float thickness = (float) Math.abs(weight * 3);
                        g2d.setStroke(new BasicStroke(thickness));
                        if (weight >= 0) {
                            g2d.setColor(Color.BLUE);
                        } else {
                            g2d.setColor(Color.RED);
                        }
                        g2d.drawLine(x, y, nextX, nextY);
                    }
                }
            }
        }
    }
    public String getPanelName() {
        return "Neural Network Graph";
    }
}
