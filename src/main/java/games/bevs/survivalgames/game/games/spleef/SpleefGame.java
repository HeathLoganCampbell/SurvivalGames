package games.bevs.survivalgames.game.games.spleef;

import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.game.componets.countdown.CountdownComponet;
import games.bevs.survivalgames.game.componets.death.DeathComponet;
import games.bevs.survivalgames.game.componets.freeze.FreezeComponet;
import games.bevs.survivalgames.game.componets.instabreak.InstaBlockComponet;
import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.game.games.GameSettings;
import games.bevs.survivalgames.map.Map;

public class SpleefGame extends Game
{
	public SpleefGame(JavaPlugin plugin, Map map)
	{
		super("Spleef", plugin, map);
		
		this.addComponet(new FreezeComponet(this));
		this.addComponet(new DeathComponet(this));
		this.addComponet(new CountdownComponet(this));
		this.addComponet(new InstaBlockComponet(this));
	}
	
	@Override
	public GameSettings onSettings(GameSettings settings)
	{
		settings.setDamagePVP(false);
		
		return settings;
	}
}
