package com.linusbauer.ui;

import com.linusbauer.neural.Matrix;
import com.linusbauer.neural.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
        boolean trainWithRotation = false;
        for (int k = 0; k < epochs; k++) {
            int progress = 0;
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
                        if (trainWithRotation) {
                            BufferedImage img = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
                            for (int y = 0; y < 28; y++) {
                                for (int n = 0; n < 28; n++) {
                                    float pixelValue = image.at(y * 28 + n, 0);
                                    int gray = (int) (pixelValue * 255);
                                    int rgb = new Color(gray, gray, gray).getRGB();
                                    img.setRGB(n, y, rgb);
                                }
                            }
                            BufferedImage rotatedImg = rotateImage(img, 10);
                            Matrix rotatedMatrix = new Matrix(784, 1);
                            for (int y = 0; y < 28; y++) {
                                for (int x = 0; x < 28; x++) {
                                    int gray = (rotatedImg.getRGB(x, y) & 0xFF);
                                    rotatedMatrix.set(y * 28 + x, 0, gray / 255.0f);
                                }
                            }
                            neuralNetwork.train(rotatedMatrix, expectedValue);
                            rotatedImg = rotateImage(img, -10);
                            rotatedMatrix = new Matrix(784, 1);
                            for (int y = 0; y < 28; y++) {
                                for (int x = 0; x < 28; x++) {
                                    int gray = (rotatedImg.getRGB(x, y) & 0xFF);
                                    rotatedMatrix.set(y * 28 + x, 0, gray / 255.0f);
                                }
                            }
                            neuralNetwork.train(rotatedMatrix, expectedValue);
                        }
                        progress++;
                        if (progress % 1000 == 0) {
                            System.out.println(progress);
                        }
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

    private static BufferedImage rotateImage(BufferedImage img, double angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, img.getType());

        Graphics2D g2d = rotated.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), w / 2.0, h / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }
}
