package games.bevs.survivalgames.game.componets;

import org.bukkit.event.Listener;

import games.bevs.survivalgames.game.games.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Component implements Listener
{
	private String name;
	private Game game;
	
	
}
