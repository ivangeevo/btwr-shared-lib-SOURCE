package org.btwr.shared_library.util.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class IdUtils {

    public static Identifier ofMC(String item) { return Identifier.ofVanilla(item); }
    public static Identifier ofDS(String item) { return Identifier.of("btwr_ds", item); }
    public static Identifier ofBTWR(String item) { return Identifier.of("org/btwr", item); }
    public static Identifier ofBWT(String item) { return Identifier.of("bwt", item); }
    public static Identifier ofTE(String item) { return Identifier.of("tough_environment", item); }
    public static Identifier ofST(String item) { return Identifier.of("sturdy_trees", item); }
    public static Identifier ofSS(String item) { return Identifier.of("self_sustainable", item); }
    public static Identifier ofVG(String item) { return Identifier.of("vegehenna", item); }

    /**
     * Utility methods to fetch registry entries.
     */
    public Item grabRawItem(Identifier id) {
        return Registries.ITEM.get(id);
    }

    public Item grabRawItem(String itemID) {
        return Registries.ITEM.get(Identifier.ofVanilla(itemID));
    }

    public Item grabRawItem(String namespace, String itemID) {
        return Registries.ITEM.get(Identifier.of(namespace, itemID));
    }

    public Block grabRawBlock(Identifier id) {
        return Registries.BLOCK.get(id);
    }

    public Block grabRawBlock(String itemID) {
        return Registries.BLOCK.get(Identifier.ofVanilla(itemID));
    }

    public Block grabRawBlock(String namespace, String itemID) {
        return Registries.BLOCK.get(Identifier.of(namespace, itemID));
    }

}