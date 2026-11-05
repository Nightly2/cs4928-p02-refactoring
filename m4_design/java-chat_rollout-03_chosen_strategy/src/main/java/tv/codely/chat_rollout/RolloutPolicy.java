package tv.codely.chat_rollout;

@FunctionalInterface
public interface RolloutPolicy {
    boolean isEnabled();
}
