package com.guardiancraft;

import com.guardiancraft.utils.Config;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public Config log = new Config(this, "log.txt");

    static Main instance = null;
    public static Main getInstance(){return instance;}

    public static String staffperm = null;

    public static boolean ativarcensura = true;
    public static String censurar = null;
    public static List<String> censurarpalavras = new ArrayList<>();

    public static boolean ativarantidv = false;
    public static boolean cancelarproibida = false;
    public static boolean simularmensagem = false;
    public static List<String> mensagemstaff = new ArrayList<>();
    public static List<String> ignore = new ArrayList<>();
    public static List<String> dv = new ArrayList<>();
    public static String mensagem = null;
    public static boolean enviarmensagem = false;
    public static String semmoney = null;
    public static boolean ativarcobrar = true;
    public static String mensagemcancelada = null;

    public static boolean ativarcapslock = false;
    public static double porcentagemdecaps = 0.0;

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        instance = this;
        consoleSend("§a[GC_AddonChat] Carregando config principal...");
        saveDefaultConfig();
        reloadConfig();
        loadConfig();
        consoleSend("§a[GC_AddonChat] Registrando eventos...");
        getServer().getPluginManager().registerEvents(new LC_Listener(), this);
        consoleSend("§a[GC_AddonChat] Carregando log...");
        if (!log.existeConfig()) log.saveDefaultConfig();
        log.reloadConfig();
        time = System.currentTimeMillis()-time;
        consoleSend("§a[GC_AddonChat] Plugin habilitado em "+time+"ms");
    }

    public Economy getVault() {
        if (!(Bukkit.getServer().getPluginManager().isPluginEnabled("Vault"))) return null;

        RegisteredServiceProvider<Economy> response = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (response == null) return null;

        return response.getProvider();
    }

    void consoleSend(String msg){getServer().getConsoleSender().sendMessage(msg);}
    void loadConfig(){
        staffperm = getConfig().getString("Permissao-bypass");

        ativarcensura = getConfig().getBoolean("Censurar.ativar-modulo");
        censurarpalavras = getConfig().getStringList("Censurar.censurar-palavras");
        censurar = getConfig().getString("Censurar.substituir-por").replaceAll("&","§");

        ativarantidv = getConfig().getBoolean("AntiDV.ativar-modulo");
        cancelarproibida = getConfig().getBoolean("AntiDV.mensagem-com-palavra-proibida.cancelar");
        simularmensagem = getConfig().getBoolean("AntiDV.mensagem-com-palavra-proibida.simular-mensagem-para-quem-enviou");
        mensagemstaff = getConfig().getStringList("AntiDV.mensagem-com-palavra-proibida.mensagem-para-staffs");
        ignore = getConfig().getStringList("AntiDV.ignorar-caracteres");
        dv = getConfig().getStringList("AntiDV.palavras-proibidas");

        ativarcobrar = getConfig().getBoolean("cobrar-mensagem.ativar-modulo");
        mensagem = getConfig().getString("cobrar-mensagem.mensagem").replaceAll("&","§");
        semmoney = getConfig().getString("cobrar-mensagem.mensagem-sem-money").replaceAll("&","§");
        enviarmensagem = getConfig().getBoolean("cobrar-mensagem.enviar-mensagem");

        ativarcapslock = getConfig().getBoolean("CapsLock.ativar-modulo");
        porcentagemdecaps = getConfig().getDouble("CapsLock.porcentagem-limite-de-letras-maiusculas");

        mensagemcancelada = getConfig().getString("AntiDV.mensagem-com-palavra-proibida.mensagem-cancelada").replaceAll("&","§");

    }
}
