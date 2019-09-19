package games.bevs.survivalgames.game.componets.countdown;

import org.github.paperspigot.Title;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;

public class CountdownComponet extends Component 
{
	public CountdownComponet(Game game) 
	{
		super("Countdown", game);
		
	}
	
	@Override
	public void onSecond()
	{
		if(this.getStage() != Stage.FROZEN)
			return;
		
		//count
		long seconds = this.getSeconds();
		this.getGame().applyToAll(player -> {
			player.sendTitle(new Title(CC.bGray + seconds));
		});
	}
}
