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
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
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
        int neuronRadius = 20;
        int numLayers = network.numberOfLayers();
        int layerSpacing = 200;
        int panelWidth = 400;
        int panelHeight = 400;

        int[] layerCenters = new int[numLayers];

        int maxNeurons = 0;
        for (int i = 0; i < numLayers; i++) {
            maxNeurons = Math.max(maxNeurons, network.getLayers().get(i).getNumberOfNeurons());
        }
        int maxLayerHeight = maxNeurons * (2 * neuronRadius + 20);
        int yOffset = maxLayerHeight / 2;
        for (int i = 0; i < numLayers; i++) {
            int numNeurons = network.getLayers().get(i).getNumberOfNeurons();
            int layerHeight = numNeurons * (2 * neuronRadius + 20);
            layerCenters[i] = (panelHeight - layerHeight) / 2 + neuronRadius +yOffset;
        }

        for (int i = 0; i < numLayers; i++) {
            int numNeurons = network.getLayers().get(i).getNumberOfNeurons();
            int x = layerSpacing * (i + 1);

            for (int j = 0; j < numNeurons; j++) {
                int y = layerCenters[i] + j * (2 * neuronRadius + 20);

                g2d.setColor(Color.WHITE);
                g2d.fillOval(x - neuronRadius, y - neuronRadius, 2 * neuronRadius, 2 * neuronRadius);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x - neuronRadius, y - neuronRadius, 2 * neuronRadius, 2 * neuronRadius);

                float outputValue = network.getLayers().get(i).output.at(j, 0);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                String text = String.format("%.2f", outputValue);
                int textWidth = g2d.getFontMetrics().stringWidth(text);
                g2d.drawString(text, x - textWidth / 2, y + 5);

                if (i < numLayers - 1) {
                    int nextNumNeurons = network.getLayers().get(i + 1).getNumberOfNeurons();
                    int nextX = layerSpacing * (i + 2);
                    int nextYCenter = layerCenters[i + 1];

                    for (int k = 0; k < nextNumNeurons; k++) {
                        int nextY = nextYCenter + k * (2 * neuronRadius + 20);
                        float weight = network.getLayers().get(i).weights.at(k, j);
                        float thickness = (float) Math.abs(weight * 3);

                        g2d.setStroke(new BasicStroke(thickness));
                        g2d.setColor(weight >= 0 ? Color.BLUE : Color.RED);
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
