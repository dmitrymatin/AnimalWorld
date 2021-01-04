package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

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

            GeneralController.startServer(port); // this won't throw exceptions because of multithreading inside startServer()
            this.form.onStartServer();
            this.logger.logMessage("Сервер успешно запущен " + LocalDateTime.now());

        } catch (Exception ex) {
            logger.logMessage("Произошла ошибка при запуске сервера: " + ex.getMessage()); // todo: некорректные данные на форму, DONE
        }
    }

    private void onStop() {
        GeneralController.stopServer();
        this.form.onStopServer();
    }

    private void onExit() {
        GeneralController.persistData();
        this.form.onExit();
    }
}
