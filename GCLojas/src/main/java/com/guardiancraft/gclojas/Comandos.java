package com.guardiancraft.gclojas;

import com.guardiancraft.gclojas.utils.Cooldown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("loja")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                if (!player.hasPermission(Main.staffperm)) {
                    if (!(Cooldown.isInCooldown(player.getUniqueId(), "loja"))) {
                        Cooldown c = new Cooldown(player.getUniqueId(), "loja", Main.delayusarcomandosloja);
                        c.start();
                    } else {
                        sender.sendMessage(Main.prefix + Main.msgusarcomandosloja);
                        return false;
                    }
                }
                if (args.length < 1) {
                    sender.sendMessage(Main.prefix + Main.semloja);
                    return false;
                } else {
                    if (LojaManager.hasLoja(args[0])) {
                        if (LojaManager.lojaSegura(args[0])) {
                            LojaManager.teleportarLoja(player, args[0]);
                            return true;
                        } else {
                            if (player.getName().equalsIgnoreCase(args[0]) || player.hasPermission(Main.staffperm)) {
                                LojaManager.teleportarLoja(player, args[0]);
                                player.sendMessage(Main.prefix + Main.inseguradono);
                                return true;
                            } else {
                                player.sendMessage(Main.prefix + Main.insegura);
                                return false;
                            }
                        }
                    }else{
                        player.sendMessage(Main.prefix + Main.lojanaoexiste);
                        return false;
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("delloja")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission(Main.staffperm)) {
                    if (!(Cooldown.isInCooldown(player.getUniqueId(), "loja"))) {
                        Cooldown c = new Cooldown(player.getUniqueId(), "loja", Main.delayusarcomandosloja);
                        c.start();
                    } else {
                        sender.sendMessage(Main.prefix + Main.msgusarcomandosloja);
                        return false;
                    }
                }
                if (args.length==0) {
                    if (LojaManager.hasLoja(player.getName())) {
                        LojaManager.delLoja(player.getName());
                        player.sendMessage(Main.prefix + Main.removida);
                        return true;
                    } else {
                        player.sendMessage(Main.prefix + Main.removeusemloja);
                        return false;
                    }
                }else{
                    if (player.hasPermission(Main.staffperm)) {
                        String loja = args[0];
                        if (LojaManager.hasLoja(loja)) {
                            LojaManager.delLoja(loja);
                            player.sendMessage("§cLoja de " + loja + " removida com sucesso!");
                        } else {
                            player.sendMessage("§cEsse jogador não tem uma loja.");
                            return false;
                        }
                    }else{
                        player.sendMessage("§cUtilize: /fecharloja");
                    }
                }
            } else {
                sender.sendMessage("Apenas jogadores podem executar este comando!");
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("setloja")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission(Main.staffperm)) {
                    if (!(Cooldown.isInCooldown(player.getUniqueId(), "loja"))) {
                        Cooldown c = new Cooldown(player.getUniqueId(), "loja", Main.delayusarcomandosloja);
                        c.start();
                    } else {
                        player.sendMessage(Main.prefix + Main.msgusarcomandosloja);
                        return false;
                    }
                }
                if (Main.permission == null || Main.permission.length() < 1) {
                    LojaManager.setLoja(player);
                    return true;
                } else {
                    if (player.hasPermission(Main.permission)) {
                        LojaManager.setLoja(player);
                        return true;
                    } else {
                        sender.sendMessage(Main.prefix + Main.permcriar);
                        return false;
                    }
                }
            } else {
                sender.sendMessage("Apenas jogadores podem executar este comando!");
                return false;
            }
        }
        if (cmd.getName().equalsIgnoreCase("lojas")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Este comando só pode ser executado por jogadores!");
                return false;
            }
            final Player player = (Player) sender;
            try {
                player.openInventory(MenuManager.inventarios.get(0));
            }catch (NullPointerException swap){
                player.sendMessage("§cNão há nenhuma loja no servidor para ser mostrada atualmente.");
                return false;
            }
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("abrirloja")){
            if (!(sender instanceof  Player)){
                sender.sendMessage("Este comando só pode ser executado por jogadores!");
                return false;
            }
            final Player player = (Player) sender;
            if (!player.hasPermission(Main.staffperm)) {
                if (!(Cooldown.isInCooldown(player.getUniqueId(), "loja"))) {
                    Cooldown c = new Cooldown(player.getUniqueId(), "loja", Main.delayusarcomandosloja);
                    c.start();
                } else {
                    player.sendMessage(Main.prefix + Main.msgusarcomandosloja);
                    return false;
                }
            }
            if (args.length==0){
                if (LojaManager.hasLoja(player.getName())){
                    if (!LojaManager.isOpen(player.getName())){
                        LojaManager.switchStatus(player.getName(),true);
                        player.sendMessage(Main.prefix + Main.msgabriu);
                        return true;
                    }else{
                        player.sendMessage(Main.prefix + Main.msgstatus2);
                        return false;
                    }
                }else{
                    player.sendMessage("§cVocê não tem uma loja.");
                    return false;
                }
            }else {
                if (player.hasPermission(Main.staffperm)) {
                    String loja = args[0];
                    if (LojaManager.hasLoja(loja)) {
                        if (!LojaManager.isOpen(loja)) {
                            LojaManager.switchStatus(loja, true);
                            player.sendMessage(Main.prefix+"Você abriu a loja de "+loja+".");
                            return true;
                        } else {
                            player.sendMessage("§cEssa loja já está aberta!");
                            return false;
                        }
                    } else {
                        player.sendMessage("§cVocê não tem uma loja.");
                        return false;
                    }
                }else{
                    player.sendMessage("§cUtilize: /abrirloja");
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("fecharloja")){
            if (!(sender instanceof Player)){
                sender.sendMessage("Este comando só pode ser executado por jogadores!");
                return false;
            }
            final Player player = (Player) sender;
            if (!player.hasPermission(Main.staffperm)) {
                if (!(Cooldown.isInCooldown(player.getUniqueId(), "loja"))) {
                    Cooldown c = new Cooldown(player.getUniqueId(), "loja", Main.delayusarcomandosloja);
                    c.start();
                } else {
                    player.sendMessage(Main.prefix + Main.msgusarcomandosloja);
                    return false;
                }
            }
            if (args.length==0){
                if (LojaManager.hasLoja(player.getName())){
                    if (LojaManager.isOpen(player.getName())){
                        LojaManager.switchStatus(player.getName(),false);
                        player.sendMessage(Main.prefix+Main.msgfechou);
                        return true;
                    }else{
                        player.sendMessage(Main.prefix + Main.msgstatus1);
                        return false;
                    }
                }else{
                    player.sendMessage("§cVocê não tem uma loja.");
                    return false;
                }
            }else{
                if (player.hasPermission(Main.staffperm)) {
                    String loja = args[0];
                    if (LojaManager.hasLoja(loja)) {
                        if (LojaManager.isOpen(loja)) {
                            LojaManager.switchStatus(loja, false);
                            player.sendMessage(Main.prefix+"Você fechou a loja de "+loja+".");
                            return true;
                        } else {
                            player.sendMessage("§cEssa loja já está fechada!");
                            return false;
                        }
                    } else {
                        player.sendMessage("§cVocê não tem uma loja.");
                        return false;
                    }
                }else{
                    player.sendMessage("§cUtilize: /fecharloja");
                }
            }
        }
        return false;
    }
}
