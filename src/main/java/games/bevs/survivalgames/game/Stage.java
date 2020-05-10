package games.bevs.survivalgames.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Stage
{
	CREATING(false, false, -1l),
	WAITING_PLAYERS(false, true, -1l),
	COUNTDOWN(false, true,  5l),
	TELEPORTING(false, false,  -1l),
	FROZEN(false, false,  10l),
	GRACE_PERIOD(true, false,  5l),
	LIVE(true, false, 60l * 20l),
	CHAMPIONS(false, false, 10l),
	FINISHED(false, false, -1l),
	DELETING(false, false, -1l);
	
	private @Getter boolean live;
	private @Getter boolean joinnable;
	private @Getter long seconds;
	
	public boolean isTimable()
	{
		return this.getSeconds() != -1l;
	}
}
