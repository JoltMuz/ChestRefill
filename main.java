package io.github.JoltMuz.ChestRefil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class main extends JavaPlugin implements CommandExecutor, Listener
{
    @Override
    public void onEnable()
    {

        this.getCommand("crstart").setExecutor(this);
        this.getCommand("crstop").setExecutor(new end());
        this.getCommand("crclear").setExecutor(new clearchests());
        this.getCommand("crtiers").setExecutor(new tiers());
        getServer().getPluginManager().registerEvents(this,this);

    }

    @Override
    public void onDisable()
    {

    }

    public static String sign = ChatColor.DARK_GREEN + "Chest Refill" + ChatColor.DARK_GRAY + " 》 ";
    public static ArrayList<BlockState> chests = new ArrayList<>();
    public static ArrayList<ArmorStand> armorStands = new ArrayList<>();
    public static BukkitTask task;
    public static boolean refilling;
    public static int refillRound = 0;

    @Override
    public boolean onCommand(CommandSender Sender, Command command, String s, String[] args)
    {
        if (Sender.isOp() && Sender instanceof Player)
        {
            if (args.length == 3)
            {
                if (refilling)
                {
                    Sender.sendMessage(sign + ChatColor.RED + "An area is already selected for chest refill, try /crstop");
                }
                else
                {
                    try
                    {
                        final int countdown = Integer.parseInt(args[0]);
                        int decrease = Integer.parseInt(args[1]);
                        int totalRounds = Integer.parseInt(args[2]);

                        refilling = true;

                        chests.clear();
                        for ( Chunk chunk : ((Player) Sender).getWorld().getLoadedChunks())
                        {
                            for (BlockState blockState : chunk.getTileEntities())
                            {
                                if (blockState instanceof Chest)
                                {
                                    chests.add(blockState);

                                    ArmorStand armorStand = (ArmorStand) blockState.getWorld().spawnEntity(blockState.getLocation().add(0.5,0.8,0.5), EntityType.ARMOR_STAND);
                                    armorStands.add(armorStand);
                                    armorStand.setCustomNameVisible(true);
                                    armorStand.setCustomName(" ");
                                    armorStand.setVisible(false);
                                    armorStand.setMarker(true);
                                    armorStand.setSmall(true);
                                    armorStand.setGravity(false);

                                }
                            }
                        }
                        chestsrefill.refill();
                        refillRound += 1;

                        task = new BukkitRunnable()
                        {
                            int count = countdown;

                            @Override
                            public void run()
                            {
                                int seconds = count % 60;
                                int minutes = count / 60;

                                String displayMinutes = minutes + "";
                                String displaySeconds = seconds + "";
                                if (minutes < 10)
                                {
                                    displayMinutes = "0" + displayMinutes;
                                }
                                if (seconds<10)
                                {
                                    displaySeconds = "0" + displaySeconds;
                                }
                                String displayCount = displayMinutes + " : " + displaySeconds;
                                for (ArmorStand stand : armorStands)
                                {
                                    stand.setCustomName(ChatColor.GREEN + displayCount);
                                }
                                if (count == 0)
                                {
                                    chestsrefill.refill();
                                    refillRound += 1;
                                    count = countdown- (decrease * refillRound);
                                }
                                if (countdown - (decrease * refillRound) < 1)
                                {
                                    this.cancel();
                                    refilling = false;
                                    refillRound = 0;
                                }
                                if (refillRound == totalRounds)
                                {
                                	this.cancel();
                                    refilling = false;
                                    refillRound = 0;
                                }
                                count = count - 1;
                            }
                        }.runTaskTimer(this, 0L, 20L);
                    }
                    catch (NumberFormatException e)
                    {
                        Sender.sendMessage(sign + ChatColor.RED + "countdown, decrease & rounds must be numbers.");
                    }
                }
            }
            else
            {
                Sender.sendMessage(sign + ChatColor.RED + "Usage: /crstart [countdown] [decrease] [rounds]");
            }
        }
        else
        {
            Sender.sendMessage(sign+ ChatColor.RED + "This command can only be used by operator players.");
        }
        return true;
    }
    @EventHandler
    public void chestBreak(BlockBreakEvent e)
    {
        if (refilling && chests.contains(e.getBlock().getState()))
        {
            e.setCancelled(true);
        }
    }

}
