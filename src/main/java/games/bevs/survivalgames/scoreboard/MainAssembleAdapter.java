package games.bevs.survivalgames.scoreboard;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.commons.utils.StringUtils;
import games.bevs.survivalgames.game.games.Game;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainAssembleAdapter implements AssembleAdapter
{
    @Override
    public String getTitle(Player player)
    {
        return CC.bAqua + "Mini MCMonday";
    }

    public void onPlayerInGame(Player player, Game game, List<String> toReturn)
    {
        toReturn.add(CC.green + game.getAliveCount() + " Players");
    }

    public void onPlayerWaitingGame(Player player, Game game, List<String> toReturn)
    {
        toReturn.add(CC.green + "Waiting Game" + StringUtils.repeat(".", (int) (game.getGameClock().getSeconds() % 3) + 1));
        toReturn.add(CC.green + game.getPlayerCount() + " Players");
    }

    public void onPlayerInLobby(Player player, List<String> toReturn)
    {
        toReturn.add(CC.green + "Lobby");
    }

    @Override
    public List<String> getLines(Player player)
    {
        final List<String> toReturn = new ArrayList<>();

        Game game = SurvivalGames.get().getGame(player);

        if(game == null)
            this.onPlayerInLobby(player, toReturn);
        else
            if(game.isAlive(player.getUniqueId()))
            {
                this.onPlayerInGame(player, game, toReturn);
            }
            else
            {
                if(!game.getStage().isLive())
                    this.onPlayerWaitingGame(player, game, toReturn);
            }

        return toReturn;
    }
}
