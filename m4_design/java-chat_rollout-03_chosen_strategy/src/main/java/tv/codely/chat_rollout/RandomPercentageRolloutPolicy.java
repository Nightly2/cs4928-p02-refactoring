package tv.codely.chat_rollout;

import java.util.Random;

public final class RandomPercentageRolloutPolicy implements RolloutPolicy {
    private final int percentage;
    private final Random random;

    public RandomPercentageRolloutPolicy(int percentage) {
        this(percentage, new Random());
    }

    public RandomPercentageRolloutPolicy(int percentage, Random random) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
        this.random = random;
    }

    @Override
    public boolean isEnabled() {
        return random.nextInt(100) <= percentage;
    }
}
