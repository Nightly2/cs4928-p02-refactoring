package tv.codely.chat_rollout;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;

public final class DynamicRolloutPolicy implements RolloutPolicy {
    private final BooleanSupplier supplier;
    private final AtomicInteger currentPercentage;

    public DynamicRolloutPolicy(BooleanSupplier supplier) {
        this.supplier = Objects.requireNonNull(supplier, "supplier must not be null");
        this.currentPercentage = null;
    }

    public DynamicRolloutPolicy(int initialPercentage) {
        this.currentPercentage = new AtomicInteger(initialPercentage);
        this.supplier = () -> currentPercentage.get() >= 100;
    }

    public void updatePercentage(int newPercentage) {
        if (currentPercentage != null) {
            currentPercentage.set(newPercentage);
        }
    }

    @Override
    public boolean isEnabled() {
        return supplier.getAsBoolean();
    }
}
