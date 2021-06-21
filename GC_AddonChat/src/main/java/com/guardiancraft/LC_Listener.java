package com.guardiancraft;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LC_Listener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void playerChatEvent(ChatMessageEvent e) {
        Player sender = e.getSender();
        String staffperm = Main.staffperm;
        if (sender.hasPermission(staffperm)) return;

        if (e.getChannel().getCostPerMessage() > 0){
            if (Main.ativarcobrar){
                if (Main.getInstance().getVault().has(e.getSender(), e.getChannel().getCostPerMessage())){
                    Main.getInstance().getVault().withdrawPlayer(e.getSender(), e.getChannel().getCostPerMessage());
                    if (Main.enviarmensagem){
                        e.getSender().sendMessage(Main.mensagem.replaceAll("%money%",e.getChannel().getCostPerMessage()+""));
                    }
                }else{
                    e.getSender().sendMessage(Main.semmoney.replaceAll("%money%",e.getChannel().getCostPerMessage()+""));
                    e.setCancelled(true);
                }
            }
        }

        if (Main.ativarcensura){
            String mensagem = StringManager.censurar(e.getMessage());
            e.setMessage(mensagem);
        }

        if (Main.ativarantidv){
            boolean temDV = StringManager.temDV(e.getMessage(), e.getSender().getName());
            if (temDV){
                String qD = StringManager.qualDV(e.getMessage());
                if (Main.simularmensagem){
                    e.getRecipients().clear();
                    e.getRecipients().add(e.getSender());
                }else{
                    e.getRecipients().clear();
                    e.getSender().sendMessage(Main.mensagemcancelada.replaceAll("%palavra%",StringManager.qualDV(e.getMessage())));
                }
                for (Player online : Bukkit.getOnlinePlayers()){
                    if (online.hasPermission(staffperm)){
                        for (String msg : Main.mensagemstaff){
                            String m = msg;
                            m = m.replaceAll("%player%", sender.getName());
                            m = m.replaceAll("%palavra%", qD);
                            m = m.replaceAll("%mensagem%", e.getMessage());
                            m = m.replaceAll("&","§");
                            online.sendMessage(m);
                        }
                    }
                }
            }
        }

        if (Main.ativarcapslock){
            boolean capsLockDeMais = StringManager.capsLock(e.getMessage(), Main.porcentagemdecaps);
            if (capsLockDeMais){
                String mensagem = e.getMessage();
                mensagem = mensagem.toLowerCase();
                e.setMessage(mensagem);
            }
        }

    }
}
