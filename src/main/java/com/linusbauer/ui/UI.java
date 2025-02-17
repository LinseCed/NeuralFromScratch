package com.linusbauer.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.linusbauer.neural.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UI {
    private JFrame frame;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public UI() {
        render();
    }
    private void render() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);
        homescreen();
        List<Integer> layers = new ArrayList<>();
        layers.add(784);
        layers.add(200);
        layers.add(200);
        layers.add(10);
        List<String> outputs = new ArrayList<>();
        outputs.add("0");
        outputs.add("1");
        outputs.add("2");
        outputs.add("3");
        outputs.add("4");
        outputs.add("5");
        outputs.add("6");
        outputs.add("7");
        outputs.add("8");
        outputs.add("9");
        NeuralNetwork network = new NeuralNetwork(layers, outputs);
        TestPanel testPanel = new TestPanel(network);
        TrainPanel trainPanel = new TrainPanel(network);
        NeuralNetworkGraph neuralNetworkGraph = new NeuralNetworkGraph(network);
        this.contentPanel.add(neuralNetworkGraph, neuralNetworkGraph.getPanelName());
        this.contentPanel.add(testPanel, testPanel.getPanelName());
        this.contentPanel.add(trainPanel, trainPanel.getPanelName());
        sidebar();
        this.frame.add(this.contentPanel, BorderLayout.CENTER);
        this.frame.setVisible(true);
    }

    private void sidebar() {
        Sidebar sidebar = new Sidebar(80, frame.getWidth());
        sidebar.addSideBarButton(getSidebarButton("Home"));
        sidebar.addSideBarButton(getSidebarButton("Neural Network Graph"));
        sidebar.addSideBarButton(getSidebarButton("TrainPanel"));
        sidebar.addSideBarButton(getSidebarButton("TestPanel"));
        this.frame.getContentPane().add(sidebar, BorderLayout.WEST);
        this.frame.revalidate();
        this.frame.repaint();
    }

    private JButton getSidebarButton(String panelName) {
        JButton button = new JButton(panelName);
        button.setPreferredSize(new Dimension(80, 40));
        button.setMaximumSize(new Dimension(80, 40));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> cardLayout.show(this.contentPanel, panelName));
        return button;
    }

    private void homescreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 600);

        JLabel label = new JLabel("Welcome to the my Neural Network");
        label.setBounds(300, 50, 200, 30);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(350, 100, 100, 30);
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(label);
        panel.add(exitButton);
        panel.setBackground(Color.LIGHT_GRAY);
        this.contentPanel.add(panel, "Home");
    }
}
