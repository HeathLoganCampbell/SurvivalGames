package games.bevs.survivalgames.items;

import games.bevs.survivalgames.SurvivalGames;
import org.bukkit.Bukkit;

public class ItemManager
{
    public ItemManager()
    {
        Bukkit.getPluginManager().registerEvents(new TnTItem(), SurvivalGames.get().getPlugin());
    }


}
