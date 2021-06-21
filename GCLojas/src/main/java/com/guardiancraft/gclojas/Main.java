package com.guardiancraft.gclojas;

import com.guardiancraft.gclojas.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static com.guardiancraft.gclojas.MenuManager.loadInventories;

public class Main extends JavaPlugin {

    static Main instancia = null;
    public static Main getInstance(){return instancia;}

    public Config config = new Config(this, "config.yml");
    public Config lojas = new Config(this, "lojas.yml");
    public Config log = new Config(this, "log.txt");

    public static String prefix = null;
    public static String permission = null;
    public static String permissiondelay = null;
    public String menutitle = null;
    public String itemtitle = null;
    public int normaldelay = 4;
    public int vipdelay = 2;
    public String teleportar = null;
    public static String teleportado = null;
    public static String staffperm = null;
    public static String insegura = null;
    public static String inseguradono = null;
    public static String semexeu = null;
    public static String semloja = null;
    public boolean logs = false;
    public static String permcriar = null;
    public int range = 0;
    public static String definida = null;
    public static String redefinida = null;
    public static String removida = null;
    public static String removeusemloja = null;
    public static String lojanaoexiste = null;

    public static String contadornome = null;
    public static int contador = 4;
    public static boolean contadorativar = false;
    public static String msgusarcomandosloja = null;
    public static int delayusarcomandosloja = 4;

    public static List<String>lorestatus = new ArrayList<>();
    public static String statusaberta = null;
    public static String statusfechada = null;
    public static String mensagemfechada = null;

    public static String msgstatus1 = null;
    public static String msgstatus2 = null;
    public static String msgabriu = null;
    public static String msgfechou = null;

    public static String ppag = null;
    public static String npag = null;

    public static boolean registrarnaconfig = false;

    public static List<GCLoja> shoplist = new ArrayList<>();

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        instancia = this;

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new StaffMenuManager(), this);

        if (!config.existeConfig()){
            config.saveDefaultConfig();
            config.reloadConfig();
        }
        if (!lojas.existeConfig())lojas.saveDefaultConfig();
        if (!log.existeConfig())log.saveDefaultConfig();

        sendCsl(ChatColor.GREEN,"[Kidarun_SetLoja]"+" Carregando lojas...");
        registerLojas();

        sendCsl(ChatColor.GREEN,"[Kidarun_SetLoja]"+" Carregando recursos adicionais...");
        if (!registerConfig()) return;
        registerCommands();

        sendCsl(ChatColor.GREEN,"[Kidarun_SetLoja]"+" Carregando inventários...");
        loadInventories();

        long demorou = System.currentTimeMillis()-time;
        getServer().getConsoleSender().sendMessage(""+ ChatColor.GREEN+"[GC_Setloja] Ativado em "+demorou+"ms");

    }

    @Override
    @EventHandler (priority = EventPriority.LOWEST)
    public void onDisable() {
        long time = System.currentTimeMillis();
        sendCsl(ChatColor.GREEN,"[GC_Setloja]"+" Salvando recursos...");
        sendCsl(ChatColor.GREEN,"[GC_Setloja]"+" Salvando lojas...");
        DataManager.updateLojas();
        long demorou = System.currentTimeMillis()-time;
        sendCsl(ChatColor.GREEN,"[GC_Setloja] Desativado com sucesso em "+demorou+"ms");
    }

    void sendCsl(ChatColor color, String msg){
        getServer().getConsoleSender().sendMessage(color+msg);
    }
    boolean registerConfig() {
        try {
            prefix = config.getString("prefixo").replaceAll("&", "§");
            permission = config.getString("permissao-para-criar-loja").replaceAll("&", "§");
            permissiondelay = config.getString("permissao-teleporte-com-delay-reduzido").replaceAll("&", "§");
            menutitle = config.getString("titulo-menu").replaceAll("&", "§");
            itemtitle = config.getString("titulo-cabeca").replaceAll("&", "§");
            normaldelay = config.getInt("delay-teleporte");
            vipdelay = config.getInt("delay-teleporte-vip");
            teleportar = config.getString("mensagem-teleportar").replaceAll("&", "§");
            teleportado = config.getString("mensagem-teleportado").replaceAll("&", "§");
            staffperm = config.getString("permissao-staff").replaceAll("&", "§");
            insegura = config.getString("loja-insegura").replaceAll("&", "§");
            inseguradono = config.getString("loja-insegura-dono").replaceAll("&", "§");
            semexeu = config.getString("mensagem-se-mexeu").replaceAll("&", "§");
            semloja = config.getString("mensagem-sem-loja").replaceAll("&", "§");
            logs = config.getBoolean("registrar-logs");
            permcriar = config.getString("mensagem-sem-permissao-para-criar-loja").replaceAll("&", "§");
            range = config.getInt("range-verificar-lava");
            definida = config.getString("loja-definida").replaceAll("&", "§");
            redefinida = config.getString("loja-redefinida").replaceAll("&", "§");
            removida = config.getString("loja-removida").replaceAll("&", "§");
            removeusemloja = config.getString("sem-loja-remover").replaceAll("&", "§");
            lojanaoexiste = config.getString("sem-loja").replaceAll("&", "§");
            contadornome = config.getString("contador-de-paginas.nome").replaceAll("&", "§");
            contador = config.getInt("contador-de-paginas.slot");
            contadorativar = config.getBoolean("contador-de-paginas.ativar");
            msgusarcomandosloja = config.getString("msg-usar-comandos-loja").replaceAll("&", "§");
            delayusarcomandosloja = config.getInt("delay-usar-comandos-loja");
            lorestatus = config.getStringList("lore-cabeca");
            statusaberta = config.getString("status-aberta").replaceAll("&", "§");
            statusfechada = config.getString("status-fechada").replaceAll("&", "§");
            mensagemfechada = config.getString("mensagem-fechada").replaceAll("&", "§");
            msgstatus1 = config.getString("msg-status1").replaceAll("&", "§");
            msgstatus2 = config.getString("msg-status2").replaceAll("&", "§");
            msgabriu = config.getString("msg-abriu").replaceAll("&", "§");
            msgfechou = config.getString("msg-fechou").replaceAll("&", "§");
            registrarnaconfig = config.getBoolean("registrar-lojas-na-config-ao-setar");
            ppag = config.getString("name-previous-page").replaceAll("&","§");
            npag = config.getString("name-next-page").replaceAll("&","§");
        } catch (NullPointerException ee) {
            sendCsl(ChatColor.RED, "[GC_Setloja] Há falta de informação na config.yml. Encerrando o plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    void registerLojas(){
        shoplist.clear();
        for (String loja : lojas.getStringList("lojas")){
            String[] split = loja.split("/");
            String player = split[0];
            Location location = DataManager.getLoja(player);
            boolean isAberta = split[7].equalsIgnoreCase("aberta");
            GCLoja shop = new GCLoja(player,location,isAberta);
            shoplist.add(shop);
        }
    }
    void registerCommands(){
        getCommand("setloja").setExecutor(new Comandos());
        getCommand("delloja").setExecutor(new Comandos());
        getCommand("loja").setExecutor(new Comandos());
        getCommand("lojas").setExecutor(new Comandos());
        getCommand("fecharloja").setExecutor(new Comandos());
        getCommand("abrirloja").setExecutor(new Comandos());
    }

}
