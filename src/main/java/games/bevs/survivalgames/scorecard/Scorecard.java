package games.bevs.survivalgames.scorecard;

import games.bevs.survivalgames.commons.io.Pair;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class Scorecard implements Comparable<Scorecard>
{
    @Getter
    @NonNull
    private UUID playerUUID;
    @Getter
    @NonNull
    private String playerName;
    private List<Pair<String, Integer>> scoreEntries = new LinkedList<>();
    private boolean refershTotalScore = true;
    private int totalScore = 0;

    public void addScoreEntry(String reason, int amount)
    {
        System.out.println(this.playerName + " has been given " + amount + " points for '" + reason + "'.");
        this.refershTotalScore = true;
        this.scoreEntries.add(new Pair<>(reason, amount));
    }

    public void clearScoreEntry()
    {
        System.out.println(this.playerName + " scorecard has been cleared.");
        this.refershTotalScore = false;
        this.totalScore = 0;
        this.scoreEntries.clear();
    }

    public int getTotalScore()
    {
        if(this.refershTotalScore) {
            totalScore = 0;
            for (Pair<String, Integer> scoreEntry : this.scoreEntries)
                totalScore += scoreEntry.getRight();
        }
        return totalScore;
    }

    @Override
    public int compareTo(Scorecard o)
    {
        return Integer.compare(this.getTotalScore(), o.getTotalScore());
    }
}
