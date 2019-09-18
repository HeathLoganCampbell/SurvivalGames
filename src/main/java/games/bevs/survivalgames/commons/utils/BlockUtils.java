package games.bevs.survivalgames.commons.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockUtils {
	/**
	 * @param loc
	 * @return the block if a player is standing on the edge
	 */
	public static Block getAdjacentX(Location loc) {
		int x = (int) loc.getX();
		Block base = loc.getBlock();
		if (x != (int) (loc.getX() - 0.31D) || (x == 0 && loc.getX() < 0.31D && loc.getX() > 0.0D)) {
			base = base.getRelative(-1, 0, 0);
		} else if (x != (int) (loc.getX() + 0.31D) || (x == 0 && loc.getX() > -0.31D && loc.getX() < 0.0D)) {
			base = base.getRelative(1, 0, 0);
		}
		return base;
	}

	/**
	 * @param loc
	 * @return the block if a player is standing on the edge
	 */
	public static Block getAdjacentZ(Location loc) {
		int z = (int) loc.getZ();
		Block base = loc.getBlock();
		if (z != (int) (loc.getZ() - 0.31D) || (z == 0 && loc.getZ() < 0.31D && loc.getZ() > 0.0D)) {
			base = base.getRelative(0, 0, -1);
		} else if (z != (int) (loc.getZ() + 0.31D) || (z == 0 && loc.getZ() > -0.31D && loc.getZ() < 0.0D)) {
			base = base.getRelative(0, 0, 1);
		}
		return base;
	}
}
