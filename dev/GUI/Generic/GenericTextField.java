package GUI.Generic;

import javax.swing.*;
import java.awt.*;

public class GenericTextField extends JTextField {
    public GenericTextField() {
        super();
        customizeTextField();
    }

    private void customizeTextField() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 16));
        setPreferredSize(new Dimension(150, 30));
        // Additional customizations can be added here
    }
}
