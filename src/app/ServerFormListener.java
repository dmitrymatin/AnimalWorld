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
        } else if (e.getSource() == form.getStopServerButton()) { // todo: refactor the same way as this.onStart(). DONE
            onStop();
        } else if (e.getSource() == form.getExitButton()) { // todo: refactor the same way as this.onStart(). DONE
            onExit();
        }
    }

    private void onStart() {
        try {
            String sPort = form.getPortTextField().getText();
            if (sPort.isBlank()) {
                throw new IllegalArgumentException("порт не должен быть пустой");
            }

            sPort = sPort.trim();

            int port;
            try {
                port = Integer.parseInt(sPort);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("введен неверный порт");
            }

            GeneralController.startServer(port);
            this.form.onStartServer();
            this.logger.logMessage("Сервер успешно запущен");

        } catch (Exception ex) {
            logger.logMessage("Произошла ошибка при запуске сервера: " + ex.getMessage()); // todo: некорректные данные на форму, DONE
        }
    }

    private void onStop() {
        GeneralController.stopServer();
        this.form.onStopServer();
        this.logger.logMessage("Сервер остановлен");
    }

    private void onExit() {
        GeneralController.persistData();
        this.form.onExit();
    }
}
