package com.crayons_2_0.service;

import java.awt.GridLayout;

import javafx.scene.control.Button;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.*;

public class SwingFrame extends JFrame {
	
	
	// Klasse Hat Error aber tut auch Nix ;)
	// Versuchszombie
	
	public SwingFrame() {
		
		
        super("Swing Frame");
        setLayout(new GridLayout(0, 1));
        JButton swingButton = new JButton("Swing Button");
         
        // Create JavaFX Container
        JPanel jfxPanel = new JPanel();
         
        // Switch to JavaFX Thread to create JavaFX content
        javafx.application.Platform.runLater(() -> {
            Button btJavaFX = new Button("JavaFX Button");
            jfxPanel.setScene(new Scene(btJavaFX));
            SwingUtilities.invokeLater(() -> {
                // Switch to Swing Thread, because we need to layout all stuff, when the JavaFX content loaded
                    pack();
                    setVisible(true);
                });
        });
         
        getContentPane().add(swingButton);
        getContentPane().add(jfxPanel);
    }
     
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingFrame());
    }
}
