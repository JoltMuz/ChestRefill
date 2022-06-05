package io.github.JoltMuz.ChestRefil;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;

public class clearchests implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender Sender, Command command, String s, String[] args)
    {
        if (Sender.isOp())
        {
            if (!main.chests.isEmpty())
            {
                for (BlockState chest : main.chests)
                {
                    ((Chest) chest).getBlockInventory().clear();
                }
                Sender.sendMessage(main.sign + ChatColor.BLUE + "All refilled chests have been cleared.");
                for (ArmorStand stand : main.armorStands)
                {
                    stand.remove();
                }
                main.armorStands.clear();
            }
            else
            {
                Sender.sendMessage(main.sign + ChatColor.RED + "Chests have never been refilled.");
            }
        }
        else
        {
            Sender.sendMessage(main.sign + ChatColor.RED + "You must be operator to use this command.");
        }
        return true;
    }
}
