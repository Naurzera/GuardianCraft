package com.guardiancraftbr.utils;

import com.guardiancraftbr.Main;
import org.bukkit.enchantments.Enchantment;

import java.util.LinkedHashMap;
import java.util.List;

public class CM {
    public static LinkedHashMap<Enchantment, String> enchants = new LinkedHashMap<>();

    public static final String staffperm = Main.getInstance().getConfig().getString("permissao-staff");
    public static final String msgencantou = Main.getInstance().getConfig().getString("item-encantado").replaceAll("&","§");
    public static final String msgmoney = Main.getInstance().getConfig().getString("sem-money").replaceAll("&","§");
    public static final String cmdkit = Main.getInstance().getConfig().getString("comando-dar-kit");
    public static final String msgreparouum = Main.getInstance().getConfig().getString("mensagem-reparou-um").replaceAll("&","§");
    public static final String msgreparou = Main.getInstance().getConfig().getString("mensagem-reparou").replaceAll("&","§");
    public static final String msgnaoreparou = Main.getInstance().getConfig().getString("mensagem-nao-reparou").replaceAll("&","§");
    public static final String msgsol = Main.getInstance().getConfig().getString("mensagem-sol").replaceAll("&","§");
    public static final String msgchuva = Main.getInstance().getConfig().getString("mensagem-chuva").replaceAll("&","§");
    public static final String msgheal = Main.getInstance().getConfig().getString("msg-heal").replaceAll("&","§");
    public static final String msgfeed = Main.getInstance().getConfig().getString("msg-feed").replaceAll("&","§");
    public static final String msgsemitem = Main.getInstance().getConfig().getString("sem-item").replaceAll("&","§");
    public static final String msgmuitositens = Main.getInstance().getConfig().getString("muitos-itens").replaceAll("&","§");
    public static final String msgmoneycobrado = Main.getInstance().getConfig().getString("mensagem-money-cobrado").replaceAll("&","§");
    public static final String msgrecebeukit = Main.getInstance().getConfig().getString("mensagem-kit").replaceAll("&","§");
    public static final List<String> reparar = Main.getInstance().getConfig().getStringList("reparar-itens");


    public static final String ARROW_DAMAGE = Main.getInstance().encantamentos.getString("ARROW_DAMAGE");
    public static final String ARROW_FIRE = Main.getInstance().encantamentos.getString("ARROW_FIRE");
    public static final String ARROW_INFINITE = Main.getInstance().encantamentos.getString("ARROW_INFINITE");
    public static final String ARROW_KNOCKBACK = Main.getInstance().encantamentos.getString("ARROW_KNOCKBACK");
    public static final String DAMAGE_ALL = Main.getInstance().encantamentos.getString("DAMAGE_ALL");
    public static final String DAMAGE_ARTHROPODS = Main.getInstance().encantamentos.getString("DAMAGE_ARTHROPODS");
    public static final String DAMAGE_UNDEAD = Main.getInstance().encantamentos.getString("DAMAGE_UNDEAD");
    public static final String DEPTH_STRIDER = Main.getInstance().encantamentos.getString("DEPTH_STRIDER");
    public static final String DIG_SPEED = Main.getInstance().encantamentos.getString("DIG_SPEED");
    public static final String DURABILITY = Main.getInstance().encantamentos.getString("DURABILITY");
    public static final String FIRE_ASPECT = Main.getInstance().encantamentos.getString("FIRE_ASPECT");
    public static final String KNOCKBACK = Main.getInstance().encantamentos.getString("KNOCKBACK");
    public static final String LOOT_BONUS_BLOCKS = Main.getInstance().encantamentos.getString("LOOT_BONUS_BLOCKS");
    public static final String LOOT_BONUS_MOBS = Main.getInstance().encantamentos.getString("LOOT_BONUS_MOBS");
    public static final String LUCK = Main.getInstance().encantamentos.getString("LUCK");
    public static final String LURE = Main.getInstance().encantamentos.getString("LURE");
    public static final String OXYGEN = Main.getInstance().encantamentos.getString("OXYGEN");
    public static final String PROTECTION_ENVIRONMENTAL = Main.getInstance().encantamentos.getString("PROTECTION_ENVIRONMENTAL");
    public static final String PROTECTION_EXPLOSIONS = Main.getInstance().encantamentos.getString("PROTECTION_EXPLOSIONS");
    public static final String PROTECTION_FALL = Main.getInstance().encantamentos.getString("PROTECTION_FALL");
    public static final String PROTECTION_FIRE = Main.getInstance().encantamentos.getString("PROTECTION_FIRE");
    public static final String PROTECTION_PROJECTILE = Main.getInstance().encantamentos.getString("PROTECTION_PROJECTILE");
    public static final String SILK_TOUCH = Main.getInstance().encantamentos.getString("SILK_TOUCH");
    public static final String THORNS = Main.getInstance().encantamentos.getString("THORNS");
    public static final String WATER_WORKER = Main.getInstance().encantamentos.getString("WATER_WORKER");

    public static void loadEnchantments(){
        if (!enchants.isEmpty()) enchants.clear();

        enchants.put(Enchantment.ARROW_DAMAGE, ARROW_DAMAGE);
        enchants.put(Enchantment.ARROW_FIRE, ARROW_FIRE);
        enchants.put(Enchantment.ARROW_INFINITE, ARROW_INFINITE);
        enchants.put(Enchantment.ARROW_KNOCKBACK, ARROW_KNOCKBACK);
        enchants.put(Enchantment.DAMAGE_ALL, DAMAGE_ALL);
        enchants.put(Enchantment.DAMAGE_ARTHROPODS, DAMAGE_ARTHROPODS);
        enchants.put(Enchantment.DAMAGE_UNDEAD, DAMAGE_UNDEAD);
        enchants.put(Enchantment.DEPTH_STRIDER, DEPTH_STRIDER);
        enchants.put(Enchantment.DIG_SPEED, DIG_SPEED);
        enchants.put(Enchantment.DURABILITY, DURABILITY);
        enchants.put(Enchantment.FIRE_ASPECT, FIRE_ASPECT);
        enchants.put(Enchantment.KNOCKBACK, KNOCKBACK);
        enchants.put(Enchantment.LOOT_BONUS_BLOCKS, LOOT_BONUS_BLOCKS);
        enchants.put(Enchantment.LOOT_BONUS_MOBS, LOOT_BONUS_MOBS);
        enchants.put(Enchantment.LUCK, LUCK);
        enchants.put(Enchantment.LURE, LURE);
        enchants.put(Enchantment.OXYGEN, OXYGEN);
        enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, PROTECTION_ENVIRONMENTAL);
        enchants.put(Enchantment.PROTECTION_EXPLOSIONS, PROTECTION_EXPLOSIONS);
        enchants.put(Enchantment.PROTECTION_FALL, PROTECTION_FALL);
        enchants.put(Enchantment.PROTECTION_FIRE, PROTECTION_FIRE);
        enchants.put(Enchantment.PROTECTION_PROJECTILE, PROTECTION_PROJECTILE);
        enchants.put(Enchantment.SILK_TOUCH, SILK_TOUCH);
        enchants.put(Enchantment.THORNS, THORNS);
        enchants.put(Enchantment.WATER_WORKER, WATER_WORKER);
    }

}
