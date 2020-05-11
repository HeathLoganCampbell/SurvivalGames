package games.bevs.survivalgames.listeners;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import games.bevs.survivalgames.commons.entityengine.renders.living.monstor.WitherRender;
import games.bevs.survivalgames.commons.entityengine.renders.living.player.PlayerRender;
import games.bevs.survivalgames.commons.utils.CC;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import games.bevs.survivalgames.lobby.Lobby;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInitialSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.UUID;

@Getter(AccessLevel.PRIVATE)
//@AllArgsConstructor
public class LobbyListener implements Listener
{
	private Lobby lobby;
	private EntityRender npc;

	public LobbyListener(Lobby lobby)
	{
		this.lobby = lobby;

		this.npc = new PlayerRender(
				CC.b + "-=[ Play ]=-",
				"eyJ0aW1lc3RhbXAiOjE1ODU5MjQ4MzI5MDIsInByb2ZpbGVJZCI6ImJlY2RkYjI4YTJjODQ5YjRhOWIwOTIyYTU4MDUxNDIwIiwicHJvZmlsZU5hbWUiOiJTdFR2Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80ZDI4MTBjMDliZWJiNTcxODYwY2FiNzg1OTJkYTlhMTRkMzM2OTc0ODQ2Mjk5ZTk2ZWQzOTUyYThkNjdlYTRlIn19fQ==",
				"GxfWxKocagIhEtcWP7mGkrJhxaB3Bw/7KfGxtYto718K9MjsiZnMswrPPIp6mwEK/y9x2nd/Pcen7i5k0+Eh45EtMcdCyWXiWYIlCbfvxtRaKb2UPFtfSOraXMeoLZOG2IN5uNLw4xSkzEU4laNs2CPDx7xvGirxK0quFBY15qVU2RJDxZP0kVAEqq8Nd23bKNfAoaGbh7n7QX7Juowb1vxiEQh9iF+chpYNOiW27fO67VZJWU2hYaVF4BTtrI34UEIRd7MEcE/yjjcPupWyeOGSpix8xBMTsS6wLEFoHUFPuq6Z5iTmZff48pXBSO1KfC5m6XxVlN5TrrUl+ZOmBqO1Hf1XQz+ykumNm9YzbTgEncLFesDJSJJ64mfkIKxGVe89xp1UO79HkINyo3Auj+grHKeMiqSy5RlmfBQjtfwmijjlVsIUeI/Pg92pHfSojliFQKqR71bcAiaspT+Z9FXgXSlo4Sjau7zJc1TgxpP+WTCgY7SyUtR+ZVDidkp6RvRJnXOaG4wbAGUbLJDohUN8/mMNpR51dWJMOpwy9ELAezBrs0P7pt2JrLmCP8ZTRjouf1dCr056FoedoFmj+HSxmtTP3HjWbIUAxTAkrW/WhjB7TupZy6XmrkgCy6wJ8F2KggkWAJ8GthQkYoJSmgML/rKnhs4nsGIkZm61X6Y=");
//		npc.forcePushMetadataUpdates(player);
		this.npc.getAttackListeners().add((event) -> {
			Player clicker = event.getPlayer();
			clicker.sendMessage(CC.green + "Joined!");
			return 0;
		});
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		if(this.getLobby().getWorld() != player.getWorld())
			return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		if(this.getLobby().getWorld() != player.getWorld())
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		Entity entity = e.getEntity();
		if(!(entity instanceof Player)) return;
		Player player = (Player) entity;
		if(this.getLobby().getWorld() != player.getWorld())
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e)
	{
		Entity entity = e.getEntity();
		if(!(entity instanceof Player)) return;
		Player player = (Player) entity;
		if(this.getLobby().getWorld() != player.getWorld())
			return;
		e.setFoodLevel(20);
	}

	@EventHandler
	public void onSpawn(PlayerInitialSpawnEvent e)
	{
		e.setSpawnLocation(new Location(this.getLobby().getWorld(), -157.5, 143.5, 316.5, 180f, 0f));
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();

//		npc.spawn(player, player.getLocation());
		SurvivalGames.get().getEntityEngine().spawn(Arrays.asList(player), npc, new Location(this.lobby.getWorld(), -151.5, 136, 223.5, 0f, 0f));
	}

	public static final Material LAUNCH_MATER = Material.GOLD_PLATE;

	@EventHandler
	public void onPad(PlayerInteractEvent e)
	{
		if(e.getAction() != Action.PHYSICAL) return;
		if(e.getClickedBlock() == null) return;
		if(e.getClickedBlock().getType() != LAUNCH_MATER) return;
		Player player = e.getPlayer();
		Vector playerDirection = player.getLocation().getDirection();

		player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 0.2F, 2.0F);
		player.setVelocity(new Vector(playerDirection.getX() * 3.0D, 1.45D, playerDirection.getZ() * 3.0D));
		e.setCancelled(true);
	}
}
