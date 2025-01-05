import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI extends JFrame {
    private JTextArea display;
    private boolean running = false; // Flag to control the thread

    public UI() {
        super("Stop Watch");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Display area
        display = new JTextArea("0:0:0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 32)); // Increased font size for visibility
        display.setBounds(300, 100, 200, 50); // Positioned at the center-top
        display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        display.setBackground(Color.LIGHT_GRAY);
        add(display);

        // Buttons
        String[] labels = {"Start", "Reset", "Stop"};
        JButton[] buttons = new JButton[3];

        for (int i = 0; i < 3; i++) {
            buttons[i] = new JButton(labels[i]);
            buttons[i].setBounds(150 + i * 150, 300, 100, 50); // Properly spaced buttons at the bottom
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(buttons[i]);
        }

        // Start button logic
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (running) return; // Prevent starting multiple threads
                running = true;

                new Thread(() -> {
                    try {
                        for (int h = 0; h < 24 && running; h++) {
                            for (int m = 0; m < 60 && running; m++) {
                                for (int s = 0; s < 60 && running; s++) {
                                    String time = h + ":" + m + ":" + s;
                                    display.setText(time); // Update display text
                                    Thread.sleep(100); // Pause for 1 second
                                }
                            }
                        }
                    } catch (InterruptedException ex) {
                        System.out.println("Timer interrupted");
                    }
                }).start();
            }
        });

        // Reset button logic
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false; // Stop the thread
                display.setText("0:0:0"); // Reset the display
            }
        });

        // Stop button logic
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false; // Stop the thread
            }
        });
    }
}
