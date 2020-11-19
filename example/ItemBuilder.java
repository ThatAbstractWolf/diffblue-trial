package net.amongmc.core.utils.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.amongmc.common.C;
import net.amongmc.core.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;

import java.util.*;

public final class ItemBuilder {

    private static final int LORE_LENGTH = 80;

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private HashMap<Enchantment, Integer> enchantments = null;

    private HashMap<LoreType, LinkedList<String>> lores = null;
    private LoreType[] loreOrder = new LoreType[]{ LoreType.ENCHANTMENT, LoreType.GENERAL };

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    private ItemBuilder(ItemBuilder builder) {
        this.itemStack = builder.itemStack.clone();
        this.itemMeta = builder.itemMeta.clone();
        this.lores = builder.lores;
        this.enchantments = builder.enchantments;
        this.loreOrder = builder.loreOrder;
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder of(ItemBuilder builder) {
        return new ItemBuilder(builder);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    public static ItemBuilder of(Material material, short durability) {
        ItemStack stack = new ItemStack(material);
        stack.setDurability(durability);
        return new ItemBuilder(stack);
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder type(Material type) {
        itemStack.setType(type);
        return this;
    }

    public ItemBuilder durability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(C.translate(name));
        return this;
    }

    public String name() {
        return itemMeta.getDisplayName();
    }
    
    public void setItemStack(ItemStack itemStack) {
    	this.itemStack = itemStack;
	}

    public ItemBuilder lore(List<String> strings) {
        return lore(LoreType.GENERAL, strings);
    }

    public ItemBuilder lore(String... strings) {
        return lore(LoreType.GENERAL, strings);
    }

    public ItemBuilder multilineLore(String string) {
        return multilineLore(LoreType.GENERAL, "", string);
    }

    public ItemBuilder multilineLore(LoreType type, String string) {
        return multilineLore(type, "", string);
    }

    public ItemBuilder multilineLore(String prefix, String string) {
        return multilineLore(LoreType.GENERAL, prefix, string);
    }

    public ItemBuilder multilineLore(LoreType type, String prefix, String string) {
        return lore(type, StringUtil.getMultiLinedTextArray(string, LORE_LENGTH, prefix));
    }

    public ItemBuilder multilineNameLore(String string) {
        return multilineNameLore(LoreType.GENERAL, "", string);
    }

    public ItemBuilder multilineNameLore(String prefix, String string) {
        return multilineNameLore(LoreType.GENERAL, prefix, string);
    }

    public ItemBuilder multilineNameLore(LoreType type, String string) {
        return multilineNameLore(type, "", string);
    }

    public ItemBuilder multilineNameLore(LoreType type, String prefix, String string) {
        String[] array = StringUtil.getMultiLinedTextArray(string, LORE_LENGTH, prefix);
        String[] newArray = new String[array.length - 1];
        for (int i = 0; i < array.length; i++) {
            if (i == 0) itemMeta.setDisplayName(array[i]);
            else newArray[i - 1] = array[i];
        }
        return lore(type, newArray);
    }

    public ItemBuilder lore(LoreType type, String... strings) {
        if (lores == null)
            lores = Maps.newHashMap();

        LinkedList<String> list = lores.getOrDefault(type, Lists.newLinkedList());
        list.addAll(Arrays.asList(strings));
        lores.put(type, list);
        return this;
    }

    public ItemBuilder lore(LoreType type, List<String> strings) {
        if (lores == null)
            lores = Maps.newHashMap();

        LinkedList<String> list = lores.getOrDefault(type, Lists.newLinkedList());
        list.addAll(strings);
        lores.put(type, list);
        return this;
    }

    public ItemBuilder cutLore(LoreType type, int byHowMuch) {
        if (lores == null) return this;
        LinkedList<String> list = lores.getOrDefault(type, Lists.newLinkedList());
        for (int i = 0; i < byHowMuch; i++)
            list.removeLast();
        lores.put(type, list);
        return this;
    }

    public ItemBuilder cutLore(int byHowMuch) {
        return cutLore(LoreType.GENERAL, byHowMuch);
    }

    public ItemBuilder sortLore(LoreType... types) {
        loreOrder = types;
        return this;
    }

    public ItemBuilder enchant(org.bukkit.enchantments.Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder skullOwner(Player player) {
        return skullOwner(player.getUniqueId());
    }

    public ItemBuilder skullOwner(OfflinePlayer player) {
        return skullOwner(player.getUniqueId());
    }

    public ItemBuilder skullOwner(UUID uuid) {
        if (itemMeta instanceof SkullMeta) {
            ((SkullMeta) itemMeta).setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        }

        return this;
    }

    public ItemBuilder removePatterns() {
        if (itemMeta instanceof BannerMeta) {
            System.out.println("removing pattern");
            for (int i = 0; i < ((BannerMeta) itemMeta).numberOfPatterns(); i++) {
                ((BannerMeta) itemMeta).getPattern(i);
            }
        }

        return this;
    }

    public ItemBuilder clearNBT() {
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        for (NamespacedKey key : container.getKeys()) {
            container.remove(key);
        }
        return this;
    }

    public ItemBuilder skullOwner(String string) {
        if (itemMeta instanceof SkullMeta) {
            ((SkullMeta) itemMeta).setOwner(string);
        }

        return this;
    }

    public ItemStack build() {
        if (lores != null) {
            LinkedList<String> lore = Lists.newLinkedList();
            for (LoreType loreType : loreOrder) {
                if (lores.containsKey(loreType)) {
                    LinkedList<String> strings = lores.get(loreType);
                    for (String line : strings) {
                        lore.add(C.translate(line));
                    }
                }
            }

            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemBuilder setCustomModelData(ItemStack itemStack, int value) {
        itemMeta.setCustomModelData(value);
        return this;
    }
}
