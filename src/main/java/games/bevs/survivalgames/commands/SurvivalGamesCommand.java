package games.bevs.survivalgames.commands;

import java.util.Arrays;
import java.util.HashMap;

import games.bevs.survivalgames.commands.subcommand.ScoreSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import games.bevs.survivalgames.commands.subcommand.DeveloperSubCommand;
import games.bevs.survivalgames.commands.subcommand.SubCommand;
import games.bevs.survivalgames.game.GameManager;

public class SurvivalGamesCommand extends Command
{
	private HashMap<String, SubCommand> subcommands = new HashMap<>();
	
	public SurvivalGamesCommand(GameManager gameManager) {
		super("SurvivalGames", 
			"Main command",
			"/survivalGames", 
			Arrays.asList("sg"));
		
		this.registerSubCommand(new DeveloperSubCommand(gameManager));
		this.registerSubCommand(new ScoreSubCommand(gameManager));
	}
	
	private void registerSubCommand(SubCommand subCommand)
	{
		this.subcommands.put(subCommand.getName(), subCommand);
	}

	@Override
	public boolean execute(CommandSender sender, String cmd, String[] args)
	{
		if(args.length < 1)
		{
			//help
			return true;
		}
		
		if(args.length >= 1)
		{
			String subCommadName = args[0];
			subCommadName = subCommadName.toLowerCase();
			
			SubCommand subCommand = this.subcommands.get(subCommadName);
			if(subCommand == null) return false;
			
			String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
			return subCommand.execute(sender, subCommadName, newArgs);
		}
		return false;
	}

}
