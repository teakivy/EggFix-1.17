package me.teakivy.eggfix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    ArrayList<UUID> mobList = new ArrayList<UUID>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

    @EventHandler
    public void onDeaths(EntityDeathEvent event) {
        UUID entityID = event.getEntity().getUniqueId();
        if (mobList.contains(entityID)) {
            event.setDroppedExp(0);
            event.getDrops().clear();
            mobList.remove(entityID);
        }
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("eggfix")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You need to have OP to run this command!");
                return true;
            }
            int count = 0;
            for (Entity entity : Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getEntities()) {
                if (entity.getType() != EntityType.PLAYER) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity lentity = (LivingEntity) entity;
                        if (Objects.requireNonNull(lentity.getEquipment()).getItemInMainHand().getType() == Material.EGG) {
                            UUID entityID = lentity.getUniqueId();
                            mobList.add(entityID);
                            lentity.setHealth(0);
                            count++;
                        }
                    }
                }
            }
            sender.sendMessage("\n" + ChatColor.GRAY + "---------------" + "\n" + ChatColor.YELLOW + "Removed " + ChatColor.GOLD.toString() + ChatColor.BOLD + count + ChatColor.RESET.toString() + ChatColor.YELLOW + " Mobs Holding Eggs!" + "\n" + ChatColor.GRAY + "---------------" + "\n");

        }

        return false;
    }
}
