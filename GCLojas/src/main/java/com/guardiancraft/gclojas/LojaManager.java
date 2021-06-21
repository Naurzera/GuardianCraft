package com.guardiancraft.gclojas;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LojaManager {


    public static boolean hasLoja(String player){
        return Main.shoplist.contains(GCLoja.getGCLoja(player));
    }
    public static boolean isOpen(String player){
        if (hasLoja(player)){
            return GCLoja.getGCLoja(player).isAberta();
        }
        return false;
    }
    public static void delLoja(String player) {
        if (hasLoja(player)) {
            if (Main.getInstance().logs){
                DataManager.registerLog(player,2);
            }
            switchStatus(player,false);
            Main.shoplist.remove(GCLoja.getGCLoja(player));
            try {
                DataManager.updateLojas();
            }catch (NullPointerException ignored){}
            MenuManager.loadInventories();
        }
    }
    public static void setLoja(Player playerr) {
        String player = playerr.getName();
        if (hasLoja(player)) {
            if (Main.getInstance().logs) {
                DataManager.registerLog(player, 3);
            }
            Location location = playerr.getLocation().clone();
            GCLoja.getGCLoja(player).setLocation(location);
            if (Main.registrarnaconfig) DataManager.updateLojas();

            if (Main.getInstance().logs) {
                DataManager.registerLog(player, 4);
            }

            playerr.sendMessage(Main.prefix + Main.redefinida);
        }else{

            Location location = playerr.getLocation().clone();
            GCLoja loja = new GCLoja(player,location,true);
            Main.shoplist.add(loja);
            if (Main.registrarnaconfig) DataManager.updateLojas();

            playerr.sendMessage(Main.prefix + Main.definida);
            if (Main.getInstance().logs){
                DataManager.registerLog(player,1);
            }
        }
        MenuManager.loadInventories();
    }

    public static boolean lojaSegura(String player){
        Location location = GCLoja.getGCLoja(player).getLocation().clone();
        assert location != null;
        assert location.getChunk() != null;
        if (!location.getChunk().isLoaded()) location.getChunk().load();
        double ipsilom = location.getY();
        if (location.getBlock() != null)if (location.getBlock().getType() != Material.AIR)return false;
        Location location1 =  location;
        location1.setY(location.getY()+1);
        if (location1.getBlock() != null)if (location1.getBlock().getType() != Material.AIR)return false;

        for (double y = location.getY()-3; y < ipsilom ; y++) {
            Location verifique = location;
            verifique.setY(y);
            Material type = verifique.getBlock().getType();
            if (type.equals(Material.AIR)) {
                return false;
            }
        }
        int i = Main.getInstance().range;
        double minX = location.getX() - i;
        double maxX = location.getX() + i;
        double minZ = location.getZ() - i;
        double maxZ = location.getZ() + i;
        double minY = location.getY() - i;
        double maxY = location.getY() + i;
        for (double x = minX; x < maxX; x++) {
            for (double z = minZ; z < maxZ; z++) {
                for (double y = minY; y < maxY; y++) {
                    Location local = new Location(location.getWorld(), x, y, z);
                    Material type = local.getBlock().getType();
                    if (type.equals(Material.LAVA) || type.equals(Material.STATIONARY_LAVA)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void teleportarLoja(Player player,String loja){
        if (player.hasPermission(Main.staffperm)) {
            final Location location = GCLoja.getGCLoja(loja).getLocation().clone();
            player.teleport(location);
            player.sendMessage(Main.prefix + Main.teleportado
                    .replaceAll("%p", loja));
            return;
        }
        if (!isOpen(loja)){
            if (!player.getName().equalsIgnoreCase("loja")){
                player.sendMessage(Main.prefix + Main.mensagemfechada);
                return;
            }
        }
        long delay = Main.getInstance().normaldelay * 20;
        if (player.hasPermission(Main.permissiondelay)) {
            delay = Main.getInstance().vipdelay * 20;
        }
        double zx = player.getLocation().getX();
        double zy = player.getLocation().getY();
        double xz = player.getLocation().getZ();
        if (zx<0)zx=zx*-1;
        if (zy<0)zy=zy*-1;
        if (xz<0)xz=xz*-1;
        final double x = zx;
        final double y = zy;
        final double z = xz;
        if (!(delay<1))player.sendMessage(Main.prefix + Main.getInstance().teleportar
                .replaceAll("%s", delay/20 + "")
                .replaceAll("%p", loja));
        new BukkitRunnable() {
            public void run() {
                double xx = player.getLocation().getX();
                double yy = player.getLocation().getY();
                double zz = player.getLocation().getZ();
                if (xx<0)xx=xx*-1;
                if (yy<0)yy=yy*-1;
                if (zz<0)zz=zz*-1;
                if (xx-x < 1 && xx-x > -1) {
                    if (yy-y < 1 && yy-y > -1) {
                        if (zz-z < 1 && zz-z > -1) {
                            final Location location = GCLoja.getGCLoja(loja).getLocation().clone();
                            player.teleport(location);
                            player.sendMessage(Main.prefix + Main.teleportado
                                    .replaceAll("%p", loja));
                            return;
                        } else {
                            player.sendMessage(Main.prefix + Main.semexeu);
                            return;
                        }
                    } else {
                        player.sendMessage(Main.prefix + Main.semexeu);
                        return;
                    }
                } else {
                    player.sendMessage(Main.prefix + Main.semexeu);
                    return;
                }
            }
        }.runTaskLater(Main.getInstance(), delay);
        return;
    }
    public static void switchStatus(String loja, boolean abrir){
        if (hasLoja(loja)){
            GCLoja.getGCLoja(loja).setAberta(abrir);
            MenuManager.switchStatus(loja,abrir);
        }
    }

}
