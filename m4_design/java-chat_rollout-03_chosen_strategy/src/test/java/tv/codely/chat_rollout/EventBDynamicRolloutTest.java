package tv.codely.chat_rollout;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class EventBDynamicRolloutTest {

    @Test
    void should_adapt_rollout_dynamically_at_runtime_without_restarting() {
        var toggle = new AtomicBoolean(false);
        var dynamicPolicy = new DynamicRolloutPolicy(toggle::get);
        var controller = new ChatController(dynamicPolicy);

        assertEquals("Messages from the OLD chat system", controller.pullMessages());

        toggle.set(true);

        assertEquals("Messages from the NEW chat system", controller.pullMessages());
    }

    @Test
    void should_allow_runtime_percentage_ramp_up() {
        var dynamicPolicy = new DynamicRolloutPolicy(10);
        var controller = new ChatController(dynamicPolicy);

        assertEquals("Messages from the OLD chat system", controller.pullMessages());

        dynamicPolicy.updatePercentage(100);

        assertEquals("Messages from the NEW chat system", controller.pullMessages());
    }
}
