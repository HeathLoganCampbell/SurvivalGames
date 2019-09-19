package games.bevs.survivalgames.game.games.classic;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.game.componets.chest.ChestComponet;
import games.bevs.survivalgames.game.componets.countdown.CountdownComponet;
import games.bevs.survivalgames.game.componets.death.DeathComponet;
import games.bevs.survivalgames.game.componets.freeze.FreezeComponet;
import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.map.Map;

public class ClassicGame extends Game
{
	public ClassicGame(JavaPlugin plugin, Map map)
	{
		super("Classic", plugin, map);
		
		this.addComponet(new ChestComponet(this));
		this.addComponet(new FreezeComponet(this));
		this.addComponet(new DeathComponet(this));
		this.addComponet(new CountdownComponet(this));
	}
}
