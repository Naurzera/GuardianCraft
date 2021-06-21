package com.guardiancraftbr.listeners;

import com.guardiancraftbr.Main;
import com.guardiancraftbr.utils.CM;
import com.guardiancraftbr.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerEnchant implements org.bukkit.event.Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onSignPlace(SignChangeEvent e){
        if (e.getLine(0).toLowerCase().contains("[enchant]")){
            if (!e.getPlayer().hasPermission(CM.staffperm)){
                e.setCancelled(true);
                e.getBlock().breakNaturally();
                e.getPlayer().sendMessage(ChatColor.RED+"Você não tem permissão para fazer isso.");
            }
            e.setLine(0, ChatColor.DARK_BLUE+"[Enchant]");
            if (!e.getLine(2).isEmpty()){
                String linha = e.getLine(2);
                try {
                    String[] split = linha.split(":");
                    try {
                        short level = Short.parseShort(split[1]);
                    }catch (NumberFormatException ee){
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED+"A linha 3 está incorreta (Utilize "+ChatColor.WHITE+"encantamento:nivel"+ChatColor.RED+")");
                    }
                }catch (ArrayIndexOutOfBoundsException eee){
                    e.setCancelled(true);
                    e.getBlock().breakNaturally();
                    e.getPlayer().sendMessage(ChatColor.RED+"A linha 3 está incorreta (Utilize "+ChatColor.WHITE+"encantamento:nivel"+ChatColor.RED+")");
                }
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
    public void onSignRightClick(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (e.getClickedBlock().getType().toString().contains("SIGN")) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(0).contains("[Enchant]")){
                    if (Cooldown.isInCooldown(e.getPlayer().getUniqueId(), "placas")) return;
                    Cooldown c = new Cooldown(e.getPlayer().getUniqueId(), "placas",1);
                    c.start();
                    for (Enchantment ench : CM.enchants.keySet()){
                        if (sign.getLine(2).contains(CM.enchants.get(ench))){
                            String linha = sign.getLine(2);
                            String[] split = linha.split(":");
                            short level = Short.parseShort(split[1]);
                            if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                                e.setCancelled(true);
                                e.getPlayer().sendMessage(CM.msgsemitem);
                                return;
                            }
                            ItemStack clone = e.getPlayer().getItemInHand().clone();
                            if (clone.getAmount()>1){
                                e.setCancelled(true);
                                e.getPlayer().sendMessage(CM.msgmuitositens);
                                return;
                            }
                            boolean executou = true;
                            if (sign.getLine(3).contains("$")){
                                executou = false;
                                String linha4 = sign.getLine(3).replace("$","");
                                double custo = Double.parseDouble(linha4);
                                if (Main.getInstance().getVault().has(e.getPlayer(), custo)){
                                    Main.getInstance().getVault().withdrawPlayer(e.getPlayer(), custo);
                                    e.getPlayer().sendMessage(CM.msgmoneycobrado.replaceAll("%money%",""+custo));
                                    executou = true;
                                }
                            }
                            if (executou) {
                                clone.addUnsafeEnchantment(ench, level);
                                e.getPlayer().setItemInHand(clone);
                                e.getPlayer().sendMessage(CM.msgencantou
                                        .replaceAll("%enchant%", CM.enchants.get(ench))
                                        .replaceAll("%nivel%", level + ""));
                                return;
                            } else {
                                e.getPlayer().sendMessage(CM.msgmoney);
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

}
