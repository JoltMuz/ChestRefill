package io.github.JoltMuz.ChestRefil;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import java.util.Random;

public class chestsrefill
{
    
    static Location location;

    public static void refill()
    {

        for (BlockState chest : main.chests)
        {
   
            ((Chest) chest).getBlockInventory().addItem(tiers.tiersList.get(main.refillRound).getItem(new Random().nextInt(tiers.tiersList.get(main.refillRound).getSize() -1)));
            ((Chest) chest).getBlockInventory().addItem(tiers.tiersList.get(main.refillRound).getItem(new Random().nextInt(tiers.tiersList.get(main.refillRound).getSize() -1)));
            ((Chest) chest).getBlockInventory().addItem(tiers.tiersList.get(main.refillRound).getItem(new Random().nextInt(tiers.tiersList.get(main.refillRound).getSize() -1)));
            ((Chest) chest).getBlockInventory().addItem(tiers.tiersList.get(main.refillRound).getItem(new Random().nextInt(tiers.tiersList.get(main.refillRound).getSize() -1)));
            ((Chest) chest).getBlockInventory().addItem(tiers.tiersList.get(main.refillRound).getItem(new Random().nextInt(tiers.tiersList.get(main.refillRound).getSize() -1)));
            if (location == null)
            {
                location = chest.getLocation();
            }
        }
        
        location.getWorld().playSound(location,Sound.CHEST_OPEN,100,1);
        Bukkit.broadcastMessage(main.sign + ChatColor.GREEN + "Chests have been refilled. " + ChatColor.DARK_GREEN + "[Tier " + main.refillRound + "]");
    }
}
