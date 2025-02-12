package com.linusbauer.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestPanel extends JPanel {
    private final BufferedImage canvas;
    private final Graphics2D g2d;
    int prevX;
    int prevY;

    public TestPanel() {
        this.setPreferredSize(new Dimension(400, 450));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        this.canvas = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        this.g2d = canvas.createGraphics();
        this.g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(20));
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, this);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(400, 400));
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
        add(drawingPanel, c);
        JButton testButton = new JButton("Test");
        testButton.setPreferredSize(new Dimension(100, 20));
        testButton.setMaximumSize(new Dimension(100, 20));
        testButton.addActionListener(e -> saveAs28x28("Test.png"));
        c.gridx = 1;
        c.weightx = 0.1;
        c.anchor = GridBagConstraints.SOUTH;
        this.add(testButton, c);
    }

    private void saveAs28x28(String fileName) {
        BufferedImage small = new BufferedImage(28, 28, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = small.createGraphics();
        g2d.drawImage(canvas, 0, 0, 28, 28, null);
        g2d.dispose();
        try {
            File outputFile = new File(fileName);
            ImageIO.write(small, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPanelName() {
        return "TestPanel";
    }
}
