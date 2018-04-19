package itc.automatas2.gui;

import itc.automatas2.gui.controller.UIController;

import javax.swing.UIManager;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
            Locale.setDefault(new Locale("es", "MX"));
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIController controller = new UIController();
    }
}
