package games.bevs.survivalgames.commands.subcommand;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.game.games.classic.ClassicGame;

public class DeveloperSubCommand extends SubCommand
{
	private GameManager gameManager;
	
	public DeveloperSubCommand(GameManager gameManager)
	{
		super("dev");
		
		this.gameManager = gameManager;
	}

	private boolean createGame()
	{
		this.gameManager.createGame(ClassicGame.class);
		return true;
	}
	
	private boolean listGame(Player player)
	{
		player.sendMessage(CC.gray + "Active game Ids");
		Iterator<Entry<Integer, GameClock>> it = this.gameManager.getGames().entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer, GameClock> entry = it.next();
			player.sendMessage(CC.gray + entry.getKey());
		}
		return true;
	}
	
	private boolean tpGame(Player player, int gameId)
	{
		GameClock clock = this.gameManager.getGameClock(gameId);
		if(clock == null) return false;
		
		player.teleport(clock.getGame().getMap().getWorld().getSpawnLocation());
		player.sendMessage("Teleporting to game world");
		return true;
	}
	
	private boolean joinGame(Player player, int gameId)
	{
		GameClock clock = this.gameManager.getGameClock(gameId);
		if(clock == null) return false;
		clock.getGame().join(player);
		return true;
	}
	
	private boolean leaveGame(Player player, int gameId)
	{
		GameClock clock = this.gameManager.getGameClock(gameId);
		if(clock == null) return false;
		clock.getGame().leave(player);
		return true;
	}
	
	@Override
	public boolean execute(CommandSender sender, String cmd, String[] args)
	{
		Player player = (Player) sender;
		
		if(args.length >= 1)
		{
			String subCmd = args[0];
			if(subCmd.equalsIgnoreCase("create"))
			{
				return this.createGame();
			}
			
			if(subCmd.equalsIgnoreCase("list"))
			{
				return this.listGame(player);
			}
			
			if(subCmd.equalsIgnoreCase("join"))
			{
				if(args.length < 2)
				{
					player.sendMessage("/sg dev join <GameId>");
					return false;
				}
				
				String gameIdStr = args[1];
				if(!StringUtils.isNumeric(gameIdStr))
				{
					player.sendMessage("GameId must be a number, do '/sg dev list' for ids");
					return false;
				}
				
				int gameId = Integer.parseInt(gameIdStr);
				return this.joinGame(player, gameId);
			}
			
			if(subCmd.equalsIgnoreCase("leave"))
			{
				if(args.length < 2)
				{
					player.sendMessage("/sg dev leave <GameId>");
					return false;
				}
				
				String gameIdStr = args[1];
				if(!StringUtils.isNumeric(gameIdStr))
				{
					player.sendMessage("GameId must be a number, do '/sg dev list' for ids");
					return false;
				}
				
				int gameId = Integer.parseInt(gameIdStr);
				return this.leaveGame(player, gameId);
			}
			
			if(subCmd.equalsIgnoreCase("tp"))
			{
				if(args.length < 2)
				{
					player.sendMessage("/sg dev tp <GameId>");
					return false;
				}
				
				String gameIdStr = args[1];
				if(!StringUtils.isNumeric(gameIdStr))
				{
					player.sendMessage("GameId must be a number, do '/sg dev list' for ids");
					return false;
				}
				
				int gameId = Integer.parseInt(gameIdStr);
				return this.tpGame(player, gameId);
			}
		} else
		{
			sender.sendMessage("ummm, not enough args");
		}
		
//		Game game = this.gameManager.createGame(ClassicGame.class);
//		player.teleport(game.getMap().getWorld().getSpawnLocation());
		
		return false;
	}
}
