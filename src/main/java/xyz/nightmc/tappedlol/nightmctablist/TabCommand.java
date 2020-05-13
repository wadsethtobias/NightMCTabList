package xyz.nightmc.tappedlol.nightmctablist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TabCommand implements CommandExecutor {

    private final NightMCTabList main;

    public TabCommand(NightMCTabList main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("nightmc.tablist")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        main.reloadConfig();
                        main.loadList();
                        main.setTab();
                        player.sendMessage(color("&aSuccessfully reloaded the configuration file"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("header")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("list")) {
                                player.sendMessage(color("Player List Header Lines:"));
                                int i = 1;
                                for (String s : main.getHeader()) {
                                    player.sendMessage(color(i + ". " + s));
                                    i++;
                                }
                                return true;
                            }
                            if (args.length > 2) {
                                if (args[1].equalsIgnoreCase("add")) {
                                    StringBuilder line = new StringBuilder();
                                    for (int i = 2; i < args.length; i++) {
                                        line.append(args[i]);
                                        line.append(" ");
                                    }
                                    main.addHeader(line.toString().trim());
                                    player.sendMessage(color("&aSuccessfully added: " + line.toString().trim()));
                                    return true;
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (isInt(args[2])) {
                                        int index = Integer.parseInt(args[2]);
                                        if (main.removeHeader(index)) {
                                            player.sendMessage(color("&aSuccessfully removed line " + index));
                                            return true;
                                        } else {
                                            player.sendMessage(color("&cCould not remove line " + index));
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("footer")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("list")) {
                                player.sendMessage(color("Player List Footer Lines:"));
                                int i = 1;
                                for (String s : main.getFooter()) {
                                    player.sendMessage(color(i + ". " + s));
                                    i++;
                                }
                                return true;
                            }
                            if (args.length > 2) {
                                if (args[1].equalsIgnoreCase("add")) {
                                    StringBuilder line = new StringBuilder();
                                    for (int i = 2; i < args.length; i++) {
                                        line.append(args[i]);
                                        line.append(" ");
                                    }
                                    main.addFooter(line.toString().trim());
                                    player.sendMessage(color("&aSuccessfully added: " + line.toString().trim()));
                                    return true;
                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    if (isInt(args[2])) {
                                        int index = Integer.parseInt(args[2]);
                                        if (main.removeFooter(index)) {
                                            player.sendMessage(color("&aSuccessfully removed line " + index));
                                            return true;
                                        } else {
                                            player.sendMessage(color("&cCould not remove line " + index));
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    player.sendMessage(color("&cUsage: /tab [header|footer|reload] [list|add|remove] [args]"));
                    return false;
                }
            } else {
                player.sendMessage(color("&cYou do not have sufficient permissions"));
                return false;
            }
        }
        return false;
    }

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
