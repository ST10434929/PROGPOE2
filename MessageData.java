package userauthprogpoe;

public class MessageData {
    private String messageID;
    private String messageHash;
    private String recipient;
    private String message;

    public MessageData(String messageID, String messageHash, String recipient, String message) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.message = message;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}
