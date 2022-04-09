package org.distantnetwork.duels.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.distantnetwork.duels.builders.InventoryBuilder;
import org.distantnetwork.duels.builders.ItemBuilder;
import org.distantnetwork.duels.utils.ArenaOneVOne;

public class MapSelector extends InventoryBuilder {
    public MapSelector(Player opponent) {
        super(54, "Duel with " + opponent.getName());
        for (int i = 0; i < getInventory().getSize(); i++) {
            setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").build());
        }
        int i = 10;
        for (ArenaOneVOne map : ArenaOneVOne.getArenas()) {
            setItem(i, map.getItem(), player -> {
                player.closeInventory();
                ArenaOneVOne arena = ArenaOneVOne.getArenas().get(ArenaOneVOne.getArenas().indexOf(map));
                player.teleport(arena.getSpawn1());

            });
            i++;
        }
    }
}
