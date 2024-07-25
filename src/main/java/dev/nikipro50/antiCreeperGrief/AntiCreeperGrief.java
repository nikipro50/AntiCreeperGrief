package dev.nikipro50.antiCreeperGrief;

import dev.nikipro50.antiCreeperGrief.commands.Commands;
import dev.nikipro50.antiCreeperGrief.commands.TabCompleters;
import dev.nikipro50.antiCreeperGrief.enums.Lang;
import dev.nikipro50.antiCreeperGrief.events.CreeperExplodeListener;
import dev.nikipro50.antiCreeperGrief.metrics.Metrics;
import dev.nikipro50.antiCreeperGrief.request.SpigotUpdaterChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class AntiCreeperGrief extends JavaPlugin {
    private static AntiCreeperGrief instance;
    private static YamlConfiguration LANG;
    private static File LANG_FILE;

    private SpigotUpdaterChecker updaterChecker;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadLang();
        Metrics metrics = new Metrics(this, 22778);
        metrics.addCustomChart(new Metrics.SimplePie("version", () -> getDescription().getVersion()));
        Bukkit.getServer().getPluginCommand("anticreepergrief").setExecutor((CommandExecutor) new Commands());
        Bukkit.getServer().getPluginCommand("anticreepergrief").setTabCompleter((TabCompleter) new TabCompleters());
        Bukkit.getServer().getPluginManager().registerEvents((Listener) new CreeperExplodeListener(), (Plugin) this);
        if (getConfig().getBoolean("CheckUpdate")) {
            this.updaterChecker = new SpigotUpdaterChecker(this, 118212);
            this.updaterChecker.cacheLatestVersion();
        }
        Bukkit.getConsoleSender().sendMessage("§aPlugin Avviato!");
    }

    @Override
    public void onDisable() {
        if (this.updaterChecker != null)
            this.updaterChecker = null;
        saveConfig();
        Bukkit.getConsoleSender().sendMessage("§cPlugin Disabilitato!");
    }

    public static AntiCreeperGrief getInstance() {
        return instance;
    }

    public static YamlConfiguration getLang() {
        return LANG;
    }

    public static File getLangFile() {
        return LANG_FILE;
    }

    private void loadLang() {
        File file = new File(getDataFolder(), "lang.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                InputStream defConfigStream = this.getResource("lang.yml");
                if (defConfigStream != null) {
                    InputStreamReader reader = new InputStreamReader(defConfigStream);
                    YamlConfiguration defConfig = new YamlConfiguration();
                    try {
                        defConfig.load(reader);
                        defConfig.save(file);
                        Lang.setFile(defConfig);
                    } catch (InvalidConfigurationException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
            } catch (IOException e) {
                Bukkit.getLogger().severe("[AntiCreeperGrief] Couldn't create language file.");
                Bukkit.getLogger().severe("[AntiCreeperGrief] This is a FATAL ERROR! Disabling...");
                Bukkit.getServer().getPluginManager().disablePlugin((Plugin) this);
                this.setEnabled(false);
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        for (Lang item : Lang.values()) {
            if (conf.getString(item.getPath()) == null)
                conf.set(item.getPath(), item.getDef());
        }
        Lang.setFile(conf);
        AntiCreeperGrief.LANG = conf;
        AntiCreeperGrief.LANG_FILE = file;
        try {
            conf.save(getLangFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
