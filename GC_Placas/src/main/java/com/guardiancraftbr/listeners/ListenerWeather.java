package com.guardiancraftbr.listeners;

import com.guardiancraftbr.Main;
import com.guardiancraftbr.utils.CM;
import com.guardiancraftbr.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerWeather implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onSignPlace(SignChangeEvent e) {
        if (e.getLine(0).toLowerCase().contains("[weather]")) {
            if (!e.getPlayer().hasPermission(CM.staffperm)){
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.RED+"Você não tem permissão para fazer isso.");
            }
            e.setLine(0, ChatColor.DARK_BLUE + "[Weather]");
            if (!e.getLine(1).isEmpty()){
                if (e.getLine(1).equalsIgnoreCase("sol")){
                    e.setLine(1,ChatColor.GREEN+e.getLine(1));
                } else {
                    if (e.getLine(1).equalsIgnoreCase("chuva")) {
                        e.setLine(1, ChatColor.GREEN + e.getLine(1));
                    } else {
                        e.setCancelled(true);
                        e.getBlock().breakNaturally();
                        e.getPlayer().sendMessage("§cValor informado na linha 2 incorreto. (Use sol ou chuva)");
                    }
                }
            }else{
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage("§cValor informado na linha 2 incorreto. (Use sol ou chuva)");
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
    }

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().toString().contains("SIGN")) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(0).contains("[Weather]")) {
                    if (Cooldown.isInCooldown(e.getPlayer().getUniqueId(), "placas")) return;
                    Cooldown c = new Cooldown(e.getPlayer().getUniqueId(), "placas", 1);
                    c.start();
                    boolean execute = true;
                    if (sign.getLine(3).contains("$")){
                        execute = false;
                        String linha4 = sign.getLine(3).replace("$","");
                        double custo = Double.parseDouble(linha4);
                        if (Main.getInstance().getVault().has(e.getPlayer(), custo)){
                            Main.getInstance().getVault().withdrawPlayer(e.getPlayer(), custo);
                            e.getPlayer().sendMessage(CM.msgmoneycobrado.replaceAll("%money%",""+custo));
                            execute = true;
                        }
                    }
                    if (execute){
                        if (sign.getLine(1).contains("Sol")){
                            // Assim muda apenas para o player
                            //e.getPlayer().setPlayerWeather(WeatherType.CLEAR);
                            e.getPlayer().getWorld().setThundering(false);
                            e.getPlayer().getWorld().setStorm(false);
                            e.getPlayer().sendMessage(CM.msgsol);
                        }
                        if (sign.getLine(1).contains("Chuva")){
                            // Assim muda apenas para o player
                            //e.getPlayer().setPlayerWeather(WeatherType.DOWNFALL);
                            e.getPlayer().getWorld().setThundering(true);
                            e.getPlayer().getWorld().setStorm(true);
                            e.getPlayer().sendMessage(CM.msgchuva);
                        }
                    } else {
                        e.getPlayer().sendMessage(CM.msgmoney);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
