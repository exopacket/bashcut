package com.inteliense.bashcut;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class BashcutController {
    @FXML
    private Label cmdLabel;
    @FXML
    private TextArea outputText;

    @FXML
    public void setCmd(String cmd) {
        cmdLabel.setText(cmd);
    }

    @FXML
    protected void scrollBottom() {
        outputText.setScrollTop(Double.MAX_VALUE);
    }

    @FXML
    public void println(String output) {
        outputText.appendText(output + "\n");
        scrollBottom();
    }

}