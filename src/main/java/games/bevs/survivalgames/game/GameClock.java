package games.bevs.survivalgames.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.events.StageChangeEvent;
import lombok.Getter;

public class GameClock 
{
	private static long TICKS_PER_SECOND = 20;
	
	private @Getter Stage stage = Stage.CREATING;
	private @Getter long ticks = 0l;
	private @Getter long seconds = 0; 
	
	private @Getter int taskId = 0;
	
	private @Getter Game game;
	
	public GameClock(JavaPlugin plugin, Game game)
	{
		this.stage =  Stage.CREATING;
		this.seconds = this.stage.getSeconds();
		
		this.game = game;
		
		//ticks
		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			this.onTick();
		}, 1l, 1l);
		
		this.setStage(Stage.WAITING_PLAYERS);
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
			Bukkit.broadcastMessage(CC.gray + "Stage ]> " + this.stage.name() + " => " + stage.name());

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
		if(this.getStage().isTimable())
		{
			this.decreaseSeconds();
			
			Bukkit.broadcastMessage(this.getSeconds() + " seconds");
			
			if(this.getSeconds() == 0)
			{
				Stage stage = this.nextStage(this.getStage());
				this.setStage(stage);
			}
		}
		else
		{
			if(this.getStage() == Stage.WAITING_PLAYERS)
			{
				if(Bukkit.getOnlinePlayers().size() == 1)
				{
					this.setStage(Stage.COUNTDOWN);
				}
			}
			
			if(this.getStage() == Stage.COUNTDOWN)
			{
				if(Bukkit.getOnlinePlayers().size() <= 0)
				{
					this.setStage(Stage.WAITING_PLAYERS);
				}
			}
		}
		
	}
}
