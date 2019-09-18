package games.bevs.survivalgames.commons.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;

public class PacketUtils 
{
	private static Method hurtSoundStr;
	
	public static EntityPlayer getNMSPlayer(Player player)
	{
		return ((CraftPlayer) player).getHandle();
	}
	
	public static void sendPacket(Player reciever, Packet<?>... packets)
	{
		for(Packet<?> packet : packets)
			getNMSPlayer(reciever).playerConnection.sendPacket(packet);
	}
	
	public static void sendPacket(Player reciever, Object... packets)
	{
		for(Object packet : packets)
			getNMSPlayer(reciever).playerConnection.sendPacket((Packet<?>) packet);
	}
	
	public static void playSound(Player player, String sound, double x, double y, double z, float volume, float pitch)
	{
		sendPacket(player, new PacketPlayOutNamedSoundEffect(sound, x, y, z, volume, pitch));
	}
	
	public static String getLivingEntitySound(LivingEntity livingEntity)
	{
		return getLivingEntitySound(((CraftLivingEntity) livingEntity).getHandle());
	}
	
	public static String getLivingEntitySound(EntityLiving entityLiving)
	{
		try {
			return (String) hurtSoundStr.invoke(entityLiving);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void playDamageAlert(Player player)
	{
		sendPacket(player, new PacketPlayOutEntityStatus(getNMSPlayer(player), (byte) 2));
	}
	
	static
	{
		try {
			hurtSoundStr = EntityLiving.class.getDeclaredMethod("bo");//hurt sound
			hurtSoundStr.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
