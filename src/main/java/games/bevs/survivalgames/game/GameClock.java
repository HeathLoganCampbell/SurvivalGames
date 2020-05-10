package games.bevs.survivalgames.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.events.StageChangeEvent;
import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.lobby.Lobby;
import lombok.Getter;

public class GameClock 
{
	private static long TICKS_PER_SECOND = 20;
	
	private @Getter Stage stage = Stage.CREATING;
	private @Getter long ticks = 0l;
	private @Getter long seconds = 0; 
	
	private @Getter int taskId = 0;
	
	private @Getter Game game;
	
	private @Getter Lobby lobby;
	
	private JavaPlugin plugin;
	
	public GameClock(JavaPlugin plugin, Game game, Lobby lobby)
	{
		this.stage =  Stage.CREATING;
		this.seconds = this.stage.getSeconds();
		
		this.lobby = lobby;
		
		this.plugin = plugin;
		this.game = game;
		this.game.setGameClock(this);
		
		this.setStage(Stage.WAITING_PLAYERS);
	}
	
	public void start()
	{
		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
			this.onTick();
		}, 1l, 1l);
	}
	
	public void setStage(Stage stage)
	{
		this.setStage(stage, true);
	}
	
	public void setStage(Stage stage, boolean hasEvent)
	{
		if(hasEvent)
		{
			StageChangeEvent event = new StageChangeEvent(this.stage, stage, this);
			event.call();
			Bukkit.broadcastMessage(CC.gray + "Stage ]> " + this.stage + " => " + stage);

			this.stage = event.getNextStage();
			this.seconds = event.getNextStage().getSeconds();
		}
		else
		{
			this.stage = stage;
			this.seconds = stage.getSeconds();
		}
	}
	
	public Stage nextStage(Stage stage)
	{
		switch(stage)
		{
		case CREATING:
			return Stage.WAITING_PLAYERS;
			
		case WAITING_PLAYERS:
			return Stage.COUNTDOWN;
			
		case COUNTDOWN:
			return Stage.TELEPORTING;
			
		case TELEPORTING:
			return Stage.FROZEN;
			
		case FROZEN:
			return Stage.GRACE_PERIOD;
			
		case GRACE_PERIOD:
			return Stage.LIVE;
			
		case LIVE:
			return Stage.CHAMPIONS;
		
		case CHAMPIONS:
			return Stage.FINISHED;
			
		case FINISHED:
			return null;
		}
		
		return null;
	}
	
	public void remove()
	{
		this.getGame().remove();
		this.game = null;
		this.plugin = null;
	}
	
	public void decreaseSeconds()
	{
		this.seconds--;
	}
	
	public void onTick()
	{
		if(this.ticks % TICKS_PER_SECOND == 0)
			this.onSecond();
		this.ticks++;
	}
	
	public void onSecond()
	{
		if(this.getStage().isLive())
		{
			this.getGame().second();
		}
		
		if(this.getStage().isTimable())
		{
			this.decreaseSeconds();
			
			if(this.getStage() == Stage.COUNTDOWN && this.getSeconds() <= 5 && this.getSeconds() != 0)
				Bukkit.broadcastMessage(CC.green + "Game starting in " + this.getSeconds() + " seconds");

			if(this.getStage() == Stage.FROZEN && this.getSeconds() <= 10)
			{
				if(this.getSeconds() != 0)
					Bukkit.broadcastMessage(CC.green + this.getSeconds() + " seconds...");
				else
					Bukkit.broadcastMessage(CC.green + "GO!");
			}
			
			if(this.getSeconds() == 0)
			{
				//Teleport players to location
				Stage stage = this.nextStage(this.getStage());
				this.setStage(stage);
			}
		}
		
//		if(this.getGame().isChampionFound())
//		{
//			Stage stage = this.nextStage(this.getStage());
//			this.setStage(stage);
//		}
		
		if(this.getStage() == Stage.WAITING_PLAYERS)
		{
			if(this.getGame().getPlayState().size() >= 1)
			{
				this.setStage(Stage.COUNTDOWN);
			}
		}
		
		if(this.getStage() == Stage.COUNTDOWN)
		{
			if(this.getGame().getPlayState().size() <= 0)
			{
				this.setStage(Stage.WAITING_PLAYERS);
			}
		}
		
		if(this.getStage() == Stage.FINISHED)
		{
			this.getGame().getMap().getWorld().getPlayers().forEach(player ->
			{
				this.getLobby().spawn(player);
			});
			this.setStage(Stage.DELETING);
		}
			
		
		if(this.getStage() == Stage.DELETING)
		{
			//end game
			Bukkit.getScheduler().cancelTask(this.getTaskId());
		}
	}
}
