package xyz.nightmc.tappedlol.nightmctablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NightMCTabList extends JavaPlugin {

    private List<String> header;
    private List<String> footer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadList();

        getCommand("tab").setExecutor(new TabCommand(this));
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
        saveList();
    }

    public void loadList() {
        header = getConfig().contains("header") ? getConfig().getStringList("header") : new ArrayList<>();
        footer = getConfig().contains("footer") ? getConfig().getStringList("footer") : new ArrayList<>();
    }

    public void saveList() {
        getConfig().set("header", header);
        getConfig().set("footer", footer);
        saveConfig();
    }

    public List<String> getHeader() {
        return header;
    }

    public List<String> getFooter() {
        return footer;
    }

    public void addHeader(String s) {
        getHeader().add(s);
        saveList();
        setTab();
    }

    public void addFooter(String s) {
        getFooter().add(s);
        saveList();
        setTab();
    }

    public boolean removeHeader(int i) {
        try {
            getHeader().remove(i - 1);
            saveList();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public boolean removeFooter(int i) {
        try {
            getFooter().remove(i - 1);
            saveList();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public String getFormatted(List<String> list) {
        return ChatColor.translateAlternateColorCodes('&', String.join("\n", list));
    }

    public void setTab() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeaderFooter(getFormatted(getHeader()), getFormatted(getFooter()));
        }
    }

}
