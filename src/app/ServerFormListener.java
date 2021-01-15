package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFormListener implements ActionListener {

    private ServerForm form;
    private Logger logger;

    public ServerFormListener(ServerForm form, Logger logger) {
        this.form = form;
        this.logger = logger;

        this.form.getStartServerButton().addActionListener(this);
        this.form.getStopServerButton().addActionListener(this);
        this.form.getExitButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.getStartServerButton()) {
            onStart();
        } else if (e.getSource() == form.getStopServerButton()) {
            onStop();
        } else if (e.getSource() == form.getExitButton()) {
            onExit();
        }
    }

    private void onStart() {
        GeneralController.startServer(form.getPortTextField().getText());
    }

    private void onStop() {
        GeneralController.stopServer();
    }

    private void onExit() {
        GeneralController.persistData();
        this.form.onExit();
    }
}
