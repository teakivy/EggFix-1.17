package me.teakivy.eggfix;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class ChunkLoadEvent implements Listener {

    @EventHandler
    public void onLoad(org.bukkit.event.world.ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        for (LivingEntity entity : chunk.getLivingEntities()) {
            if (entity.getType() != EntityType.PLAYER) {
				if (Objects.requireNonNull(entity.getEquipment()).getItemInMainHand().getType() == Material.EGG) {
					entity.remove();
				}
            }
        }
    }

}