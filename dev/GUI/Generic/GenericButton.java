package GUI.Generic;

import javax.swing.JButton;
import java.awt.*;

public class GenericButton extends JButton {

    public GenericButton(String text) {
        super(text);
        customizeButton();
    }

    private void customizeButton() {
        // Set the font
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        setFont(buttonFont);

        // Set the foreground and background color

        Color backgroundColor = Color.gray;

        setBackground(backgroundColor);

        // Set the button size
        Dimension buttonSize = new Dimension(300, 60);

        setPreferredSize(buttonSize);

        // Add an event listener
        addActionListener(e -> {
            // Perform actions when the button is clicked
            System.out.println("Button clicked");
            // Add your custom code here
        });
    }

}
