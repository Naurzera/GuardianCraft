package com.guardiancraftbr;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvCheio {

    public static boolean invVazio(Player p){

        if (p.getInventory().getHelmet() != null) return false;
        if (p.getInventory().getChestplate() != null) return false;
        if (p.getInventory().getLeggings() != null) return false;
        if (p.getInventory().getBoots() != null) return false;
        for (ItemStack item : p.getInventory().getContents())
        {
            if (item != null) return false;
        }
        return true;

    }
    public static boolean armorCheia(Player p){

        if (p.getInventory().getHelmet() != null) return false;
        if (p.getInventory().getChestplate() != null) return false;
        if (p.getInventory().getLeggings() != null) return false;
        if (p.getInventory().getBoots() != null) return false;
        return true;

    }

}
