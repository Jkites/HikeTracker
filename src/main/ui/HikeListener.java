package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// a listener for actions on the UI for HikeApp
public class HikeListener implements ActionListener {
    private final JTextField nameField = new JTextField();
    private final JTextField lengthField = new JTextField();
    private final JTextField ratingField = new JTextField();
    private final JTextField indexField = new JTextField();
    private final HikeApp ha;
    private final JLabel imageLabel;

    // EFFECTS: creates new HikeListener and initializes associated image
    public HikeListener(HikeApp ha) {
        super();
        this.ha = ha;
        ImageIcon image = new ImageIcon("./data/motivation.jpg");
        imageLabel = new JLabel(image);
    }

    // MODIFIES: HikeApp
    // EFFECTS: Takes action in HikeApp based on the action event that is listened
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Save".equals(e.getActionCommand())) {
            ha.saveHikeList();
        } else if ("Load".equals(e.getActionCommand())) {
            ha.loadHikeList();
        } else if ("Remove".equals(e.getActionCommand())) {
            ha.removeHike(indexField.getText());
        } else if ("Add".equals((e.getActionCommand()))) {
            ha.addHike(nameField.getText(), lengthField.getText(), ratingField.getText());
        } else if ("SLength".equals(e.getActionCommand())) {
            ha.sortHikeByLength();
        } else if ("SRating".equals(e.getActionCommand())) {
            ha.sortHikeByRating();
        } else if ("SName".equals(e.getActionCommand())) {
            ha.sortHikeByName();
        }
        ha.updateHikeDisplay();
    }

    public JTextField getLengthField() {
        return lengthField;
    }

    public JTextField getRatingField() {
        return ratingField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getIndexField() {
        return indexField;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }
}
