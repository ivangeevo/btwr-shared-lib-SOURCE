package btwr.btwr_sl.lib.gui;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Intercepts how vanilla handles FOV by removing the values
 * of attributes from provided namespaces, from the total attribute value.
 * This is a safe way of reversing the effect of speed penalties on the
 * client's FOV, and should be compatible with other mods.
 */
public class FOVManager {
    /**
     * Instance of FOVManager
     */
    private static final FOVManager INSTANCE = new FOVManager();
    /**
     * List of namespaces to remove attributes from FOV calculations
     */
    private static HashMap<String,Namespace> namespaces = new HashMap<>();

    /**
     * Returns FOVManager instance
     */
    public static FOVManager getInstance() {
        return INSTANCE;
    }

    /**
     * Lambda condition test that determines namespace exclusion
     * from the total speed attribute value
     */
    @FunctionalInterface
    public interface ExclusionCondition {
        boolean test();
    }

    /**
     * Represents a mod namespace, and condition in which it's attributes
     * would be excluded from the total speed attribute value
     */
    static public class Namespace {
        private ExclusionCondition condition;
        private String namespace;

        /**
         * Constructor that always excludes a given namespace
         * @param namespace Mod namespace
         */
        public Namespace(String namespace) {
            this.condition = () -> true;
            this.namespace = namespace;
        }

        /**
         * Constructor that excludes a given namespace of provided condition passes
         * @param condition Exclusion condition
         * @param namespace Mod namespace
         */
        public Namespace(ExclusionCondition condition, String namespace) {
            this.condition = condition;
            this.namespace = namespace;
        }

        public String getNamespace() {
            return this.namespace;
        }
    }

    /**
     * Offsets provided speed attribute value based on user configuration.
     * If there is no attribute instance, it will just return an unmodified attribute value.
     * @param player PlayerEntity to pull attribute from
     * @param value Attribute value to offset
     * @return Modified attribute value, if conditions pass
     */
    public double offsetAttribute(PlayerEntity player, double value) {
        // Get attribute instance
        EntityAttributeInstance speedAttrib = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speedAttrib != null) {
            // Return modified attribute value
            return getAttributeValueFiltered(speedAttrib);
        }
        return value;
    }

    /**
     * Retrieves all attributes that are intended for exclusion from the
     * attribute total, and reverses their operations, effectively removing
     * their contributions to the total
     * @param attribute Attribute to filter
     * @return Attribute value with namespaces excluded
     */
    public float getAttributeValueFiltered(EntityAttributeInstance attribute) {
        double currentValue = attribute.getValue();

        Map<EntityAttributeModifier.Operation, Set<EntityAttributeModifier>> operationToModifiers = Stream.of(
                EntityAttributeModifier.Operation.values()).collect(
                Collectors.toMap(Function.identity(), operation -> Sets.newHashSet(), (o1, o2) -> o1,
                        () -> Maps.newEnumMap(EntityAttributeModifier.Operation.class)
                ));

        // Gets modifiers that are in namespace map and their condition passes
        attribute.getModifiers()
                .stream()
                .filter(this::shouldInclude)
                .forEach(modifier -> operationToModifiers.get(modifier.operation()).add(modifier));

        // Append all modifiers
        for (EntityAttributeModifier attributeModifier : operationToModifiers.get(EntityAttributeModifier.Operation.ADD_VALUE)) {
            currentValue -= attributeModifier.value();
        }

        for (EntityAttributeModifier attributeModifier : operationToModifiers.get(
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)) {
            currentValue -= currentValue / attributeModifier.value();
        }

        for (EntityAttributeModifier attributeModifier : operationToModifiers.get(
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) {
            currentValue /= 1.0 + attributeModifier.value();
        }

        return (float) attribute.getAttribute().value().clamp(currentValue);
    }

    /**
     * Tests to see if modifier should be filtered (excluded from attribute value)
     * @param modifier Modifier to test
     * @return Test result
     */
    private boolean shouldInclude(EntityAttributeModifier modifier) {
        // Get namespace from modifier
        String namespaceString = modifier.id().getNamespace();
        Namespace namespace = namespaces.get(namespaceString);
        if (namespace == null) return false;

        // Return test result
        return namespace.condition.test();
    }

    /**
     * Adds namespace to exclusion list.
     * @param namespace Namespace object
     */
    public void addNamespace(Namespace namespace) {
        namespaces.put(namespace.getNamespace(), namespace);
    }

    /**
     * Adds namespace to exclusion list.
     * @param namespaceString Mod namespace string
     */
    public void addNamespace(String namespaceString) {
        addNamespace(new Namespace(namespaceString));
    }
}
