package userauthprogpoe;

import javax.swing.JOptionPane;
import java.util.HashMap;

public class Login {
    private static final HashMap<String, String> userDatabase = new HashMap<>();
    private static boolean loggedIn = false;

    public static void register() {
        String username = JOptionPane.showInputDialog("Enter a username:");
        String password = JOptionPane.showInputDialog("Enter a password:");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
            return;
        }

        if (userDatabase.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists.");
        } else {
            userDatabase.put(username, password);
            JOptionPane.showMessageDialog(null, "Registration successful.");
        }
    }

    public static boolean login() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful.");
            loggedIn = true;
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials.");
            return false;
        }
    }

    public static boolean isUserLoggedIn() {
        return loggedIn;
    }
}
