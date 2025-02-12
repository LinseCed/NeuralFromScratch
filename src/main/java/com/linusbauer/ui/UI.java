package com.linusbauer.ui;

import javax.swing.*;
import java.awt.*;

public class UI {
    private JFrame frame;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public UI() {
        render();
    }
    private void render() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);
        homescreen();
        second();
        TestPanel testPanel = new TestPanel();
        this.contentPanel.add(testPanel, testPanel.getPanelName());
        sidebar();
        this.frame.add(this.contentPanel, BorderLayout.CENTER);
        this.frame.setVisible(true);
    }

    private void sidebar() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(80, this.frame.getHeight()));
        sidebarPanel.setBackground(Color.DARK_GRAY);
        sidebarPanel.add(getSidebarButton("Home"));
        sidebarPanel.add(getSidebarButton("Second"));
        sidebarPanel.add(getSidebarButton("TestPanel"));
        this.frame.getContentPane().add(sidebarPanel, BorderLayout.WEST);
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

        JLabel label = new JLabel("Welcome to LinusBauer");
        label.setBounds(300, 50, 200, 30);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(350, 100, 100, 30);
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(label);
        panel.add(exitButton);
        panel.setBackground(Color.LIGHT_GRAY);
        this.contentPanel.add(panel, "Home");
    }
    
    private void second() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 600);
        JLabel label = new JLabel("This is a second screen");
        label.setBounds(300, 50, 200, 30);
        panel.add(label);
        panel.setBackground(Color.LIGHT_GRAY);
        this.contentPanel.add(panel, "Second");
    }
}
