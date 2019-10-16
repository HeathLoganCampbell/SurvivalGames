package games.bevs.survivalgames.commons.scoreboard.player;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class FakePlayer implements OfflinePlayer 
{
	private UUID uniqueId;
	private String name;
	private Team team;
	
	public FakePlayer(String name, Team team) 
	{
		this.name = name;
		this.team = team;
		
		this.uniqueId = UUID.randomUUID();
		this.team.addPlayer(this);
	}

	@Override
	public boolean isOp() {
		return false;
	}

	@Override
	public void setOp(boolean arg0)
	{
		
	}

	@Override
	public Map<String, Object> serialize()
	{
		return null;
	}

	@Override
	public Location getBedSpawnLocation() {
		return null;
	}

	@Override
	public long getFirstPlayed() {
		return 0;
	}

	@Override
	public long getLastPlayed() {
		return 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public UUID getUniqueId() {
		return this.uniqueId;
	}

	@Override
	public boolean hasPlayedBefore() {
		return false;
	}

	@Override
	public boolean isBanned() {
		return false;
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	@Override
	public boolean isWhitelisted() {
		return false;
	}

	@Override
	public void setBanned(boolean arg0) {
		
	}

	@Override
	public void setWhitelisted(boolean arg0) {
		
	}
	
	@Override
	public String toString() 
	{ 
		return "FakePlayer{name='" + this.name + "'}";
	}

}
