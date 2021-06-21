package com.guardiancraftbr;

import com.guardiancraftbr.listeners.*;
import com.guardiancraftbr.utils.CM;
import com.guardiancraftbr.utils.Config;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    static Main instance = null;

    public Config encantamentos = new Config(this, "encantamentos.yml");

    public Economy getVault() {
        if (!(Bukkit.getServer().getPluginManager().isPluginEnabled("Vault"))) return null;

        RegisteredServiceProvider<Economy> response = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (response == null) return null;

        return response.getProvider();
    }

    public static Main getInstance(){return instance;}
    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        instance = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"[GC_Placas] Carregando configs...");
        if (!encantamentos.existeConfig()) encantamentos.saveDefaultConfig();
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"[GC_Placas] Carregando encantamentos...");
        CM.loadEnchantments();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"[GC_Placas] Registrando eventos...");
        getServer().getPluginManager().registerEvents(new ListenerEnchant(), this);
        getServer().getPluginManager().registerEvents(new ListenerKit(), this);
        getServer().getPluginManager().registerEvents(new ListenerWarp(), this);
        getServer().getPluginManager().registerEvents(new ListenerRepair(), this);
        getServer().getPluginManager().registerEvents(new ListenerWeather(), this);
        getServer().getPluginManager().registerEvents(new ListenerHealFeed(), this);
        getServer().getPluginManager().registerEvents(new ListenerComando(), this);
        long levou = System.currentTimeMillis()-time;
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"[GC_Placas] Habilitado em "+levou+"ms!");
    }
}
