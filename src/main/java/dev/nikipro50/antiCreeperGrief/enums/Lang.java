package dev.nikipro50.antiCreeperGrief.enums;

import dev.nikipro50.antiCreeperGrief.utils.ColorAPI;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    NO_PERMS("NoPerms", "&cYou don't any permission to do that!"),
    ALREADY_ENABLED("AlreadyEnabled", "&8[&aAntiCreeperGrief&8] &7The Anti-Grief by creeper is &nalready&r &a&nenabled&7."),
    ALREADY_DISABLED("AlreadyDisabled", "&8[&aAntiCreeperGrief&8] &7The Anti-Grief by creeper is &nalready&r &c&ndisabled&7."),
    ENABLE("Enable", "&8[&aAntiCreeperGrief&8] &7You have &a&nenabled&7 the anti-grief on creeper explosion!"),
    DISABLE("Disable", "&8[&aAntiCreeperGrief&8] &7You have &c&ndisable&7 the anti-grief on creeper explosion!");

    final String path;
    final String def;
    private static YamlConfiguration LANG;

    Lang(String path, String def) {
        this.path = path;
        this.def = def;
    }

    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        return ColorAPI.colorize(LANG.getString(this.path, this.def));
    }

    public String getPath() {
        return path;
    }

    public String getDef() {
        return def;
    }
}
