package games.bevs.survivalgames.commands.subcommand;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.game.games.classic.ClassicGame;
import games.bevs.survivalgames.game.games.spleef.SpleefGame;
import games.bevs.survivalgames.scorecard.Scorecard;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;


// /sg dev create classic
// /sg dev list
// /sg dev join <id>
public class ScoreSubCommand extends SubCommand
{
	private GameManager gameManager;

	public ScoreSubCommand(GameManager gameManager)
	{
		super("score");
		
		this.gameManager = gameManager;
	}

	
	@Override
	public boolean execute(CommandSender sender, String cmd, String[] args)
	{

		if(args.length >= 1)
		{
			if(args[0].equalsIgnoreCase("clear"))
			{
				SurvivalGames.get().getScorecardManager().clearScorecards();
			}
			return true;
		}

		sender.sendMessage(CC.green + "Top Players");
		ArrayList<Scorecard> rankedScorecards = SurvivalGames.get().getScorecardManager().getRankedScorecards();
		int i = 1;
		for (Scorecard rankedScorecard : rankedScorecards)
		{
			if(i == 11) break;
			int totalScore = rankedScorecard.getTotalScore();
			String playerName = rankedScorecard.getPlayerName();
			sender.sendMessage(CC.green + i + ". " + totalScore + " " + playerName);
			i++;
		}
		return true;
	}
}
