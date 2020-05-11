package games.bevs.survivalgames.commons.entityengine.listeners;

import org.bukkit.entity.Player;

public interface RightClickListener
{
    public void onRightClicked(Player whoClicked, boolean interactedAt);
}