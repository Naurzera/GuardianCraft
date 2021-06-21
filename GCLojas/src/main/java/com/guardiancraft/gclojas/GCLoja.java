package com.guardiancraft.gclojas;

import org.bukkit.Location;

public class GCLoja {
    final String Dono;
    Location location;
    boolean aberta;

    /**
     *
     * @param dono should be not null
     * @param location should be not null
     * @param aberta should be not null
     */
    public GCLoja(String dono, Location location, boolean aberta){
        this.Dono = dono;
        this.location = location;
        this.aberta = aberta;
    }
    public String getDono() {
        return this.Dono;
    }
    public Location getLocation() {
        return this.location;
    }
    public boolean isAberta() {
        return this.aberta;
    }
    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public static GCLoja getGCLoja(String dono){
        for (GCLoja loja : Main.shoplist){
            if (loja.getDono().equalsIgnoreCase(dono)){
                return loja;
            }
        }
        return null;
    }
}
