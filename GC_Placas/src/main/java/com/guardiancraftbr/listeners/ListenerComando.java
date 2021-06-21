package com.guardiancraftbr.listeners;

import com.guardiancraftbr.Main;
import com.guardiancraftbr.utils.CM;
import com.guardiancraftbr.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerComando implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onSignPlace(SignChangeEvent e) {
        if (e.getLine(0).toLowerCase().contains("[player]")) {
            if (!e.getPlayer().hasPermission(CM.staffperm)){
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.RED+"Você não tem permissão para fazer isso.");
            }
            e.setLine(0, ChatColor.DARK_BLUE + "[Player]");
            if (!e.getLine(1).startsWith("/")){
                String linha = e.getLine(1);
                linha = "/"+linha;
                e.setLine(1,linha);
            }
            if (!e.getLine(3).isEmpty()){
                try{
                    String linha4 = e.getLine(3).replace("$","");
                    double custo = Double.parseDouble(linha4);
                    e.setLine(3, "$"+custo);
                }catch (NumberFormatException ee){
                    e.setCancelled(true);
                    e.getBlock().breakNaturally();
                    e.getPlayer().sendMessage("§cValor informado na linha 4 incorreto.");
                }
            }
        }
        if (e.getLine(0).toLowerCase().contains("[console]")) {
            if (!e.getPlayer().hasPermission(CM.staffperm)){
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.RED+"Você não tem permissão para fazer isso.");
            }
            e.setLine(0, ChatColor.DARK_BLUE + "[Console]");
            if (!e.getLine(3).isEmpty()){
                try{
                    String linha4 = e.getLine(3).replace("$","");
                    double custo = Double.parseDouble(linha4);
                    e.setLine(3, "$"+custo);
                }catch (NumberFormatException ee){
                    e.setCancelled(true);
                    e.getBlock().breakNaturally();
                    e.getPlayer().sendMessage("§cValor informado na linha 4 incorreto.");
                }
            }
        }
    }

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().toString().contains("SIGN")) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(0).contains("[Player]")) {
                    if (Cooldown.isInCooldown(e.getPlayer().getUniqueId(), "placas")) return;
                    Cooldown c = new Cooldown(e.getPlayer().getUniqueId(), "placas", 1);
                    c.start();

                    boolean execute = true;
                    if (sign.getLine(3).contains("$")) {
                        execute = false;
                        String linha4 = sign.getLine(3).replace("$", "");
                        double custo = Double.parseDouble(linha4);
                        if (Main.getInstance().getVault().has(e.getPlayer(), custo)) {
                            Main.getInstance().getVault().withdrawPlayer(e.getPlayer(), custo);
                            e.getPlayer().sendMessage(CM.msgmoneycobrado.replaceAll("%money%", "" + custo));
                            execute = true;
                        }
                    }
                    if (execute) {
                        String cmd = sign.getLine(1);
                        e.getPlayer().chat(cmd);
                    } else {
                        e.getPlayer().sendMessage(CM.msgmoney);
                        e.setCancelled(true);
                    }
                }
                if (sign.getLine(0).contains("[Console]")) {
                    if (Cooldown.isInCooldown(e.getPlayer().getUniqueId(), "placas")) return;
                    Cooldown c = new Cooldown(e.getPlayer().getUniqueId(), "placas", 1);
                    c.start();

                    boolean execute = true;
                    if (sign.getLine(3).contains("$")) {
                        execute = false;
                        String linha4 = sign.getLine(3).replace("$", "");
                        double custo = Double.parseDouble(linha4);
                        if (Main.getInstance().getVault().has(e.getPlayer(), custo)) {
                            Main.getInstance().getVault().withdrawPlayer(e.getPlayer(), custo);
                            e.getPlayer().sendMessage(CM.msgmoneycobrado.replaceAll("%money%", "" + custo));
                            execute = true;
                        }
                    }
                    if (execute) {
                        String cmd = sign.getLine(1);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("\\$p",e.getPlayer().getName()));
                    } else {
                        e.getPlayer().sendMessage(CM.msgmoney);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
