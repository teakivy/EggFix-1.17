package me.teakivy.eggfix;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();

        if (getConfig().getBoolean("check-on-chunk-load")) {
            getServer().getPluginManager().registerEvents(new ChunkLoadEvent(), this);
        }
    }

    @EventHandler
    public void onEggPickup(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        if (!(entity.getType().equals(EntityType.PLAYER))) {
            if(event.getItem().getItemStack().getType().equals(Material.EGG)) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("eggfix")) {
            if(sender instanceof Player) {

                if(!sender.isOp()) {
                    sender.sendMessage(ChatColor.RED + "You need to have OP to run this command!");
                    return true;
                }
            }

            int count = 0;

            for(World world : Bukkit.getWorlds()) {
                for(LivingEntity entity : world.getLivingEntities()) {
                    if (entity.getType() != EntityType.PLAYER && Objects.requireNonNull(entity.getEquipment()).getItemInMainHand().getType() == Material.EGG) {
                        entity.remove();
                        count++;
                    }
                }
            }

            if (sender instanceof Player) {
                sender.sendMessage(
                        "\n" + ChatColor.GRAY + "---------------" + "\n" +
                                ChatColor.YELLOW + "Removed " +
                                ChatColor.GOLD.toString() + ChatColor.BOLD + count +
                                ChatColor.RESET.toString() + ChatColor.YELLOW + " Mobs Holding Eggs!" + "\n" +
                                ChatColor.GRAY + "---------------" + "\n");
                return true;
            }
            sender.sendMessage("Removed " + count + " Mobs Holding Eggs");

            return true;

        }

        return false;
    }
}
