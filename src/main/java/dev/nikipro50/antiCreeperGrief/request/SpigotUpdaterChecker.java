package dev.nikipro50.antiCreeperGrief.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.nikipro50.antiCreeperGrief.AntiCreeperGrief;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.BiConsumer;

public class SpigotUpdaterChecker {
    private final JavaPlugin plugin;
    private final int resourceId;
    private String latestCacheVersion;

    public String getLatestCacheVersion() {
        return latestCacheVersion;
    }

    public <T extends JavaPlugin> SpigotUpdaterChecker(T plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(BiConsumer<String, Boolean> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.plugin, () -> {
            Gson gson = (new GsonBuilder()).create();
            try {
                URL url = new URL("https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + this.resourceId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                if (jsonElement == null) {
                    consumer.accept(null, false);
                    return;
                }
                JsonObject json = jsonElement.getAsJsonObject();
                reader.close();
                connection.disconnect();
                String version = json.get("current_version").getAsString();
                consumer.accept(version, true);
                this.latestCacheVersion = version;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void isLatest(BiConsumer<Boolean, String> consumer) {
        getVersion((version, status) -> consumer.accept(getCurrentVersion().equals(version), version));
    }

    public void cacheLatestVersion(BiConsumer<String, Boolean> consumer) {
        getVersion((latest, status) -> {
            if (status) this.latestCacheVersion = latest;
            consumer.accept(latest, status);
        });
    }

    public void cacheLatestVersion() {
        cacheLatestVersion((s, aBoolean) -> this.plugin.getLogger().info("[Update Checker] Cached latest version of AntiCreeperGrief."));
    }

    public String getCurrentVersion() {
        return AntiCreeperGrief.getInstance().getDescription().getVersion();
    }

}
