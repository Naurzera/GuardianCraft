package com.guardiancraft.gclojas;

import com.guardiancraft.gclojas.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataManager {

    public static Config lojas = new Config(Main.getInstance(), "lojas.yml");

    static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static boolean temLoja(String player){
        for (String string : lojas.getStringList("lojas")){
            if (string.startsWith(player)){
                return true;
            }
        }
        return false;
    }
    public static Location getLoja(String player) {
        if (temLoja(player)) {
            for (String string : lojas.getStringList("lojas")) {
                string = string.replaceAll(",","");
                String[] split = string.split("/");
                if (player.equalsIgnoreCase(split[0])) {
                    World world = Bukkit.getWorld(split[1]);
                    double x = Double.parseDouble(split[2]);
                    double y = Double.parseDouble(split[3]);
                    double z = Double.parseDouble(split[4]);
                    float yaw = Float.parseFloat(split[5]);
                    float pitch = Float.parseFloat(split[6]);

                    Location location = new Location(Bukkit.getWorld("world"),0,0,0,0,0);
                    location.setWorld(world);
                    location.setX(x);
                    location.setY(y);
                    location.setZ(z);
                    location.setYaw(yaw);
                    location.setPitch(pitch);
                    return location;
                }
            }
        }
        return null;
    }
    public static String getLojaLog(Location location) {
        if (location==null)return "null";
        String world = location.getWorld().getName();
        String x = formatNumber(location.getX());
        String y = formatNumber(location.getY());
        String z = formatNumber(location.getZ());

        return "["+world+"]"+"X:"+x+"/Y:"+y+"/Z:"+z;
    }
    public static void registerLog(String player, int i){
        LocalDateTime momento = LocalDateTime.now();
        List<String> loga = Main.getInstance().log.getStringList("Eventos");
        switch (i){
            case 1:
                loga.add("["+momento.format(formato)+"] "+player+" criou sua loja: "+getLojaLog(GCLoja.getGCLoja(player).getLocation()));
                Main.getInstance().log.set("Eventos", loga);
                Main.getInstance().log.saveConfig();
                break;
            case 2:
                loga.add("["+momento.format(formato)+"] "+player+" removeu sua loja: "+getLojaLog(GCLoja.getGCLoja(player).getLocation()));
                Main.getInstance().log.set("Eventos", loga);
                Main.getInstance().log.saveConfig();
                break;
            case 3:
                loga.add("["+momento.format(formato)+"] "+player+" alterou a coordenada de sua loja. Antiga:"+getLojaLog(GCLoja.getGCLoja(player).getLocation()));
                Main.getInstance().log.set("Eventos", loga);
                Main.getInstance().log.saveConfig();
                break;
            case 4:
                loga.add("["+momento.format(formato)+"] "+player+" alterou a coordenada de sua loja. Nova:"+getLojaLog(GCLoja.getGCLoja(player).getLocation()));
                Main.getInstance().log.set("Eventos", loga);
                Main.getInstance().log.saveConfig();
                break;
        }
    }
    public static void updateLojas() {
        List<String> lojas = new ArrayList<>();
        for (GCLoja loja : Main.shoplist) {
            Location loc = loja.getLocation();
            String dono = loja.getDono();
            boolean aberta = loja.isAberta();
            String isaberta = "fechada";
            if (aberta) isaberta = "aberta";

            String location = dono + "/" + loc.getWorld().getName() + "/" + formatNumber(loc.getX()) + "/" +
                    formatNumber(loc.getY()) + "/" + formatNumber(loc.getZ()) + "/" +
                    formatNumber(loc.getYaw()) + "/" + formatNumber(loc.getPitch()) +
                    "/"+isaberta;
            lojas.add(location);
        }
        Main.getInstance().lojas.set("lojas", lojas);
        Main.getInstance().lojas.saveConfig();
        Main.getInstance().lojas.reloadConfig();
    }
    public static void removeLoja(GCLoja loja) {
        List<String> lojas = Main.getInstance().lojas.getStringList("lojas");
        lojas.removeIf(shop -> shop.startsWith(loja.getDono()));
        Main.getInstance().lojas.set("lojas", lojas);
        Main.getInstance().lojas.saveConfig();
        Main.getInstance().lojas.reloadConfig();
    }
    private static String formatNumber(final double d) {
        final NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(d);
    }

}
