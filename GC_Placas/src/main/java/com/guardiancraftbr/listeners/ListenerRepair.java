package com.guardiancraftbr.listeners;

import com.guardiancraftbr.Main;
import com.guardiancraftbr.utils.CM;
import com.guardiancraftbr.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerRepair implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onSignPlace(SignChangeEvent e) {
        if (e.getLine(0).toLowerCase().contains("[repair]")) {
            if (!e.getPlayer().hasPermission(CM.staffperm)){
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.RED+"Você não tem permissão para fazer isso.");
            }
            e.setLine(0, ChatColor.DARK_BLUE + "[Repair]");

            if (!e.getLine(1).isEmpty()){
                boolean igual;
                igual = e.getLine(1).equalsIgnoreCase("hand") || e.getLine(1).equalsIgnoreCase("all");
                if (!igual){
                    e.setCancelled(true);
                    e.getBlock().breakNaturally();
                    e.getPlayer().sendMessage(ChatColor.RED+"Valor informado na linha 2 incorreto. Use \"Hand\" ou \"All\"");
                }
            }else{
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.RED+"Você deixou a linha 2 em branco!. Use \"Hand\" ou \"All\"");
            }

            if (!e.getLine(3).isEmpty()) {
                try {
                    String linha4 = e.getLine(3).replace("$", "");
                    double custo = Double.parseDouble(linha4);
                    e.setLine(3, "$" + custo);
                } catch (NumberFormatException ee) {
                    e.setCancelled(true);
                    e.getBlock().breakNaturally();
                    e.getPlayer().sendMessage(ChatColor.RED+"Valor informado na linha 4 incorreto.");
                }
            }
        }
    }

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().toString().contains("SIGN")) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(0).contains("[Repair]")) {
                    if (Cooldown.isInCooldown(e.getPlayer().getUniqueId(), "placas")) return;
                    Cooldown c = new Cooldown(e.getPlayer().getUniqueId(), "placas", 1);
                    c.start();
                    boolean execute = true;
                    double custo = 0;
                    if (sign.getLine(3).contains("$")){
                        execute = false;
                        String linha4 = sign.getLine(3).replace("$","");
                        custo = Double.parseDouble(linha4);
                        if (Main.getInstance().getVault().has(e.getPlayer(), custo)){
                            Main.getInstance().getVault().withdrawPlayer(e.getPlayer(), custo);
                            execute = true;
                        }
                    }
                    if (execute){
                        if (sign.getLine(1).equalsIgnoreCase("hand")){
                            if (!repairHand(e.getPlayer())) Main.getInstance().getVault().depositPlayer(e.getPlayer(), custo);
                            else if (custo>0) e.getPlayer().sendMessage(CM.msgmoneycobrado.replaceAll("%money%",""+custo));
                        }
                        if (sign.getLine(1).equalsIgnoreCase("all")){
                            if (!repairAll(e.getPlayer())) Main.getInstance().getVault().depositPlayer(e.getPlayer(), custo);
                            else if (custo>0) e.getPlayer().sendMessage(CM.msgmoneycobrado.replaceAll("%money%",""+custo));
                        }
                    } else {
                        e.getPlayer().sendMessage(CM.msgmoney);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    boolean repairAll(Player p) {
        boolean reparou = false;
        for (int i = 0; i < 36; i++) {
            try {
                ItemStack item = p.getInventory().getItem(i);
                if (item == null || item.getType() == Material.AIR) continue;
                String nome = item.getType().toString().toUpperCase();
                for (String reparar : CM.reparar){
                    if (nome.contains(reparar.toUpperCase())){
                        if (item.getDurability() != 0) {
                            item.setDurability(Short.parseShort("0"));
                            reparou = true;
                        }
                    }
                }
            }catch (NullPointerException ignored){}
        }
        if (reparou) {
            p.sendMessage(CM.msgreparou);
        } else {
            p.sendMessage(CM.msgnaoreparou);
        }
        return reparou;
    }
    boolean repairHand(Player p) {
        boolean reparou = false;
            try {
                ItemStack item = p.getItemInHand();
                String nome = item.getType().toString().toUpperCase();
                for (String reparar : CM.reparar){
                    if (nome.contains(reparar.toUpperCase())){
                        if (item.getDurability() != 0) {
                            item.setDurability(Short.parseShort("0"));
                            reparou = true;
                        }
                    }
                }
            }catch (NullPointerException ignored){}
        if (reparou) {
            p.sendMessage(CM.msgreparouum);
        } else {
            p.sendMessage(CM.msgnaoreparou);
        }
        return reparou;
    }

}
