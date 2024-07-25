package dev.nikipro50.antiCreeperGrief.events;

import dev.nikipro50.antiCreeperGrief.AntiCreeperGrief;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperExplodeListener implements Listener {
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (!AntiCreeperGrief.getInstance().getConfig().getBoolean("Enabled"))
            return;
        if (event.getEntity() instanceof Creeper)
            event.setCancelled(true);
    }
}
