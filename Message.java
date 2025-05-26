package userauthprogpoe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.swing.JOptionPane;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Message {
    private static final String FILE_NAME = "messages.json";
    private static final int MAX_MESSAGE_LENGTH = 250;
    private static final List<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;
    
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(String messageID, String recipient, String message, String messageHash) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = messageHash;
    }

    public static void startMessaging() {
        if (!Login.isUserLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please log in first.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            String menu = """
                    Choose an option:
                    1) Send Messages
                    2) Show recently sent messages
                    3) Quit
                    """;
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) return;

            switch (input) {
                case "1" -> sendMessages(scanner);
                case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.");
                case "3" -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Exiting program.");
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }

    private static void sendMessages(Scanner scanner) {
        int numMessages = 0;
        try {
            numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient phone number (e.g., +27821234567):");
            if (!checkRecipientCell(recipient)) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number. Must start with +27 and be 12 characters.");
                i--; continue;
            }

            String messageText = JOptionPane.showInputDialog("Enter message (max 250 chars):");
            if (messageText == null || messageText.length() > MAX_MESSAGE_LENGTH) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                i--; continue;
            }

            String messageID = generateMessageID();
            if (!checkMessageID(messageID)) {
                JOptionPane.showMessageDialog(null, "Invalid Message ID generated.");
                continue;
            }

            String messageHash = createMessageHash(messageID, totalMessagesSent, messageText);

            String action = JOptionPane.showInputDialog("""
                    What would you like to do?
                    1) Send Message
                    2) Disregard Message
                    3) Store Message to send later
                    """);

            switch (action) {
                case "1" -> {
                    Message msg = new Message(messageID, recipient, messageText, messageHash);
                    sentMessages.add(msg);
                    totalMessagesSent++;
                    JOptionPane.showMessageDialog(null, msg.printMessage());
                }
                case "2" -> JOptionPane.showMessageDialog(null, "Message disregarded.");
                case "3" -> JOptionPane.showMessageDialog(null, "Message stored for later. (Functionality not implemented)");
                default -> {
                    JOptionPane.showMessageDialog(null, "Invalid action. Message not sent.");
                    i--;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + returnTotalMessages());
        saveMessageToJSON();
    }

    public static boolean checkMessageID(String id) {
        return id != null && id.length() == 10;
    }

    public static boolean checkRecipientCell(String cell) {
        return cell != null && cell.startsWith("+27") && cell.length() == 12;
    }

    public static String createMessageHash(String id, int msgCount, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return id.substring(0, 2) + ":" + msgCount + ":" + firstWord + lastWord;
    }

    public String printMessage() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + message;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static void saveMessageToJSON() {
        try {
            List<Message> allMessages = loadMessagesFromJSON();
            allMessages.addAll(sentMessages);

            Gson gson = new Gson();
            FileWriter writer = new FileWriter(FILE_NAME);
            gson.toJson(allMessages, writer);
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }

    public static List<Message> loadMessagesFromJSON() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return new ArrayList<>();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
            Gson gson = new Gson();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading messages: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }
}
