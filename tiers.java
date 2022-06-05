package io.github.JoltMuz.ChestRefil;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class tiers implements CommandExecutor
{
	public static ArrayList<Inventory> tiersList = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender.isOp() && sender instanceof Player)
		{
			if (args.length > 0)
			{
				if (args.length == 1 && args[0].equalsIgnoreCase("add"))
				{
					Inventory inv = Bukkit.createInventory(null, 27);
					tiersList.add(inv);
					((Player) sender).openInventory(inv);
					sender.sendMessage(main.sign + ChatColor.GREEN + " Tier added " + ChatColor.DARK_GREEN + "[Tier " + tiersList.indexOf(inv) + "]");
				}
				else if (args.length == 2)
				{
					if (args[0].equalsIgnoreCase("modify"))
					{
						try
						{
							int tier = Integer.parseInt(args[1]);
							if (tier >= tiersList.size())
							{
								sender.sendMessage(main.sign + ChatColor.RED + "There are total "+  tiersList.size() + " number of tiers. Try /crtiers add");
							}
							else
							{
								((Player)sender).openInventory(tiersList.get(tier));
							}
						}
						catch (NumberFormatException e)
						{
							sender.sendMessage(main.sign + ChatColor.RED + "Tier# must be Integer.");
						}
					}
					else
					{
						sender.sendMessage(main.sign + ChatColor.RED  + "Usage: /crtiers modify");
					}
				}
				else
				{
					sender.sendMessage(main.sign + ChatColor.RED + "Usage: /crtiers [add/modify]");
				}
			}
			else
			{
				sender.sendMessage(main.sign + ChatColor.RED + "Usage: /crtiers [add/modify]");
			}
			
		}
		else
		{
			sender.sendMessage(main.sign + ChatColor.RED + "You must be operator player to use this command.");
		}
		return true;
	}

}
