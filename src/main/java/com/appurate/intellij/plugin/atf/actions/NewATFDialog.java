package com.appurate.intellij.plugin.atf.actions;

import javax.swing.*;
import java.awt.event.*;

//TODO Create a drop down with PSI types including XMLSchemas to select from


public class NewATFDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField modelName;
    private JTextField sourceType;
    private JTextField destinationType;

    public NewATFDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (validateInput()) {// TODO: 12/26/2015 Display a related error if there is validation issues
// add your code here
            dispose();
        }
    }

    private boolean validateInput() {
        if (this.modelName == null || this.modelName.getText().isEmpty() ||
                this.sourceType == null || this.sourceType.getText().isEmpty() ||
                this.destinationType == null || this.destinationType.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        NewATFDialog dialog = new NewATFDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public String getModelName() {
        return this.modelName.getText();
    }

    public String getSourceType() {
        return this.sourceType.getText();
    }

    public String getDestinationType() {
        return this.destinationType.getText();
    }
}
