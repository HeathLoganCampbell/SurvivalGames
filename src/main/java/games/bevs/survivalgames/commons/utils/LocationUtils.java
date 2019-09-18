package games.bevs.survivalgames.commons.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;


@UtilityClass
public class LocationUtils {

    public Location toCenterLocation(Block block) {
        return toCenterLocation(block.getLocation());
    }

    public Location toCenterLocation(Location location) {
        return new Location(location.getWorld(), location.getBlockX() + 0.5, location.getY(), location.getBlockZ() + 0.5);
    }

    public Location toCenterBlockLocation(Block block) {
        return toCenterBlockLocation(block.getLocation());
    }

    public Location toCenterBlockLocation(Location location) {
        Location center = toCenterLocation(location);
        center.setY(location.getBlockY() + 0.5);
        return center;
    }

}