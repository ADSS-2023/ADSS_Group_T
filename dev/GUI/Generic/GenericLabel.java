package GUI.Generic;

import javax.swing.*;
import java.awt.*;

public class GenericLabel extends JLabel {

    public GenericLabel() {
        super();
        customizeLabel();
    }

    public GenericLabel(String text) {
        super(text);
        customizeLabel();
    }

    private void customizeLabel() {
        //without background color
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 16));
        setPreferredSize(new Dimension(150, 30));
        // Additional customizations can be added here
    }
}
