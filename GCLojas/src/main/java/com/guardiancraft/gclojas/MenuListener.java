package com.guardiancraft.gclojas;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.SkullMeta;

import static com.guardiancraft.gclojas.MenuManager.inventarios;

public class MenuListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public static void onInventoryClick (InventoryClickEvent e) {
        InventoryView inv = e.getWhoClicked().getOpenInventory();
        try {
            if (inv.getTitle().contains("Lojas")) {
                e.setResult(Event.Result.DENY);
                int i = 0;
                if (inventarios.containsValue(e.getClickedInventory())) {
                    for (int v = i; v < inventarios.size(); v++) {
                        if (inventarios.get(v).equals(e.getClickedInventory())) i = v;
                    }
                }
                if (e.isRightClick() && e.getWhoClicked().hasPermission(Main.staffperm)) return;
                if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                    SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    String deu = meta.getOwner();
                    Player p = (Player) e.getWhoClicked();
                    p.chat("/loja " + deu);
                    p.closeInventory();
                    return;
                }
                try {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Próxima")) {
                        e.getWhoClicked().openInventory(inventarios.get(i + 1));
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Anterior")) {
                        e.getWhoClicked().openInventory(inventarios.get(i - 1));
                    }
                } catch (NullPointerException ignore) {
                }
            }
        }catch (NullPointerException ignored){}
    }
}
