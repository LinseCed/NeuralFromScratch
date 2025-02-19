package com.linusbauer.ui;

import com.linusbauer.neural.Matrix;
import com.linusbauer.neural.NeuralNetwork;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPanel extends JPanel {
    private final transient BufferedImage canvas;
    private final transient Graphics2D g2d;
    int prevX;
    int prevY;
    private final transient NeuralNetwork network;
    private Matrix expected;
    private List<JButton> numberButtons;
    private static final Map<Integer, Color> NUMBER_COLORS = new HashMap<>();
    static {
        NUMBER_COLORS.put(0, Color.RED);
        NUMBER_COLORS.put(1, Color.BLUE);
        NUMBER_COLORS.put(2, Color.GREEN);
        NUMBER_COLORS.put(3, Color.ORANGE);
        NUMBER_COLORS.put(4, Color.MAGENTA);
        NUMBER_COLORS.put(5, Color.CYAN);
        NUMBER_COLORS.put(6, Color.YELLOW);
        NUMBER_COLORS.put(7, Color.PINK);
        NUMBER_COLORS.put(8, new Color(128, 0, 128)); // Purple
        NUMBER_COLORS.put(9, new Color(165, 42, 42)); // Brown
    }

    public TestPanel(NeuralNetwork network) {
        this.network = network;
        this.numberButtons = new ArrayList<>();
        this.expected = new Matrix(10, 1);
        this.setPreferredSize(new Dimension(400, 450));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout(10, 10));
        this.canvas = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        this.g2d = canvas.createGraphics();
        this.g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(40, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(getDrawingPanel(), c);
        this.add(centerPanel, BorderLayout.CENTER);
        JPanel resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(400, 50));
        resultPanel.setBackground(Color.GRAY);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            this.repaint();
        });
        JButton testButton = new JButton("Test");
        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        testButton.addActionListener(e -> {
            int detectedNumber = Integer.parseInt(testDrawnImage());
            resultLabel.setText("Result: " + detectedNumber);
            Color color = NUMBER_COLORS.getOrDefault(detectedNumber, Color.GRAY);
            resultPanel.setBackground(color);
            resultPanel.repaint();
        });
        resultLabel.setText("Result: ");
        resultLabel.setForeground(Color.BLACK);
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        controlPanel.add(testButton);
        controlPanel.add(clear);
        this.add(resultPanel, BorderLayout.NORTH);
        this.add(controlPanel, BorderLayout.SOUTH);
    }

    private JPanel getDrawingPanel() {
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, this);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(800, 800));
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
        BufferedImage small = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = small.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(canvas, 0, 0, 28, 28,null);
        g2d.dispose();
        float[] blurKernel = {
                1 / 16f, 2 / 16f, 1 / 16f,
                2 / 16f, 4 / 16f, 2 / 16f,
                1 / 16f, 2 / 16f, 1 / 16f
        };
        Kernel kernel = new Kernel(3, 3, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        small = op.filter(small, null);
        File outputFile = new File("test.png");
        try {
            ImageIO.write(small, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Matrix input = new Matrix(784, 1);
        float[] data = small.getData().getPixels(0, 0, 28, 28, (float[]) null);
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                input.set(i * 28 + j, 0, ((data[i * 28 + j] / 255.f * 0.99f) + 0.01f));
            }
        }
        return network.forward(input);
    }

    public String getPanelName() {
        return "TestPanel";
    }
}
