package tv.codely.chat_rollout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChatControllerTest {

    @Test
    void returnsNewChatMessagesWhenRolloutPolicyIsEnabled() {
        ChatController controller = new ChatController(() -> true);
        assertEquals("Messages from the NEW chat system", controller.pullMessages());
    }

    @Test
    void returnsOldChatMessagesWhenRolloutPolicyIsDisabled() {
        ChatController controller = new ChatController(() -> false);
        assertEquals("Messages from the OLD chat system", controller.pullMessages());
    }

    @Test
    void defaultConstructorPreservesBackwardCompatibility() {
        ChatController controller = new ChatController();
        String result = controller.pullMessages();
        assertTrue(
            result.equals("Messages from the NEW chat system") ||
                result.equals("Messages from the OLD chat system")
        );
    }
}
