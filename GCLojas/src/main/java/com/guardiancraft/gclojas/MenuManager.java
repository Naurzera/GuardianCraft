package com.guardiancraft.gclojas;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MenuManager {

    public static ItemStack addBlackStainedGlassPain(Material material, int durability) {

        ItemStack item = new ItemStack(material);
        item.setDurability((short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack addPagCount(int i) {

        ItemStack item = new ItemStack(Material.PAPER, i);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Main.contadornome.replaceAll("%p", "" + i));
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack addButton(boolean proxima) {

        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Main.ppag);
        if (proxima) meta.setDisplayName(Main.npag);
        item.setItemMeta(meta);

        return item;

    }

    public static LinkedHashMap<Integer, Inventory> inventarios = new LinkedHashMap<>();

    public static void loadInventories() {

        inventarios.clear();

        int[] la = {10, 11, 12, 13, 14, 15, 16};
        int[] gp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

        int quantidade = Main.shoplist.size();
        int lojan = 0;

        double eoq = Math.ceil((double) quantidade / 7.0);

        for (int i = 0; i < eoq; i++) {

            if (inventarios.get(i - 1) != null) inventarios.get(i - 1).setItem(17, addButton(true));

            Inventory inv = Bukkit.createInventory(null, 27, Main.getInstance().menutitle.replaceAll("%p", "" + (i + 1)).replaceAll("&", "§"));

            int p = i + 1;

            for (int n : la) {
                if (Main.shoplist.size() > lojan) {
                    String nick = Main.shoplist.get(lojan).getDono();
                    String prefix = Main.getInstance().itemtitle;
                    prefix = prefix.replaceAll("%player%", nick);
                    ItemStack random = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
                    meta.setOwner(nick);
                    meta.setDisplayName(prefix);
                    List<String> lore = new ArrayList<>();
                    for (int m = 0;m<Main.lorestatus.size();m++){
                        String status = Main.statusfechada;
                        if (LojaManager.isOpen(nick)) status = Main.statusaberta;
                        lore.add(Main.lorestatus.get(m)
                                .replaceAll("%status%",status)
                                .replaceAll("&","§"));
                    }
                    meta.setLore(lore);
                    random.setItemMeta(meta);
                    inv.setItem(n, random);
                    lojan++;
                } else {
                    inv.setItem(n, addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
                }
            }
            for (int n : gp) {
                inv.setItem(n, addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
            }

            if (Main.contadorativar) inv.setItem(Main.contador, addPagCount(p));

            if (i != 0) {
                inv.setItem(9, addButton(false));
            } else {
                inv.setItem(9, addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
            }

            inv.setItem(17,
                    addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));

            inventarios.put(i, inv);
        }
        if (inventarios.size() == 1)
            inventarios.get(0).setItem(9, addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
    }
    public static void switchStatus(String loja, boolean abrir){
        if (LojaManager.hasLoja(loja)){
            int[] cb = {10, 11, 12, 13, 14, 15, 16};
            int p = inventarios.size();
            for (int i = 0;i<p;i++){
                Inventory inventory = inventarios.get(i);
                System.out.println(i);
                for (int n : cb){
                    try{
                        if (inventory.getItem(n).getItemMeta().getDisplayName().toLowerCase().contains(loja.toLowerCase())){
                            ItemMeta meta = inventory.getItem(n).getItemMeta();
                            List<String> lore = new ArrayList<>();
                            String status = Main.statusfechada;
                            if (abrir) status = Main.statusaberta;
                            for (int m = 0;m<Main.lorestatus.size();m++){
                                lore.add(Main.lorestatus.get(m)
                                        .replaceAll("%status%",status)
                                        .replaceAll("&","§"));
                            }
                            meta.setLore(lore);
                            ItemStack itemStack = inventory.getItem(n);
                            itemStack.setItemMeta(meta);
                            inventory.setItem(n, itemStack);
                            inventarios.replace(i,inventory);
                        }
                    }catch (NullPointerException ignore){}
                }
            }
        }
    }
}
