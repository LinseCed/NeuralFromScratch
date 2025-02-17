package com.linusbauer.ui;

import com.linusbauer.neural.Matrix;
import com.linusbauer.neural.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrainPanel extends JPanel {
    private NeuralNetwork neuralNetwork;
    public TrainPanel(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.setPreferredSize(new Dimension(400, 450));
        this.setBackground(Color.LIGHT_GRAY);
        int epochs = 20;
        for (int k = 0; k < epochs; k++) {
            InputStream inputStream = TrainPanel.class.getClassLoader().getResourceAsStream("mnist_train.csv");
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] trainingSample = line.split(",");
                        int expectedValue = Integer.parseInt(trainingSample[0]);
                        Matrix image = new Matrix(784, 1, 0);
                        for (int i = 0; i < 28; i++) {
                            for (int j = 0; j < 28; j++) {
                                image.set(i * 28 + j, 0, (Float.parseFloat(trainingSample[1 + i * 28 + j]) / 255.f));
                            }
                        }
                        neuralNetwork.train(image, expectedValue);
                    }
                    System.out.println("Epoche: " + k);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("File not found");
            }
        }
        InputStream inputStream = TrainPanel.class.getClassLoader().getResourceAsStream("mnist_test.csv");
        List<Integer> scoreBoard = new ArrayList<>();
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] trainingSample = line.split(",");
                    int expectedValue = Integer.parseInt(trainingSample[0]);
                    Matrix image = new Matrix(784, 1, 0);
                    for (int i = 0; i < 28; i++) {
                        for (int j = 0; j < 28; j++) {
                            image.set(i * 28 + j, 0, (Float.parseFloat(trainingSample[1 + i * 28 + j]) / 255.f));
                        }
                    }
                    if (Integer.parseInt(neuralNetwork.forward(image)) == expectedValue) {
                        scoreBoard.add(1);
                    } else {
                        scoreBoard.add(0);
                    }
                }
                System.out.println("Performance " + (float) scoreBoard.stream().mapToInt(i -> i).sum() / (float) scoreBoard.size());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("File not found");
        }
    }
    public String getPanelName() {
        return "TrainPanel";
    }
}
