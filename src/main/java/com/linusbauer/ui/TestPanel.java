package com.linusbauer.ui;

import com.linusbauer.neural.Matrix;
import com.linusbauer.neural.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TestPanel extends JPanel {
    private final transient BufferedImage canvas;
    private final transient Graphics2D g2d;
    int prevX;
    int prevY;
    private final transient NeuralNetwork network;
    private Matrix expected;
    private List<JButton> numberButtons;
    public TestPanel(NeuralNetwork network) {
        this.network = network;
        this.numberButtons = new ArrayList<>();
        this.expected = new Matrix(10, 1);
        this.setPreferredSize(new Dimension(400, 450));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout(10, 10));
        this.canvas = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        this.g2d = canvas.createGraphics();
        this.g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(getDrawingPanel(), c);
        this.add(centerPanel, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            this.repaint();
        });
        JButton testButton = new JButton("Test");
        JLabel resultLabel = new JLabel("Result: ");
        testButton.addActionListener(e -> {
            resultLabel.setText("Result: " + testDrawnImage());
        });
        resultLabel.setText("Result: ");
        controlPanel.add(testButton);
        controlPanel.add(resultLabel);
        controlPanel.add(clear);
        this.add(controlPanel, BorderLayout.SOUTH);
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(1, 10, 5, 5));
        for (int i = 0; i < 10; i++) {
            JButton numberButton = new JButton("" + i);
            numberButton.setName("" + i);
            numberButton.setPreferredSize(new Dimension(40, 40));
            numberButton.addActionListener(e -> {
                this.numberButtons.forEach(button -> {
                    button.setBackground(Color.WHITE);
                    this.expected = new Matrix(10, 1);
                });
                this.expected.set(Integer.parseInt(numberButton.getName()), 0, 1);
                numberButton.setBackground(Color.LIGHT_GRAY);
            });
            this.numberButtons.add(numberButton);
            numberPanel.add(numberButton);
        }
        this.add(numberPanel, BorderLayout.NORTH);
    }

    private JPanel getDrawingPanel() {
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, this);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(400, 400));
        drawingPanel.setMinimumSize(new Dimension(400, 400));
        drawingPanel.setMaximumSize(new Dimension(400, 400));
        drawingPanel.setBackground(Color.BLACK);
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
            }
        });
        drawingPanel.addMouseMotionListener(new MouseAdapter() {
           @Override
           public void mouseDragged(MouseEvent e) {
               int x = e.getX();
               int y = e.getY();
               g2d.drawLine(prevX, prevY, x, y);
               prevX = x;
               prevY = y;
               drawingPanel.repaint();
           }
        });
        return drawingPanel;
    }

    private String testDrawnImage() {
        BufferedImage small = new BufferedImage(28, 28, BufferedImage.TYPE_INT_ARGB);
        Matrix input = new Matrix(784, 1);
        float[] data = small.getData().getPixels(0, 0, 28, 28, (float[]) null);
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                input.set(i + j, 0, data[i * 28 + j * 4]);
            }
        }
        String result = network.forward(input);
        network.backprop(this.expected);
        return result;
    }

    public String getPanelName() {
        return "TestPanel";
    }
}
