package games.bevs.survivalgames.commons.gui;

import org.bukkit.entity.Player;

import games.bevs.survivalgames.commons.utils.Callback;
import games.bevs.survivalgames.commons.utils.ItemStackBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuIcon 
{
	private ItemStackBuilder icon;
	private int slot;
	private Callback<Player> runnable;
}
