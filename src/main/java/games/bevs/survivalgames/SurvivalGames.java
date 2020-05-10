package games.bevs.survivalgames;

import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.leaderboard.LeaderboardManager;
import games.bevs.survivalgames.lobby.Lobby;
import games.bevs.survivalgames.map.MapManager;
import games.bevs.survivalgames.scorecard.ScorecardManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter
public class SurvivalGames
{
    private static SurvivalGames INSTANCE; public static SurvivalGames get() { return INSTANCE; }

    public static final int POINTS_PER_KILL = 5;
    public static final int POINTS_PER_WIN = 50;
    public static final int POINTS_PER_MINUTE = 1;

    private SurvivalGamesPlugin plugin;

    private MapManager mapManager;
    private GameManager gameManager;
    private ScorecardManager scorecardManager;
    private LeaderboardManager leaderboardManager;
    private Lobby lobby;

    public SurvivalGames(SurvivalGamesPlugin plugin)
    {
        INSTANCE = this;

        this.plugin = plugin;

        this.scorecardManager = new ScorecardManager();
        this.leaderboardManager = new LeaderboardManager();

        this.lobby = new Lobby(Bukkit.getWorlds().get(0));

        this.mapManager = new MapManager(plugin);
        this.gameManager = new GameManager(plugin, this.mapManager, lobby);
    }

    public Game getGame(Player player)
    {
        for (Map.Entry<Integer, GameClock> idClockEntry : this.getGameManager().getGames().entrySet()) {
            Game game = idClockEntry.getValue().getGame();
            boolean within = game.isWithin(player.getUniqueId());
            if(within)
                return game;
        }
        return  null;
    }
}
