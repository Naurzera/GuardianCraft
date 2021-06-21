package com.guardiancraft.gclojas;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class StaffMenuManager implements Listener {

    public static ItemStack addBlackStainedGlassPain(Material material, int durability) {
        ItemStack item = new ItemStack(material);
        item.setDurability((short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack addItem(Material material, String name, List<String> lore, int durability) {
        ItemStack item = new ItemStack(material);
        item.setDurability((short)durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public static void onInventoryClick(InventoryClickEvent e){
        try{
            if (e.getClickedInventory().getTitle().contains("Lojas")){
                if (e.getClick().isRightClick() && e.getWhoClicked().hasPermission(Main.staffperm)) {
                    e.setResult(Event.Result.DENY);
                    if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                        ItemStack cabeca = e.getCurrentItem();
                        SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
                        String loja = meta.getOwner();
                        Player p = (Player) e.getWhoClicked();
                        Inventory inv = Bukkit.createInventory(null,36, "§cPainel Staff - "+loja);
                        for (int i = 0;i<inv.getSize();i++){
                            inv.setItem(i,addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
                        }
                        inv.setItem(4,cabeca);
                        inv.setItem(19,addItem(Material.STAINED_GLASS_PANE, "§cFechar loja", null, 14));
                        inv.setItem(21,addItem(Material.STAINED_GLASS_PANE, "§aAbrir loja", null, 5));
                        inv.setItem(23,addItem(Material.BARRIER, "§c§lDeletar loja", null, 0));
                        inv.setItem(25,addItem(Material.ACACIA_DOOR_ITEM,"§eIr para a loja",null, 0));
                        p.openInventory(inv);
                        return;
                    }
                }

            }
            if (e.getClickedInventory().getName().contains("Painel Staff")) {
                e.setResult(Event.Result.DENY);
                String loja = e.getClickedInventory().getTitle();
                loja = loja.replaceAll("§cPainel Staff - ","");
                if (e.getWhoClicked().hasPermission(Main.staffperm)){
                    String display = e.getCurrentItem().getItemMeta().getDisplayName();
                    Player player = (Player) e.getWhoClicked();
                    if (display.contains("Fechar loja")) player.chat("/fecharloja "+loja);
                    if (display.contains("Abrir loja")) player.chat("/abrirloja "+loja);
                    if (display.contains("Deletar loja")) player.chat("/delloja "+loja);
                    if (display.contains("Ir para a loja")) player.chat("/loja "+loja);
                }
            }
        }catch (NullPointerException ignored){}
    }

}
