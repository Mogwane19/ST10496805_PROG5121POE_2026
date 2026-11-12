import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;
    private String flag; 

    // Global static arrays/lists to track data across the session
    public static ArrayList<Message> allMessages = new ArrayList<>();
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    private static int totalMessagesSent = 0;

    // Constructor
    public Message(int messageNumber, String recipientCell, String messageText, String flag) {
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.flag = flag;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        
        categorizeMessage();
    }

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipientCell != null && recipientCell.startsWith("+") && recipientCell.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (messageText == null || messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "";
        }
        String idPrefix = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        String rawHash = idPrefix + ":" + messageNumber + ":" + firstWord + ":" + lastWord;
        return rawHash.toUpperCase();
    }

    private void categorizeMessage() {
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
        allMessages.add(this);

        if ("Sent".equalsIgnoreCase(flag)) {
            sentMessages.add(messageText);
            totalMessagesSent++;
        } else if ("Stored".equalsIgnoreCase(flag)) {
            storedMessages.add(messageText);
            storeMessageJSON();
        } else if ("Disregard".equalsIgnoreCase(flag) || "Disregarded".equalsIgnoreCase(flag)) {
            disregardedMessages.add(messageText);
        }
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                flag = "Sent";
                sentMessages.add(messageText);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                flag = "Disregarded";
                disregardedMessages.add(messageText);
                return "Press 0 to delete the message.";
            case 3:
                flag = "Stored";
                storedMessages.add(messageText);
                storeMessageJSON();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public void storeMessageJSON() {
        String jsonContent = "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"messageNumber\": " + messageNumber + ",\n" +
                "  \"recipient\": \"" + recipientCell + "\",\n" +
                "  \"message\": \"" + messageText + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\"\n" +
                "}";

        try (FileWriter file = new FileWriter("stored_message_" + messageID + ".json")) {
            file.write(jsonContent);
        } catch (IOException e) {
            System.err.println("Error saving message to JSON: " + e.getMessage());
        }
    }

    // --- PART 3 METHODS ---

    public static String getLongestMessage() {
        if (allMessages.isEmpty()) return "No messages available.";
        String longest = allMessages.get(0).messageText;
        for (Message m : allMessages) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest;
    }

    public static String searchMessageByID(String targetID) {
        for (Message m : allMessages) {
            if (m.messageID.equals(targetID)) {
                return m.messageText;
            }
        }
        return "Message ID not found.";
    }

    public static ArrayList<String> searchMessagesByRecipient(String targetRecipient) {
        ArrayList<String> results = new ArrayList<>();
        for (Message m : allMessages) {
            if (m.recipientCell.equals(targetRecipient)) {
                results.add(m.messageText);
            }
        }
        return results;
    }

    public static boolean deleteMessageByHash(String targetHash) {
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).messageHash.equalsIgnoreCase(targetHash)) {
                allMessages.remove(i);
                messageHashes.remove(targetHash);
                return true;
            }
        }
        return false;
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipientCell + "\n" +
               "Message: " + messageText;
    }

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText() { return messageText; }
    public String getFlag() { return flag; }
}