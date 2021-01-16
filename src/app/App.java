package app;

import java.util.Locale;
import java.util.ResourceBundle;

public class App {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourceBundle", Locale.getDefault());
        GeneralController.startApp(resourceBundle);
    }
}