package games.bevs.survivalgames.game.games.classic;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.componets.chest.ChestComponet;
import games.bevs.survivalgames.game.Game;

public class ClassicGame extends Game
{
	public ClassicGame(JavaPlugin plugin)
	{
		super("Classic", plugin);
		
		this.addComponet(new ChestComponet());
	}
}
