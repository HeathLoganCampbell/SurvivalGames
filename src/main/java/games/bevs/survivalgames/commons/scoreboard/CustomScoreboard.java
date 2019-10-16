package games.bevs.survivalgames.commons.scoreboard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import games.bevs.survivalgames.commons.scoreboard.player.FakePlayer;
import games.bevs.survivalgames.commons.utils.CC;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public abstract class CustomScoreboard implements Consumer<Player> {
	public static final char[] CHAR_COLORS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f', 'l', 'r', 'm', 'n', 'o', 'k' };

	private final Scoreboard scoreboard;
	private final Objective objective;

	private List<FakePlayer> fakePlayers;
	
	private List<String> lastEntries;
	private List<String> nextEntries;

	public CustomScoreboard() {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.scoreboard.registerNewObjective(UUID.randomUUID().toString().substring(0, 8), "dummy")
				.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective = this.scoreboard.getObjective(DisplaySlot.SIDEBAR);

		this.fakePlayers = new ArrayList<>();
		
		this.lastEntries = new LinkedList<>();
		this.nextEntries = new LinkedList<>();
	}

	public void setTitle(String title) {
		String newTitle = (title == null || title.equals("")) ? CC.r : title;
		if (!this.objective.getDisplayName().equals(newTitle))
			this.objective.setDisplayName(newTitle);
	}

	public void updateValues() {
		this.lastEntries = new LinkedList<>(this.nextEntries);
		this.nextEntries.clear();
//		this.accept(player);
	}

}
