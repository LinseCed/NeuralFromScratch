package com.linusbauer;

import com.linusbauer.ui.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UI::new); 
    }
}
