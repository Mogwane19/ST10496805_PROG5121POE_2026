import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class MessageTest {

    @Test
    public void testSentMessagesArrayPopulated() {
        Message.sentMessages.clear();
        new Message(1, "+2734557896", "Did you get the cake?", "Sent");
        new Message(4, "0838884567", "It is dinner time!", "Sent");
        
        assertTrue(Message.sentMessages.contains("Did you get the cake?"));
        assertTrue(Message.sentMessages.contains("It is dinner time!"));
    }

    @Test
    public void testDisplayLongestMessage() {
        Message.allMessages.clear();
        new Message(1, "+2734557896", "Short msg", "Sent");
        new Message(2, "+2738884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        
        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.getLongestMessage());
    }

    @Test
    public void testSearchMessageByID() {
        Message m = new Message(4, "0838884567", "It is dinner time!", "Sent");
        String found = Message.searchMessageByID(m.getMessageID());
        assertEquals("It is dinner time!", found);
    }

    @Test
    public void testSearchMessagesByRecipient() {
        Message.allMessages.clear();
        new Message(2, "+2738884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        new Message(5, "+2738884567", "Ok, I am leaving without you.", "Stored");

        ArrayList<String> recipientMsgs = Message.searchMessagesByRecipient("+2738884567");
        assertEquals(2, recipientMsgs.size());
    }

    @Test
    public void testDeleteMessageByHash() {
        Message m = new Message(2, "+2738884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        String hash = m.getMessageHash();
        
        boolean deleted = Message.deleteMessageByHash(hash);
        assertTrue(deleted);
    }
}