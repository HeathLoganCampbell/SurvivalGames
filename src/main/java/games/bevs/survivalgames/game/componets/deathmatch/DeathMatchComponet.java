package games.bevs.survivalgames.game.componets.deathmatch;

import java.util.List;

import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeathMatchComponet extends Component 
{
	public long secondsRemaining = 60 * 5;
	public int playersRemaining = 3;
	
	@Getter @Setter
	private  DeathMatchStage deathMatchStage = DeathMatchStage.PREWARNING;
	
	public DeathMatchComponet(Game game) 
	{
		super("DeathMatch", game);
		
	}
	
	@Override
	public void onSecond()
	{
		if(this.getStage() != Stage.LIVE)
			return;
		
		List<Player> alivePlayers = this.getGame().getAlivePlayers();
		
		//count
//		long seconds = this.getSeconds();
//		if(seconds < this.getSecondsRemaining() ||  alivePlayers.size() <= this.getPlayersRemaining())
//		{
//			//say death match is starting
//			if(this.deathMatchWarningSeconds > 0)
//			{
//				if(this.getDeathMatchStage() == DeathMatchStage.PREWARNING)
//					this.setDeathMatchStage(DeathMatchStage.WARNING);
//				
//				this.broadcast("Death match in " + deathMatchWarningSeconds + " second" + (deathMatchWarningSeconds == 1 ? "" :  "s"));
//				deathMatchWarningSeconds--;
//			}
//			
//			if(this.deathMatchWarningSeconds == 0)
//			{
//				this.setDeathMatchStage(DeathMatchStage.TELEPORTING);
//				alivePlayers.forEach(player -> {
//					player.teleport(this.getGame().getMap().getSpawn());
//				});
//				this.setDeathMatchStage(DeathMatchStage.FREEZE);
//			}
//		}
//		
		
		
//		this.getGame().applyToAll(player -> {
//			player.sendTitle(new Title(CC.bGray + seconds));
//		});
	}
	
	public enum DeathMatchStage
	{
		PREWARNING(),
		WARNING(),
		TELEPORTING,
		FREEZE,
		ATTACK;
	}
}
