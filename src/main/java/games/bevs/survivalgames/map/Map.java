package games.bevs.survivalgames.map;

import org.bukkit.World;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Map 
{
	@Getter @NonNull
	private World world;
}
