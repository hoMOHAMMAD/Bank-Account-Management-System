/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author moham
 */
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class FocusHelper {

    public static void applyEnterNavigation(JComponent... components) {

        for (int i = 0; i < components.length; i++) {

            JComponent current = components[i];
            JComponent next = components[(i + 1) % components.length];
            JComponent previous = components[(i - 1 + components.length) % components.length];

            // ENTER moves to next component
            current.getInputMap(JComponent.WHEN_FOCUSED).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                    "moveNext"
            );

            current.getActionMap().put("moveNext", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    next.requestFocusInWindow();
                }
            });

            // DOWN arrow moves to next component
            current.getInputMap(JComponent.WHEN_FOCUSED).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
                    "moveNextArrow"
            );

            current.getActionMap().put("moveNextArrow", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    next.requestFocusInWindow();
                }
            });

            // UP arrow moves to previous component
            current.getInputMap(JComponent.WHEN_FOCUSED).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                    "movePreviousArrow"
            );

            current.getActionMap().put("movePreviousArrow", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    previous.requestFocusInWindow();
                }
            });
        }
    }
}
