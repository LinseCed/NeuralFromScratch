package com.linusbauer.ui;

import com.formdev.flatlaf.FlatLightLaf;

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
        second();
        TestPanel testPanel = new TestPanel();
        this.contentPanel.add(testPanel, testPanel.getPanelName());
        sidebar();
        this.frame.add(this.contentPanel, BorderLayout.CENTER);
        this.frame.setVisible(true);
    }

    private void sidebar() {
        Sidebar sidebar = new Sidebar(80, frame.getWidth());
        sidebar.addSideBarButton(getSidebarButton("Home"));
        sidebar.addSideBarButton(getSidebarButton("Second"));
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
