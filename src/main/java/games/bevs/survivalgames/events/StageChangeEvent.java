package games.bevs.survivalgames.events;

import games.bevs.survivalgames.commons.events.EventBase;
import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.games.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StageChangeEvent extends EventBase
{
	private Stage currentStage, nextStage;
	private GameClock gameClock;
	
	public Game getGame()
	{
		return this.getGameClock().getGame();
	}
}
