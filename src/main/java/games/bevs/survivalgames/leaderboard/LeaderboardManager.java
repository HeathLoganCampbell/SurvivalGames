package games.bevs.survivalgames.leaderboard;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.scorecard.Scorecard;

import java.util.ArrayList;

public class LeaderboardManager
{
    private ArrayList<Integer> ncpIds = new ArrayList<>();
    private ArrayList<Integer> hologramsId = new ArrayList<>();

    private ArrayList<Scorecard> lastCachedRanks;

    //Spawn NPC on stand
    //Spawn Holograms
    public LeaderboardManager()
    {
        this.lastCachedRanks = SurvivalGames.get().getScorecardManager().getRankedScorecards();

        this.spawnHolograms();
        this.spawnNPCs();
    }

    public void spawnHolograms()
    {


        this.updateHolograms();
    }

    public void spawnNPCs()
    {


        this.updateNPCs();
    }

    public void updateHolograms()
    {

    }

    public void updateNPCs()
    {

    }
}
