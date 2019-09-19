package games.bevs.survivalgames.game.componets;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import games.bevs.survivalgames.game.PlayState;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.games.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Component implements Listener
{
	private String name;
	private Game game;
	
	public World getGameWorld()
	{
		return this.getGame().getWorld();
	}
	
	public Stage getStage()
	{
		return this.getGame().getStage();
	}
	
	public long getSeconds()
	{
		return this.getGame().getGameClock().getSeconds();
	}
	
	public boolean isInGame(Player player)
	{
		return this.getGame().getPlayState().getOrDefault(player.getUniqueId(), PlayState.DEAD) != PlayState.ALIVE;
	}
	
	public void onSecond()
	{
		
	}
}
