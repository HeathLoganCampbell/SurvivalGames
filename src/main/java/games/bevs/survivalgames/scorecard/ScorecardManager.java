package games.bevs.survivalgames.scorecard;

import games.bevs.survivalgames.SurvivalGames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ScorecardManager
{
    //Keep all scorecards in memory so we know the winner
    private HashMap<UUID, Scorecard> scorecards = new HashMap<>();

    public ScorecardManager()
    {
        Bukkit.getScheduler().runTask(SurvivalGames.get().getPlugin(), () -> {

        });
    }

    public ArrayList<Scorecard> getRankedScorecards()
    {
        ArrayList<Scorecard> scorecards = new ArrayList<>(this.scorecards.values());
        Collections.sort(scorecards);
        return scorecards;
    }

    public void registerPlayer(Player player)
    {
        Scorecard scorecard = new Scorecard(player.getUniqueId(), player.getName());
        this.scorecards.put(player.getUniqueId(), scorecard);
    }

    public Scorecard getScorecard(UUID uuid)
    {
        return this.scorecards.get(uuid);
    }

    public Scorecard getScorecard(Player player)
    {
        return this.getScorecard(player.getUniqueId());
    }

    public void unregisterPlayer(Player player)
    {
        this.scorecards.remove(player.getUniqueId());
    }
}
