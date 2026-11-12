import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();

        // Preload Part 3 test data
        new Message(1, "+2734557896", "Did you get the cake?", "Sent");
        new Message(2, "+2738884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        new Message(3, "+2734484567", "Yohoooo, I am at your gate.", "Disregard");
        new Message(4, "0838884567", "It is dinner time !", "Sent");
        new Message(5, "+2738884567", "Ok, I am leaving without you.", "Stored");

        System.out.println("=== USER REGISTRATION ===");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Cellphone (+27...): ");
        String cellPhone = scanner.nextLine();

        String regStatus = loginSystem.registerUser(firstName, lastName, username, password, cellPhone);
        System.out.println(regStatus);

        if (!regStatus.equals("Username and Password successfully captured.")) {
            System.out.println("Registration failed. Exiting application.");
            scanner.close();
            return;
        }

        System.out.println("\n=== USER LOGIN ===");
        System.out.print("Enter Username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter Password: ");
        String loginPassword = scanner.nextLine();

        boolean isAuthenticated = loginSystem.loginUser(loginUsername, loginPassword);
        System.out.println(loginSystem.returnLoginStatus(isAuthenticated));

        if (!isAuthenticated) {
            System.out.println("Login failed. Exiting application.");
            scanner.close();
            return;
        }

        // --- QUICKCHAT MAIN MENU LOOP ---
        System.out.println("\nWelcome to QuickChat");

        boolean running = true;
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("How many messages would you like to enter? ");
                    int numMessages = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");
                        System.out.print("Enter Recipient Cell Number: ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter Message Text: ");
                        String text = scanner.nextLine();

                        Message msg = new Message(i, recipient, text, "Sent");

                        System.out.println(msg.checkRecipientCell());
                        System.out.println(msg.checkMessageLength());

                        System.out.println("\nAction for message:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Select action: ");
                        int action = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println(msg.SentMessage(action));
                        if (action == 1) {
                            System.out.println(msg.printMessages());
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- SENT MESSAGES ---");
                    for (String sm : Message.sentMessages) {
                        System.out.println("- " + sm);
                    }
                    break;

                case 3: // Stored Messages Sub-menu[cite: 11]
                    boolean subRunning = true;
                    while (subRunning) {
                        System.out.println("\n--- STORED MESSAGES MENU ---");
                        System.out.println("a) Display sender and recipient of all stored messages");
                        System.out.println("b) Display the longest stored message");
                        System.out.println("c) Search for a message ID");
                        System.out.println("d) Search for messages by recipient");
                        System.out.println("e) Delete a message using the message hash");
                        System.out.println("f) Display full task report");
                        System.out.println("g) Return to Main Menu");
                        System.out.print("Sub-choice: ");
                        String subChoice = scanner.nextLine().trim().toLowerCase();

                        switch (subChoice) {
                            case "a":
                                System.out.println("\n[Sender & Recipient of Stored Messages]");
                                for (Message m : Message.allMessages) {
                                    if ("Stored".equalsIgnoreCase(m.getFlag())) {
                                        System.out.println("Recipient: " + m.getRecipientCell() + " | Message: " + m.getMessageText());
                                    }
                                }
                                break;
                            case "b":
                                System.out.println("\n[Longest Message]");
                                System.out.println(Message.getLongestMessage());
                                break;
                            case "c":
                                System.out.print("Enter Message ID to search: ");
                                String searchID = scanner.nextLine();
                                System.out.println("Found Message: " + Message.searchMessageByID(searchID));
                                break;
                            case "d":
                                System.out.print("Enter Recipient Number: ");
                                String searchCell = scanner.nextLine();
                                ArrayList<String> foundMsgs = Message.searchMessagesByRecipient(searchCell);
                                System.out.println("Messages for " + searchCell + ": " + foundMsgs);
                                break;
                            case "e":
                                System.out.print("Enter Message Hash to delete: ");
                                String hashToDelete = scanner.nextLine();
                                boolean deleted = Message.deleteMessageByHash(hashToDelete);
                                if (deleted) {
                                    System.out.println("Message successfully deleted.");
                                } else {
                                    System.out.println("Hash not found.");
                                }
                                break;
                            case "f":
                                System.out.println("\n--- FULL TASK REPORT ---");
                                for (Message m : Message.allMessages) {
                                    System.out.println(m.printMessages());
                                    System.out.println("-----------------------------------");
                                }
                                break;
                            case "g":
                                subRunning = false;
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("Exiting QuickChat. Total messages sent: " + Message.returnTotalMessagess());
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }
}