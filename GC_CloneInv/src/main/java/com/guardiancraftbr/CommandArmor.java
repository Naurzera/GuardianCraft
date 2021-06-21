package com.guardiancraftbr;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class CommandArmor extends BukkitCommand
{
    String comando;
    public CommandArmor(String command)
    {
        super(command);
        comando = command;
    }

    @Override
    public boolean execute(final CommandSender sender, String alias, final String[] args)
    {
        if (sender instanceof Player)
        {
            Player p1 = (Player) sender;
            if (!p1.hasPermission(Messages.permission))
            {
                p1.sendMessage(Format.format(Messages.msgpermission, p1.getName(), comando));
            }
            Player p2 = null;
            try
            {
                p2 = Bukkit.getPlayer(args[0]);
            }catch (Exception e)
            {
                p1.sendMessage(Format.format(Messages.naoeplayer, p1.getName(), comando));
                return false;
            }
            if (!InvCheio.armorCheia(p1.getPlayer()))
            {
                p1.sendMessage(Format.format(Messages.msgerro, p1.getName(), comando));
                return false;
            }
            try {
                p1.getInventory().setHelmet(p2.getInventory().getHelmet());
                p1.getInventory().setChestplate(p2.getInventory().getChestplate());
                p1.getInventory().setLeggings(p2.getInventory().getLeggings());
                p1.getInventory().setBoots(p2.getInventory().getBoots());
            }catch (Exception e) {
                p1.sendMessage(Format.format(Messages.naoeplayer, p1.getName(), comando));
                return false;
            }
            p1.sendMessage(Format.format(Messages.msgsucesso, p1.getName(), comando));
        }
        else
        {
            sender.sendMessage("Este comando está limitado a poder ser utilizado apenas por jogadores in-game.");
        }
        return false;
    }
}
