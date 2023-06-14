package PresentationLayer.GUI.Components;

import javax.swing.*;
import java.awt.*;

public class Button<T> extends JButton {
    private T data;

    public Button(String text) {
        super(text);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void customizeButton() {
        // Customization logic goes here
        // Example: Setting a custom background color
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Add more customization as needed
    }
}
