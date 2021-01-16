package app;

import java.awt.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class ServerForm extends Frame {
    private ResourceBundle rb;

    // server control part
    Panel controlPanel = new Panel();
    Label portLabel = new Label();
    TextField portTextField = new TextField();
    Button startServerButton = new Button();
    Button stopServerButton = new Button();
    Button exitButton = new Button();

    // logging part
    Panel loggingPanel = new Panel();
    TextArea loggingTextArea = new TextArea();

    public ServerForm(String title, ResourceBundle rb) throws HeadlessException {
        super(title);
        this.rb = rb;

        portLabel.setText(rb.getString("PORT_LABEL"));
        startServerButton.setLabel(rb.getString("START_LABEL"));
        stopServerButton.setLabel(rb.getString("STOP_LABEL"));
        exitButton.setLabel(rb.getString("EXIT_LABEL"));

        setSize(560, 380);
        setLayout(null);

        initialiseControlPart();
        initialiseLoggingPart();

        setVisible(true);
    }

    private void initialiseControlPart() {
        controlPanel.setBounds(30, 30, 500, 90);
        controlPanel.setBackground(Color.LIGHT_GRAY);

        portLabel.setBounds(10, 10, 30, 20);
        portTextField.setBounds(50, 10, 120, 20);
        startServerButton.setBounds(10, 50, 120, 30);
        stopServerButton.setBounds(140, 50, 120, 30);
        exitButton.setBounds(270, 50, 120, 30);

        portTextField.setText(GeneralController.getProperties().getProperty("DEFAULT_PORT"));
        stopServerButton.setEnabled(false);

        controlPanel.add(portLabel);
        controlPanel.add(portTextField);
        controlPanel.add(startServerButton);
        controlPanel.add(stopServerButton);
        controlPanel.add(exitButton);

        controlPanel.setLayout(null);
        add(controlPanel);
    }

    private void initialiseLoggingPart() {
        loggingPanel.setBounds(30, 125, 500, 240);
        loggingPanel.setBackground(Color.LIGHT_GRAY);

        loggingTextArea.setBounds(10, 10, 480, 220);
        loggingTextArea.setEditable(false);

        loggingPanel.add(loggingTextArea);

        loggingPanel.setLayout(null);
        add(loggingPanel);
    }

    public void onStartServer() {
        startServerButton.setEnabled(false);
        stopServerButton.setEnabled(true);
        exitButton.setEnabled(false);

        portTextField.setEditable(false);
    }

    public void onStopServer() {
        startServerButton.setEnabled(true);
        stopServerButton.setEnabled(false);
        exitButton.setEnabled(true);

        portTextField.setEditable(true);
    }

    public void onExit() {
        dispose();
    }

    public void logMessageToForm(String message) {
        loggingTextArea.setText(message);
    }


    public Button getStartServerButton() {
        return startServerButton;
    }

    public Button getStopServerButton() {
        return stopServerButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public TextField getPortTextField() {
        return portTextField;
    }

    public TextArea getTextArea() {
        return loggingTextArea;
    }
}