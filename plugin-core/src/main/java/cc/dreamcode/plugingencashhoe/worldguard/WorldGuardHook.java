package cc.dreamcode.plugingencashhoe.worldguard;

import cc.dreamcode.platform.bukkit.hook.PluginHook;
import cc.dreamcode.platform.bukkit.hook.annotation.Hook;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.Getter;
import org.bukkit.Location;

import java.util.List;

@Hook(name = "WorldGuard")
public class WorldGuardHook implements PluginHook {

    @Getter private WorldGuard worldGuard;

    @Override
    public void onInit() {
        this.worldGuard = WorldGuard.getInstance();
    }

    public boolean handleRegion(WorldGuardHook worldGuardHook, List<String> blockedRegions, Location location) {
        WorldGuard worldGuard = worldGuardHook.getWorldGuard();

        RegionContainer regionContainer = worldGuard.getPlatform().getRegionContainer();
        RegionQuery regionQuery = regionContainer.createQuery();

        for (ProtectedRegion protectedRegion : regionQuery.getApplicableRegions(BukkitAdapter.adapt(location))) {
            if (blockedRegions.stream().anyMatch(string -> string.equalsIgnoreCase(protectedRegion.getId()))) {
                return true;
            }
        }

        return false;
    }
}