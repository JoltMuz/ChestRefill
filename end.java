package io.github.JoltMuz.ChestRefil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;

public class end implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String s, String[] args)
    {
        if (Sender.isOp())
        {
            if (!main.refilling)
            {
                Sender.sendMessage(main.sign + ChatColor.RED + "There is no chest refilling in process.");
            }
            else
            {
                main.refilling = false;
                main.task.cancel();
                Bukkit.broadcastMessage(main.sign + ChatColor.YELLOW + "Chests Refilling has been force-stopped. You can /crclear to clear refilled chests.");
                main.refillRound = 0;

                for (ArmorStand stand : main.armorStands)
                {
                    stand.remove();
                }
                main.armorStands.clear();
            }
        }
        else
        {
            Sender.sendMessage(main.sign + ChatColor.RED + "You must be operator to use this command.");
        }
        return true;
    }
}
