package com.linusbauer.ui;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar(int width, int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(width, height ));
        setBackground(Color.DARK_GRAY);
    }
    public void addSideBarButton(JButton button) {
        add(button);
    }
}
