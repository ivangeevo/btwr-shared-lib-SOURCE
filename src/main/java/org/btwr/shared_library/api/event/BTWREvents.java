package org.btwr.shared_library.api.event;

import net.minecraft.entity.LivingEntity;

import java.util.function.Consumer;

public class BTWREvents {

    public static final SimpleEvent<Consumer<LivingEntity>> LIVING_TICK = new SimpleEvent<>(
            handlers -> living -> handlers.forEach(c -> c.accept(living))
    );

}