package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFormListener implements ActionListener {

    private ServerForm form;

    public ServerFormListener(ServerForm form) {
        this.form = form;

        this.form.getStartServerButton().addActionListener(this);
        this.form.getStopServerButton().addActionListener(this);
        this.form.getExitButton().addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.getStartServerButton()) {
            String sPort = form.getPortTextField().getText();
            if (sPort.isBlank()) return;

            sPort.trim();

            int port = 0;
            try {
                port = Integer.parseInt(sPort);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                return;
            }

            boolean serverStarted = GeneralController.startServer(port);
            if (serverStarted) {
                this.form.onStartServer();
                this.form.logMessage("Сервер успешно запущен");
            } else {
                this.form.logMessage("Сервер не может быть запущен");
            }
        } else if (e.getSource() == form.getStopServerButton()) {
            GeneralController.stopServer();
            this.form.onStopServer();
            this.form.logMessage("Сервер остановлен");
        } else if (e.getSource() == form.getExitButton()) {
            GeneralController.persistData();
            this.form.onExit();
        }
    }
}
