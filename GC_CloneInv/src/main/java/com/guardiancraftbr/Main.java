package com.guardiancraftbr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance(){return instance;}
    public static final Logger LOG = new Logger();

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        sendCsl(ChatColor.GREEN, " ");
        sendCsl(ChatColor.YELLOW,"------------------------------------------");
        sendCsl(ChatColor.GREEN, " ");
        saveDefaultConfig();
        instance = this;
        sendCsl(ChatColor.GREEN,"[GC_CloneInv] Registering main command...");
        registerCommands(Messages.command, new Command(Messages.command));
        registerCommands(Messages.commandarmor, new CommandArmor(Messages.commandarmor));
        long ms = System.currentTimeMillis()-time;
        sendCsl(ChatColor.GREEN,"[GC_CloneInv] GC_CloneInv Loaded successful in "+ms+"ms!");
        sendCsl(ChatColor.GREEN, " ");
        sendCsl(ChatColor.YELLOW,"------------------------------------------");
        sendCsl(ChatColor.GREEN, " ");
    }

    void sendCsl(ChatColor color, String msg){
        getServer().getConsoleSender().sendMessage(color+msg);
    }
    private void registerCommands(String command, BukkitCommand bukkitCommand) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(command, bukkitCommand);
        } catch (Exception e) {
            setEnabled(false);
            throw new RuntimeException("Failed to register commands", e);
        }
    }
}
