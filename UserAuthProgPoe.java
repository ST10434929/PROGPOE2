package userauthprogpoe;

import javax.swing.JOptionPane;

public class UserAuthProgPoe {
    public static void main(String[] args) {
        String[] options = {"Register", "Login", "Exit"};
        boolean running = true;

        while (running) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an option", "QuickChat",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> Login.register();
                case 1 -> {
                    if (Login.login()) {
                        Message.startMessaging();
                    }
                }
                case 2 -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Exiting program.");
                }
                default -> running = false;
            }
        }
    }
}
