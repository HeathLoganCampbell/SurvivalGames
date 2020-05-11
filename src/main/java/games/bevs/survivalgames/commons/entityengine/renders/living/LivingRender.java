package games.bevs.survivalgames.commons.entityengine.renders.living;

import games.bevs.survivalgames.commons.entityengine.ReflectionUtil;
import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public class LivingRender extends EntityRender
{
    private static final Class<PacketPlayOutSpawnEntityLiving> SPAWN_LIVING_ENTITY_PACKET = PacketPlayOutSpawnEntityLiving.class;
    private static final Class<PacketPlayOutEntityStatus> ENTITY_STATUS_CLASS = PacketPlayOutEntityStatus.class;

    private static Field ENTITY_ID_FIELD;
    private static Field ENTITY_TYPE_FIELD;

    private static Field LOCATION_X_FIELD;
    private static Field LOCATION_Y_FIELD;
    private static Field LOCATION_Z_FIELD;

    private static Field LOCATION_PITCH_FIELD;
    private static Field LOCATION_YAW_FIELD;
    private static Field LOCATION_HEAD_YAW_FIELD;

    private static Field VELOCITY_X_FIELD;
    private static Field VELOCITY_Y_FIELD;
    private static Field VELOCITY_Z_FIELD;

    private static Field ENTITY_STATUS_ENTITY_ID_FIELD;
    private static Field ENTITY_STATUS_ID_FIELD;

    private static Field DATAWATCHER_FIELD;

    static
    {
        ENTITY_ID_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "a");
        ENTITY_TYPE_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "b");

        LOCATION_X_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "c");
        LOCATION_Y_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "d");
        LOCATION_Z_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "e");

        LOCATION_YAW_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "i");
        LOCATION_PITCH_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "j");
        LOCATION_HEAD_YAW_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "k");


        VELOCITY_X_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "f");
        VELOCITY_Y_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "g");
        VELOCITY_Z_FIELD = ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "h");

        ENTITY_STATUS_ENTITY_ID_FIELD = ReflectionUtil.getField(ENTITY_STATUS_CLASS, "a");
        ENTITY_STATUS_ID_FIELD = ReflectionUtil.getField(ENTITY_STATUS_CLASS, "b");

        DATAWATCHER_FIELD =  ReflectionUtil.getField(SPAWN_LIVING_ENTITY_PACKET, "l");
    }

    private static final NMSMetadata HEALTH = new NMSMetadata(6, NMSMetadata.NMSMetadataType.FLOAT);
    private static final NMSMetadata POTION_EFFECT_COLOR = new NMSMetadata(7, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata AMBIENT_POTION_EFFECT = new NMSMetadata(8, NMSMetadata.NMSMetadataType.BYTE);
    private static final NMSMetadata ARROW_COUNT = new NMSMetadata(9, NMSMetadata.NMSMetadataType.BYTE);

    private float health = 1f;
    private int potionEffectColor;
    private byte ambientPotionEffect;
    private byte arrowCount;
    private int attachedTo = -1;

    public LivingRender(EntityType entityType)
    {
        super(entityType);

        HEALTH.init(this, health);
        POTION_EFFECT_COLOR.init(this, this.potionEffectColor);
        AMBIENT_POTION_EFFECT.init(this, this.ambientPotionEffect);
        ARROW_COUNT.init(this, this.arrowCount);
    }

    @Override
    protected EntityRender spawnFor(List<Player> players)
    {
        PacketPlayOutSpawnEntityLiving spawnLivingPacket = new PacketPlayOutSpawnEntityLiving();

        try {
            ENTITY_ID_FIELD.setInt(spawnLivingPacket, this.getEntityId());

            LOCATION_X_FIELD.setInt(spawnLivingPacket, toFloatingPointNum(this.getX()));
            LOCATION_Y_FIELD.setInt(spawnLivingPacket, toFloatingPointNum(this.getY()));
            LOCATION_Z_FIELD.setInt(spawnLivingPacket, toFloatingPointNum(this.getZ()));

            LOCATION_PITCH_FIELD.setByte(spawnLivingPacket, (byte) this.toAngle(this.getPitch()));
            LOCATION_YAW_FIELD.setByte(spawnLivingPacket, (byte) this.toAngle(this.getYaw()));
            LOCATION_HEAD_YAW_FIELD.setByte(spawnLivingPacket, (byte) this.toAngle(this.getYaw()));

            ENTITY_TYPE_FIELD.setByte(spawnLivingPacket, (byte) this.getEntityType().getTypeId());
            DATAWATCHER_FIELD.set(spawnLivingPacket, this.getDataWatcher());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.broadcastPacket(players, spawnLivingPacket);
        return this;
    }

    @Override
    protected EntityRender spawnFor(Player player)
    {
        PacketPlayOutSpawnEntityLiving spawnLivingPacket = new PacketPlayOutSpawnEntityLiving();

        try {
            ENTITY_ID_FIELD.setInt(spawnLivingPacket, this.getEntityId());

            LOCATION_X_FIELD.setInt(spawnLivingPacket, toFloatingPointNum(this.getX()));
            LOCATION_Y_FIELD.setInt(spawnLivingPacket, toFloatingPointNum(this.getY()));
            LOCATION_Z_FIELD.setInt(spawnLivingPacket, toFloatingPointNum(this.getZ()));

            LOCATION_PITCH_FIELD.setByte(spawnLivingPacket, (byte) this.toAngle(this.getPitch()));
            LOCATION_YAW_FIELD.setByte(spawnLivingPacket, (byte) this.toAngle(this.getYaw()));
            LOCATION_HEAD_YAW_FIELD.setByte(spawnLivingPacket, (byte) this.toAngle(this.getYaw()));

            ENTITY_TYPE_FIELD.setByte(spawnLivingPacket, (byte) this.getEntityType().getTypeId());
            DATAWATCHER_FIELD.set(spawnLivingPacket, this.getDataWatcher());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.sendPacket(player, spawnLivingPacket);
        return this;
    }


    public LivingRender setHealth(float health) {
        this.health = health;
        HEALTH.set(this, this.health);
        pushMetadataUpdates();
        return this;
    }

    public LivingRender setPotionColor(int potionEffectColor) {
        this.potionEffectColor = potionEffectColor;
        POTION_EFFECT_COLOR.set(this, this.potionEffectColor);
        pushMetadataUpdates();
        return this;
    }

    public LivingRender setPotionIsAmbient(boolean ambientPotionEffect) {
        this.ambientPotionEffect = ambientPotionEffect ? (byte) 1 : (byte)0;
        AMBIENT_POTION_EFFECT.set(this, this.ambientPotionEffect);
        pushMetadataUpdates();
        return this;
    }

    public LivingRender setArrowCount(byte arrowCount) {
        this.arrowCount = arrowCount;
        ARROW_COUNT.set(this, this.arrowCount);
        pushMetadataUpdates();
        return this;
    }


    public void playEntityFall(List<Player> viewer)
    {
        playStatus(viewer, (byte) 2);
    }

    public void playStatus(List<Player> viewers, byte status)
    {
        PacketPlayOutEntityStatus entityStatus = new PacketPlayOutEntityStatus();
        try {
            ENTITY_STATUS_ENTITY_ID_FIELD.set(entityStatus, this.getEntityId());
            ENTITY_STATUS_ID_FIELD.set(entityStatus, status);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Player viewer : viewers) {
            this.sendPacket(viewer, entityStatus);
        }
    }
}
