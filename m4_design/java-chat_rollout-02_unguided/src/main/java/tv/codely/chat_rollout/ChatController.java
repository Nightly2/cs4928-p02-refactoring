package tv.codely.chat_rollout;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntSupplier;

public final class ChatController {
    public static final int NEW_CHAT_ROLLOUT_PERCENTAGE = 30;

    private final IntSupplier percentageSupplier;

    public ChatController(IntSupplier percentageSupplier) {
        this.percentageSupplier = percentageSupplier;
    }

    // Convenience default constructor using ThreadLocalRandom
    public ChatController() {
        this(() -> ThreadLocalRandom.current().nextInt(100));
    }

    public String pullMessages() {
        return isNewChatEnabled()
            ? "Messages from the NEW chat system"
            : "Messages from the OLD chat system";
    }

    private boolean isNewChatEnabled() {
        return percentageSupplier.getAsInt() < NEW_CHAT_ROLLOUT_PERCENTAGE;
    }
}
