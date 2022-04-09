package org.distantnetwork.duels.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.distantnetwork.duels.utils.Config.ConfigurationManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DuelPlayer {
    private Player player;
    private UUID uuid;
    private boolean creatingArena;
    private boolean inDuel;

    private int losses;
    private int wins;
    private int kills;
    private int deaths;

    private List<UUID> duelInvites = new ArrayList<>();
    private List<UUID> duelRequests = new ArrayList<>();

    public DuelPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        File file = ConfigurationManager.getFileFile("playerdata.yml");
        if (file == null) return;
        FileConfiguration config = ConfigurationManager.getConfig(file);
        if (config == null) return;
        if (config.contains(uuid.toString() + ".losses")) this.losses = config.getInt(uuid.toString() + ".losses");
        if (config.contains(uuid.toString() + ".wins")) this.wins = config.getInt(uuid.toString() + ".wins");
        if (config.contains(uuid.toString() + ".kills")) this.kills = config.getInt(uuid.toString() + ".kills");
        if (config.contains(uuid.toString() + ".deaths")) this.deaths = config.getInt(uuid.toString() + ".deaths");
        if (config.contains(uuid.toString() + ".duelInvites")) config.getStringList(uuid.toString() + ".duelInvites").forEach(uuid -> {
            this.duelInvites.add(UUID.fromString(uuid));
        });
        if (config.contains(uuid.toString() + ".duelRequests")) config.getStringList(uuid.toString() + ".duelRequests").forEach(uuid -> {
            this.duelRequests.add(UUID.fromString(uuid));
        });
        if (config.contains(uuid.toString() + ".inDuel")) this.inDuel = config.getBoolean(uuid.toString() + ".inDuel");
        if (config.contains(uuid.toString() + ".creatingArena")) this.creatingArena = config.getBoolean(uuid.toString() + ".creatingArena");
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public boolean isCreatingArena() {
        return creatingArena;
    }

    public void setCreatingArena(boolean creatingArena) {
        this.creatingArena = creatingArena;
    }

    public void addDuelInvite(UUID uuid) {
        this.duelInvites.add(uuid);
    }

    public void removeDuelInvite(UUID uuid) {
        this.duelInvites.remove(uuid);
    }

    public boolean hasDuelInvite(UUID uuid) {
        return this.duelInvites.contains(uuid);
    }

    public List<UUID> getDuelInvites() {
        return this.duelInvites;
    }

    public void addDuelRequest(UUID uuid) {
        this.duelRequests.add(uuid);
    }

    public void removeDuelRequest(UUID uuid) {
        this.duelRequests.remove(uuid);
    }

    public boolean hasDuelRequest(UUID uuid) {
        return this.duelRequests.contains(uuid);
    }

    public List<UUID> getDuelRequests() {
        return this.duelRequests;
    }

    public boolean isInDuel() {
        return this.inDuel;
    }

    public void setInDuel(boolean inDuel) {
        this.inDuel = inDuel;
    }

    public UUID getUUID() {
        return this.uuid;
    }



    public DuelPlayer save() {
        File file = ConfigurationManager.getFileFile("playerdata.yml");
        if (file == null) return null;
        FileConfiguration config = ConfigurationManager.getConfig(file);
        if (config == null) return null;
        config.set(uuid.toString() + ".losses", losses);
        config.set(uuid.toString() + ".wins", wins);
        config.set(uuid.toString() + ".kills", kills);
        config.set(uuid.toString() + ".deaths", deaths);
        config.set(uuid.toString() + ".duelInvites", duelInvites.stream().map(UUID::toString).collect(Collectors.toList()));
        config.set(uuid.toString() + ".duelRequests", duelRequests.stream().map(UUID::toString).collect(Collectors.toList()));
        config.set(uuid.toString() + ".creatingArena", creatingArena);
        config.set(uuid.toString() + ".inDuel", inDuel);
        ConfigurationManager.saveConfig(file, config);
        return new DuelPlayer(player);
    }
}
