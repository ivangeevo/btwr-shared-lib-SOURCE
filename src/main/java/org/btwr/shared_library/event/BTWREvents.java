package org.btwr.shared_library.event;

import net.minecraft.entity.LivingEntity;
import org.btwr.shared_library.api.event.SimpleEvent;

import java.util.function.Consumer;

public class BTWREvents {

    public static final SimpleEvent<Consumer<LivingEntity>> LIVING_TICK = new SimpleEvent<>(
            handlers -> living -> handlers.forEach(c -> c.accept(living))
    );

}
