package org.btwr.shared_library.util.utils;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class IdUtils {
    public static Identifier ofMC(String path) {
        return Identifier.ofVanilla(path);
    }
    public static Identifier ofSL(String path) {
        return Identifier.of("btwr_sl", path);
    }
    public static Identifier ofDS(String path) {
        return Identifier.of("btwr_ds", path);
    }
    public static Identifier ofBTWR(String path) {
        return Identifier.of("btwr", path);
    }
    public static Identifier ofBWT(String path) {
        return Identifier.of("bwt", path);
    }
    public static Identifier ofTE(String path) {
        return Identifier.of("tough_environment", path);
    }
    public static Identifier ofST(String path) {
        return Identifier.of("sturdy_trees", path);
    }
    public static Identifier ofSS(String path) {
        return Identifier.of("self_sustainable", path);
    }
    public static Identifier ofVG(String path) {
        return Identifier.of("vegehenna", path);
    }

    /**
     * Utility methods to fetch registry entries.
     */
    public static Item grabRaw(Identifier id) {
        return Registries.ITEM.get(id);
    }

    public static Item grabRaw(String itemID) {
        return Registries.ITEM.get(Identifier.ofVanilla(itemID));
    }

    public static Item grabRaw(String namespace, String itemID) {
        return Registries.ITEM.get(Identifier.of(namespace, itemID));
    }
}