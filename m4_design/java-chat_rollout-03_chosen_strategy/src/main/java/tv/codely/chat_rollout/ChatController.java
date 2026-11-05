package tv.codely.chat_rollout;

public final class ChatController {
    public static final int NEW_CHAT_ROLLOUT_PERCENTAGE = 30;

    private final RolloutPolicy rolloutPolicy;

    public ChatController(RolloutPolicy rolloutPolicy) {
        this.rolloutPolicy = rolloutPolicy;
    }

    public ChatController() {
        this(new RandomPercentageRolloutPolicy(NEW_CHAT_ROLLOUT_PERCENTAGE));
    }

    public String pullMessages() {
        return rolloutPolicy.isEnabled()
            ? "Messages from the NEW chat system"
            : "Messages from the OLD chat system";
    }
}
