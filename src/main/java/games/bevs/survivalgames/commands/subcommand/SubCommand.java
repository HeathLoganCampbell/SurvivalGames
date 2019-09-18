package games.bevs.survivalgames.commands.subcommand;

import org.bukkit.command.CommandSender;

import lombok.Getter;

public class SubCommand 
{
	@Getter
	private String name;
	
	public SubCommand(String name)
	{
		this.name = name;
	}
	
	public boolean execute(CommandSender sender, String cmd, String[] args)
	{
		return false;
	}
}
