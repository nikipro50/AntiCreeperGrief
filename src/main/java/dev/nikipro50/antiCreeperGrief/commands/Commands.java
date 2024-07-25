package dev.nikipro50.antiCreeperGrief.commands;

import dev.nikipro50.antiCreeperGrief.AntiCreeperGrief;
import dev.nikipro50.antiCreeperGrief.enums.Lang;
import dev.nikipro50.antiCreeperGrief.utils.ColorAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("anticreepergrief.admin")) {
            sender.sendMessage(Lang.NO_PERMS.toString());
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ColorAPI.colorize("&8[&aAntiCreeperGrief&8] &7Use &2/acg on|off|info|help"));
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            if (AntiCreeperGrief.getInstance().getConfig().getBoolean("Enabled")) {
                sender.sendMessage(Lang.ALREADY_ENABLED.toString());
                return true;
            }
            AntiCreeperGrief.getInstance().getConfig().set("Enabled", true);
            AntiCreeperGrief.getInstance().saveConfig();
            sender.sendMessage(Lang.ENABLE.toString());
            return true;
        }
        if (args[0].equalsIgnoreCase("off")) {
            if (!AntiCreeperGrief.getInstance().getConfig().getBoolean("Enabled")) {
                sender.sendMessage(Lang.ALREADY_DISABLED.toString());
                return true;
            }
            AntiCreeperGrief.getInstance().getConfig().set("Enabled", false);
            AntiCreeperGrief.getInstance().saveConfig();
            sender.sendMessage(Lang.DISABLE.toString());
            return true;
        }
        if (args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(ColorAPI.colorize("&aAntiCreeperGrief &8• &7" + AntiCreeperGrief.getInstance().getDescription().getVersion()));
            sender.sendMessage("");
            sender.sendMessage(ColorAPI.colorize("&8» &7Author: &anikipro50&r &b(@xxniki50xx on &ndiscord)"));
            sender.sendMessage(ColorAPI.colorize("&8» &7Thank for using this!"));
            sender.sendMessage("");
            sender.sendMessage(ColorAPI.colorize("&8» &7Anti-Grief by creeper is " + (AntiCreeperGrief.getInstance().getConfig().getBoolean("Enabled") ? "&a&nenabled&r" : "&c&ndisabled&r") + "&7."));
            sender.sendMessage("");
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ColorAPI.colorize("&8[&aAntiCreeperGrief&8] &7Use &2/acg on|off|info|help"));
            return true;
        }
        sender.sendMessage(ColorAPI.colorize("&8[&aAntiCreeperGrief&8] &7Use &2/acg on|off|info|help"));
        return false;
    }
}
