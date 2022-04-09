package org.distantnetwork.duels.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    public ItemBuilder(Material material, int amount){
        this.item = new ItemStack(material, amount);
    }
    public ItemBuilder(Material material){
        this(material, 1);
    }
    public ItemBuilder(ItemStack item){
        this.item=item;
    }

    public ItemBuilder setName(String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setMaterial(Material material){
        item.setType(material);
        return this;
    }
    public ItemBuilder setLore(String... lore){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(Arrays.asList(lore));
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder setLore(ArrayList<String> lore){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder addLoreLine(String line){
        ItemMeta meta = item.getItemMeta();
        List<String> lore;
        if (meta != null) {
            if (meta.hasLore()) {
                lore = new ArrayList<String>() {{
                    addAll(meta.getLore());
                }};
            } else {
                lore = new ArrayList<String>();
            }
            lore.add(line);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder addLoreLine(String line, int pos){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<String>() {{
                addAll(meta.getLore());
            }};
            lore.set(pos, line);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder setAmount(int amount){
        item.setAmount(amount);
        return this;
    }
    public ItemBuilder setEnchantment(Enchantment ench, int level){
        item.addUnsafeEnchantment(ench, level);
        return this;
    }
    public ItemBuilder setEnchantment(Enchantment ench){
        item.addUnsafeEnchantment(ench, 1);
        return this;
    }
    public ItemBuilder setUnbreakable(boolean unbreakable){
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(unbreakable);
        return this;
    }
    public ItemBuilder setDurability(short durability){
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable) {
            Damageable dmg = (Damageable) item.getItemMeta();
            if (dmg.hasDamage()) dmg.setDamage(durability);
            item.setItemMeta(dmg);
        }
        return this;
    }
    public ItemBuilder addItemFlags(ItemFlag... flags){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags);
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder addItemFlags(List<ItemFlag> flags){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags.toArray(new ItemFlag[0]));
            item.setItemMeta(meta);
        }
        return this;
    }
    public ItemBuilder addItemFlags(ArrayList<ItemFlag> flags){
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags.toArray(new ItemFlag[0]));
            item.setItemMeta(meta);
        }
        return this;
    }
    @Deprecated
    public ItemBuilder setSkullOwner(String owner){
        try {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if (meta != null) {
                meta.setOwner(owner);
                item.setItemMeta(meta);
            }
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return this;
    }
    public ItemStack toItem(){
        return item;
    }
    public ItemStack build(){
        return item;
    }
}
