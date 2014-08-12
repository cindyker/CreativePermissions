package com.minecats.cindyk.creativepermissions;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by cindy on 8/11/2014.
 */
public class CreativePermissions extends JavaPlugin implements Listener{

    private Permission permission;
    @Override
    public void onEnable() {
        super.onEnable();

        // Set up Vault
        if(!setupPermissions()) {
            getLogger().info(String.format("[%s] - Could not find Vault dependency, disabling.", getDescription().getName()));
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler(priority = EventPriority.LOW)
    void onPlayerLogin (PlayerJoinEvent event)
    {
        Player pl;
        pl = event.getPlayer();
        // no player to be had
        if(pl == null)
            return;

        boolean creative = false;
        if( !permission.has(pl,"CreativeAllowed")){
               if (pl.getGameMode() == GameMode.CREATIVE)
                    getLogger().info(pl.getName() + " was in Creative, set them to survival");
                pl.setGameMode(GameMode.SURVIVAL);
            }
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }



}
