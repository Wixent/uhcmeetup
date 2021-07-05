package net.enganxe.meetupuhc.utils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class ItemBuilder {
    protected ItemStack is;

    protected ItemMeta im;

    public ItemBuilder(ItemStack itemStack) {
        this.is = new ItemStack(itemStack);
    }

    public ItemBuilder(Material material) {
        this.is = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        this.is = new ItemStack(material, amount);
    }

    public ItemBuilder setDurability(int durability) {
        this.is.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setUnbrekeable(boolean b) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.setUnbreakable(b);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addAtributte(Attribute attribute, AttributeModifier modifier) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.addAttributeModifier(attribute, modifier);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setCustomModelData(int model) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.setCustomModelData(model);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setCustomModelData(int model, boolean b) {
        if (!b)
            return this;
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.setCustomModelData(model);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.setDisplayName(name);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.addEnchant(enchantment, level, true);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addUnsafeEnchant(Enchantment enchantment, int level) {
        this.is.addUnsafeEnchantment(enchantment, level);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addEnchants(Map<Enchantment, Integer> enchantments) {
        this.im = this.is.getItemMeta();
        if (!enchantments.isEmpty())
            for (Enchantment ench : enchantments.keySet()) {
                assert this.im != null;
                this.im.addEnchant(ench, enchantments.get(ench), true);
            }
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemflag) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.addItemFlags(itemflag);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.im = this.is.getItemMeta();
        assert this.im != null;
        this.im.setLore(lore);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemStack build() {
        return this.is;
    }
}
