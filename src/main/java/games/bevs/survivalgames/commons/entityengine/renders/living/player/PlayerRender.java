package games.bevs.survivalgames.commons.entityengine.renders.living.player;

import com.mojang.authlib.GameProfile;
import games.bevs.survivalgames.commons.entityengine.ReflectionUtil;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import games.bevs.survivalgames.commons.entityengine.renders.living.LivingRender;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerRender extends LivingRender
{
    private static final Class<PacketPlayOutNamedEntitySpawn> NAMED_ENTITY_PACKET = PacketPlayOutNamedEntitySpawn.class;
    private static final Class<PacketPlayOutPlayerInfo> PLAYER_INFO_PACKET = PacketPlayOutPlayerInfo.class;

    private static Field PLAYER_ENTITY_ID;
    private static Field PLAYER_UUID;

    private static Field PLAYER_X;
    private static Field PLAYER_Y;
    private static Field PLAYER_Z;

    private static Field PLAYER_YAW;
    private static Field PLAYER_PITCH;

    private static Field DATAWATCHER_FIELD;

    private static Field TAB_ACTION;
    private static Field TAB_LIST;

    static
    {
        PLAYER_ENTITY_ID = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "a");
        PLAYER_UUID = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "b");

        PLAYER_X = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "c");
        PLAYER_Y = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "d");
        PLAYER_Z = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "e");

        PLAYER_YAW = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "f");
        PLAYER_PITCH = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "g");

        DATAWATCHER_FIELD = ReflectionUtil.getField(NAMED_ENTITY_PACKET, "i");

        TAB_ACTION = ReflectionUtil.getField(PLAYER_INFO_PACKET, "a");
        TAB_LIST = ReflectionUtil.getField(PLAYER_INFO_PACKET, "b");
    }

    private GameProfile gameprofile;

    public PlayerRender(String username) {
        super(EntityType.PLAYER);

        this.gameprofile = new GameProfile(UUID.randomUUID(), username);;
    }

    @Override
    protected PlayerRender spawnFor(List<Player> players)
    {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        try {
            PLAYER_ENTITY_ID.setInt(packet, this.getEntityId());
            PLAYER_UUID.set(packet, this.gameprofile.getId());

            PLAYER_X.setInt(packet, toFloatingPointNum(this.getX()));
            PLAYER_Y.setInt(packet, toFloatingPointNum(this.getY()));
            PLAYER_Z.setInt(packet, toFloatingPointNum(this.getZ()));

            PLAYER_YAW.setByte(packet, (byte) this.toAngle(this.getPitch()));
            PLAYER_PITCH.setByte(packet, (byte) this.toAngle(this.getPitch()));

            DataWatcher watcher = new DataWatcher(null);
            watcher.a(6,(float)20);
            watcher.a(10,(byte)127);

            DATAWATCHER_FIELD.set(packet, watcher);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        this.addToTab(players);

        return this;
    }

    @Override
    protected PlayerRender spawnFor(Player player)
    {
        return this.spawnFor(Arrays.asList(player));
    }

    private PlayerRender addToTab(List<Player> viewers)
    {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players =  ReflectionUtil.getValue(TAB_LIST, packet);
        players.add(data);

        try {
            TAB_ACTION.set(packet, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
            TAB_LIST.set(packet, players );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.broadcastPacket(viewers, packet);
        return this;
    }

    private PlayerRender removeFromTab(List<Player> viewers)
    {

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) ReflectionUtil.getValue(TAB_LIST, packet);
        players.add(data);

        try {
            TAB_ACTION.set(packet, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
            TAB_LIST.set(packet, players );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.broadcastPacket(viewers, packet);
        return this;
    }

}
