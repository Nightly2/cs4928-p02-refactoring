package tv.codely.chat_rollout;

import org.junit.jupiter.api.Test;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class RandomPercentageRolloutPolicyTest {

    @Test
    void enablesRolloutWhenRandomValueIsLessThanOrEqualToPercentage() {
        Random stubRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 30; // boundary condition: 30 <= 30
            }
        };
        RandomPercentageRolloutPolicy policy = new RandomPercentageRolloutPolicy(30, stubRandom);
        assertTrue(policy.isEnabled());
    }

    @Test
    void disablesRolloutWhenRandomValueExceedsPercentage() {
        Random stubRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 31; // boundary condition: 31 > 30
            }
        };
        RandomPercentageRolloutPolicy policy = new RandomPercentageRolloutPolicy(30, stubRandom);
        assertFalse(policy.isEnabled());
    }

    @Test
    void rejectsInvalidPercentageValues() {
        assertThrows(IllegalArgumentException.class, () -> new RandomPercentageRolloutPolicy(-1));
        assertThrows(IllegalArgumentException.class, () -> new RandomPercentageRolloutPolicy(101));
    }
}
