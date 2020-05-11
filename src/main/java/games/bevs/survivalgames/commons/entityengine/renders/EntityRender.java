package games.bevs.survivalgames.commons.entityengine.renders;

import games.bevs.survivalgames.commons.entityengine.EntityEngine;
import games.bevs.survivalgames.commons.entityengine.EntityPuppeteer;
import games.bevs.survivalgames.commons.entityengine.NetworkEntityTypes;
import games.bevs.survivalgames.commons.entityengine.ReflectionUtil;
import games.bevs.survivalgames.commons.entityengine.listeners.AttackListener;
import games.bevs.survivalgames.commons.entityengine.listeners.RightClickListener;
import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Getter
public class EntityRender
{
    //SPAWN ENTITY PACKET
    private static final Class<PacketPlayOutSpawnEntity> SPAWN_ENTITY_PACKET = PacketPlayOutSpawnEntity.class;

    private static Field ENTITY_ID_FIELD;
    private static Field ENTITY_TYPE_FIELD;

    private static Field LOCATION_X_FIELD;
    private static Field LOCATION_Y_FIELD;
    private static Field LOCATION_Z_FIELD;

    private static Field LOCATION_PITCH_FIELD;
    private static Field LOCATION_YAW_FIELD;

    private static Field DATA_FIELD;

    private static Field VELOCITY_X_FIELD;
    private static Field VELOCITY_Y_FIELD;
    private static Field VELOCITY_Z_FIELD;

    //TELEPORT PACKET
    private static final Class<PacketPlayOutEntityTeleport> ENTITY_TELEPORT_PACKET = PacketPlayOutEntityTeleport.class;

    private static Field TELEPORT_ENTITY_ID_FIELD;

    private static Field TELEPORT_LOCATION_X_FIELD;
    private static Field TELEPORT_LOCATION_Y_FIELD;
    private static Field TELEPORT_LOCATION_Z_FIELD;

    private static Field TELEPORT_LOCATION_PITCH_FIELD;
    private static Field TELEPORT_LOCATION_YAW_FIELD;
    private static Field TELEPORT_ON_GROUND_FIELD;
    //END


    //PacketPlayOutEntityLook

    //end

    private static final byte ON_FIRE_FLAG = 0x01;
    private static final byte CROUCH_FLAG = 0x02;
    private static final byte SPRINTING_FLAG = 0x08;
    private static final byte EATING_DRINKING_BLOCKING_FLAG = 0x10;
    private static final byte INVISIBLE_FLAG = 0x20;

    private static final NMSMetadata MAIN_MASK = new NMSMetadata(0, NMSMetadata.NMSMetadataType.BYTE);//IDataWatcherObject.newByteObject(0);
    private static final NMSMetadata AIR_TIME = new NMSMetadata(1, NMSMetadata.NMSMetadataType.INT);//IDataWatcherObject.newIntObject(1);
    private static final NMSMetadata CUSTOM_NAME = new NMSMetadata(2, NMSMetadata.NMSMetadataType.STRING);//IDataWatcherObject.newStringObject(2);
    private static final NMSMetadata CUSTOM_NAME_VISIBLE = new NMSMetadata(3, NMSMetadata.NMSMetadataType.BYTE);//IDataWatcherObject.newByteObject(3);
    private static final NMSMetadata IS_SILENT = new NMSMetadata(4, NMSMetadata.NMSMetadataType.BYTE);//IDataWatcherObject.newByteObject(4);

    private static Method GET_DATAWATCHER_OBJECT;
    private static Field UPDATE_FIELD;

    static
    {
        //SPAWN ENTITY
        ENTITY_ID_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "a");

        LOCATION_X_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "b");
        LOCATION_Y_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "c");
        LOCATION_Z_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "d");

        LOCATION_PITCH_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "h");
        LOCATION_YAW_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "i");

        DATA_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "k");
        ENTITY_TYPE_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "j");

        VELOCITY_X_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "e");
        VELOCITY_Y_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "f");
        VELOCITY_Z_FIELD = ReflectionUtil.getField(SPAWN_ENTITY_PACKET, "g");

        //TELEPORT
        TELEPORT_ENTITY_ID_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "a");

        TELEPORT_LOCATION_X_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "b");
        TELEPORT_LOCATION_Y_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "c");
        TELEPORT_LOCATION_Z_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "d");

        TELEPORT_LOCATION_YAW_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "e");
        TELEPORT_LOCATION_PITCH_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "f");

        TELEPORT_ON_GROUND_FIELD = ReflectionUtil.getField(ENTITY_TELEPORT_PACKET, "g");


        Class<?> clazz = DataWatcher.class;
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getReturnType() != null && m.getReturnType().getSimpleName().toLowerCase().contains("watchableobject")) {
                m.setAccessible(true);
                GET_DATAWATCHER_OBJECT = m;
                break;
            }
        }
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getType().equals(boolean.class) && f.getName().equalsIgnoreCase("e")) {
                f.setAccessible(true);
                UPDATE_FIELD = f;
                break;
            }
        }
    }


    private static int AUTO_COUNT_ENTITY_ID = Integer.MAX_VALUE / 2;
    public EntityPuppeteer entityPuppeteer;
    @Setter @NonNull
    private int entityId;

    private double x = 0, y = 0, z = 0;
    private float pitch = 0, yaw = 0;
    private int data = 0;
    private EntityType entityType;
    private final DataWatcher dataWatcher = new DataWatcher(null);

    private byte mainMask;
    private short airTime = 300;
    private String customName = "";
    private boolean customNameVisible;
    private boolean isSilent;
    private boolean updateMetadata;

    @Getter @Setter
    private Location lastLocation;
    private float lastYaw = 0
                , lastPitch = 0;

    private List<RightClickListener> rightClickListeners = new LinkedList<>();
    private List<AttackListener> AttackListeners = new LinkedList<>();
    private WeakHashMap<Player, Boolean> canSeeEntities = new WeakHashMap<Player, Boolean>();

    public EntityRender(EntityType entityType)
    {
        this.entityType = entityType;
        this.entityId = ++AUTO_COUNT_ENTITY_ID;

        registerMetadata(MAIN_MASK.index, mainMask);
        registerMetadata(AIR_TIME.index, airTime);
        registerMetadata(CUSTOM_NAME.index, customName);
        registerMetadata(CUSTOM_NAME_VISIBLE.index, (byte) (customNameVisible ? 1 : 0));
        registerMetadata(IS_SILENT.index, (byte) (isSilent ? 1 : 0));
    }

    protected EntityRender spawnFor(List<Player> players)
    {
        PacketPlayOutSpawnEntity spawnEntity = new PacketPlayOutSpawnEntity();

        try {
            ENTITY_ID_FIELD.setInt(spawnEntity, this.getEntityId());

            LOCATION_X_FIELD.setInt(spawnEntity, this.toFloatingPointNum(this.getX()));
            LOCATION_Y_FIELD.setInt(spawnEntity, this.toFloatingPointNum(this.getY()));
            LOCATION_Z_FIELD.setInt(spawnEntity, this.toFloatingPointNum(this.getZ()));

            LOCATION_PITCH_FIELD.setInt(spawnEntity, this.toAngle(this.getPitch()));
            LOCATION_YAW_FIELD.setInt(spawnEntity, this.toAngle(this.getYaw()));

            if(this.getData() == 0)
            {
                double velX = 0;
                double velY = 0;
                double velZ = 0;
                double maxVelocity = 3.9D;

                if (velX < -maxVelocity)
                    velX = -maxVelocity;
                if (velY < -maxVelocity)
                    velY = -maxVelocity;
                if (velZ < -maxVelocity)
                    velZ = -maxVelocity;

                if (velX > maxVelocity)
                    velX = maxVelocity;
                if (velY > maxVelocity)
                    velY = maxVelocity;
                if (velZ > maxVelocity)
                    velZ = maxVelocity;

                VELOCITY_X_FIELD.setInt(spawnEntity, this.toVelocityValue(this.getX()));
                VELOCITY_Y_FIELD.setInt(spawnEntity, this.toVelocityValue(this.getY()));
                VELOCITY_Z_FIELD.setInt(spawnEntity, this.toVelocityValue(this.getZ()));
            }

            DATA_FIELD.setInt(spawnEntity, this.getData());
            ENTITY_TYPE_FIELD.setInt(spawnEntity, NetworkEntityTypes.getNetworkEntityFromEntityType(this.getEntityType()));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        PacketPlayOutEntityMetadata ppoem = new PacketPlayOutEntityMetadata(this.getEntityId(), this.getDataWatcher(), true);

        for (Player player : players) {
            this.canSeeEntities.put(player, true);
        }

        this.broadcastPacket(players, spawnEntity);
        this.broadcastPacket(players, ppoem);

        return this;
    }

    protected EntityRender spawnFor(Player player)
    {
        PacketPlayOutSpawnEntity spawnEntity = new PacketPlayOutSpawnEntity();

        try {
            ENTITY_ID_FIELD.setInt(spawnEntity, this.getEntityId());

            LOCATION_X_FIELD.setInt(spawnEntity, this.toFloatingPointNum(this.getX()));
            LOCATION_Y_FIELD.setInt(spawnEntity, this.toFloatingPointNum(this.getY()));
            LOCATION_Z_FIELD.setInt(spawnEntity, this.toFloatingPointNum(this.getZ()));

            LOCATION_PITCH_FIELD.setInt(spawnEntity, this.toAngle(this.getPitch()));
            LOCATION_YAW_FIELD.setInt(spawnEntity, this.toAngle(this.getYaw()));

            if(this.getData() == 0)
            {
                double velX = 0;
                double velY = 0;
                double velZ = 0;
                double maxVelocity = 3.9D;

                if (velX < -maxVelocity)
                    velX = -maxVelocity;
                if (velY < -maxVelocity)
                    velY = -maxVelocity;
                if (velZ < -maxVelocity)
                    velZ = -maxVelocity;

                if (velX > maxVelocity)
                    velX = maxVelocity;
                if (velY > maxVelocity)
                    velY = maxVelocity;
                if (velZ > maxVelocity)
                    velZ = maxVelocity;

                VELOCITY_X_FIELD.setInt(spawnEntity, this.toVelocityValue(this.getX()));
                VELOCITY_Y_FIELD.setInt(spawnEntity, this.toVelocityValue(this.getY()));
                VELOCITY_Z_FIELD.setInt(spawnEntity, this.toVelocityValue(this.getZ()));
            }

            DATA_FIELD.setInt(spawnEntity, this.getData());
            ENTITY_TYPE_FIELD.setInt(spawnEntity, NetworkEntityTypes.getNetworkEntityFromEntityType(this.getEntityType()));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        PacketPlayOutEntityMetadata ppoem = new PacketPlayOutEntityMetadata(this.getEntityId(), this.getDataWatcher(), true);


        this.canSeeEntities.put(player, true);

        this.sendPacket(player, spawnEntity);
        this.sendPacket(player, ppoem);

        return this;
    }

    public EntityRender removeFor(Player player)
    {
        this.canSeeEntities.remove(player);

        PacketPlayOutEntityDestroy destroyEntityPacket = new PacketPlayOutEntityDestroy(this.getEntityId());
        this.sendPacket(player, destroyEntityPacket);
        return this;
    }

    public EntityRender removeFor(List<Player> players)
    {
        for (Player player : players) {
            this.canSeeEntities.remove(player);
        }

        PacketPlayOutEntityDestroy destroyEntityPacket = new PacketPlayOutEntityDestroy(this.getEntityId());
        this.broadcastPacket(players, destroyEntityPacket);
        return this;
    }

    protected EntityRender sendTeleportFor(List<Player> players, Vector newPosition, float newYaw, float newPitch)
    {
        PacketPlayOutEntityTeleport ppoet = new PacketPlayOutEntityTeleport();

        try {
            TELEPORT_ENTITY_ID_FIELD.setInt(ppoet, this.getEntityId());

            TELEPORT_LOCATION_X_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getX()));
            TELEPORT_LOCATION_Y_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getY()));
            TELEPORT_LOCATION_Z_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getZ()));

            TELEPORT_LOCATION_PITCH_FIELD.setByte(ppoet, (byte) this.toAngle(newPitch));
            TELEPORT_LOCATION_YAW_FIELD.setByte(ppoet, (byte) this.toAngle(newYaw));

            TELEPORT_ON_GROUND_FIELD.setBoolean(ppoet, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

       this.broadcastPacket(players, ppoet);
        return this;
    }

    public EntityRender sendTeleportFor(Player player, Vector newPosition, float newYaw, float newPitch)
    {
        PacketPlayOutEntityTeleport ppoet = new PacketPlayOutEntityTeleport();

        try {
            TELEPORT_ENTITY_ID_FIELD.setInt(ppoet, this.getEntityId());

            TELEPORT_LOCATION_X_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getX()));
            TELEPORT_LOCATION_Y_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getY()));
            TELEPORT_LOCATION_Z_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getZ()));

            TELEPORT_LOCATION_PITCH_FIELD.setByte(ppoet, (byte) this.toAngle(newPitch));
            TELEPORT_LOCATION_YAW_FIELD.setByte(ppoet, (byte) this.toAngle(newYaw));

            TELEPORT_ON_GROUND_FIELD.setBoolean(ppoet, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.sendPacket(player, ppoet);
        return this;
    }


    public EntityRender sendTeleportForAll(List<Player> players, Vector newPosition, float newYaw, float newPitch)
    {
        PacketPlayOutEntityTeleport ppoet = new PacketPlayOutEntityTeleport();

        try {
            TELEPORT_ENTITY_ID_FIELD.setInt(ppoet, this.getEntityId());

            TELEPORT_LOCATION_X_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getX()));
            TELEPORT_LOCATION_Y_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getY()));
            TELEPORT_LOCATION_Z_FIELD.setInt(ppoet, this.toFloatingPointNum(newPosition.getZ()));

            TELEPORT_LOCATION_PITCH_FIELD.setByte(ppoet, (byte) this.toAngle(newPitch));
            TELEPORT_LOCATION_YAW_FIELD.setByte(ppoet, (byte) this.toAngle(newYaw));

            TELEPORT_ON_GROUND_FIELD.setBoolean(ppoet, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.broadcastPacket(players, ppoet);
        return this;
    }

    protected EntityRender sendRotateFor(List<Player> players, float yaw, float pitch)
    {
        try {
            PacketPlayOutEntity.PacketPlayOutEntityLook ppoel = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.getEntityId(), (byte) this.toAngle(yaw), (byte) this.toAngle(pitch), true);
            players.forEach(player -> {
                CraftPlayer cp = (CraftPlayer) player;
                cp.getHandle().playerConnection.sendPacket(ppoel);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }



    protected EntityRender sendPositionFor(List<Player> players, byte deltaX, byte deltaY, byte deltaZ)
    {
        try {
            PacketPlayOutEntity.PacketPlayOutRelEntityMove ppoem = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(this.getEntityId(), deltaX, deltaY, deltaZ, true);
            players.forEach(player -> {
                CraftPlayer cp = (CraftPlayer) player;
                cp.getHandle().playerConnection.sendPacket(ppoem);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    protected EntityRender sendPositionAndRotationFor(List<Player> players, byte deltaX, byte deltaY, byte deltaZ, float yaw, float pitch)
    {
        try {
            PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook ppoem = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.getEntityId(), deltaX, deltaY, deltaZ, (byte) this.toAngle(yaw), (byte) this.toAngle(pitch), true);
            players.forEach(player -> {
                CraftPlayer cp = (CraftPlayer) player;
                cp.getHandle().playerConnection.sendPacket(ppoem);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    protected EntityRender sendHeadRotationFor(List<Player> players, float headYaw)
    {
        return this;
    }

    protected EntityRender sendOtherUpdatesFor(List<Player> players)
    {
        return this;
    }

    protected EntityRender sendBasicUpdatesFor(List<Player> players)
    {
        return this;
    }

    public EntityRender sendPacket(Player player, Packet... packets)
    {
        EntityEngine.sendPacket(player, packets);
        return this;
    }

    public EntityRender broadcastPacket(List<Player> players, Packet... packets)
    {
        EntityEngine.broadcastPacket(players, packets);
        return this;
    }

    protected int toFloatingPointNum(double value)
    {
        return MathHelper.floor(value * 32.0D);
    }

    protected int toVelocityValue(double value)
    {
        return (int) (value * 8000.0D);
    }

    protected int toAngle(float angle) {
        return MathHelper.d(angle * 256.0F / 360.0F);
    }

    public void spawn(List<Player> players, Location location)
    {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();

        this.pitch = location.getPitch();
        this.yaw = location.getYaw();

        this.setLastLocation(location);

        this.spawnFor(players);
    }

    public void spawn(Player player, Location location)
    {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();

        this.pitch = location.getPitch();
        this.yaw = location.getYaw();

        this.spawnFor(player);
    }

    public <T> EntityRender registerMetadata(int index, T value) {
        this.dataWatcher.a(index, value);
        return this;
    }

    public <T> void noEWatch(int i, T t0) {
        try {
            DataWatcher.WatchableObject datawatcher_watchableobject = (DataWatcher.WatchableObject) GET_DATAWATCHER_OBJECT.invoke(dataWatcher, i);
            if (ObjectUtils.notEqual(t0, datawatcher_watchableobject.b())) {
                datawatcher_watchableobject.a(t0);
                datawatcher_watchableobject.a(true);
                UPDATE_FIELD.set(dataWatcher, true);
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void pushMetadataUpdates() {
        updateMetadata = true;
    }

    public void forcePushMetadataUpdates(List<Player> players)
    {
        PacketPlayOutEntityMetadata ppoem = new PacketPlayOutEntityMetadata(this.getEntityId(), this.dataWatcher, true);
        this.broadcastPacket(players, ppoem);
    }

    public void forcePushMetadataUpdates(Player player)
    {
        PacketPlayOutEntityMetadata ppoem = new PacketPlayOutEntityMetadata(this.getEntityId(), this.dataWatcher, true);
        this.sendPacket(player, ppoem);
    }

    public EntityRender setAction(boolean action){
        byte b = mainMask;
        if (action)
            b |= EATING_DRINKING_BLOCKING_FLAG;
        else
            b &= ~EATING_DRINKING_BLOCKING_FLAG;
        mainMask = b;
        MAIN_MASK.set(this, this.mainMask);
        pushMetadataUpdates();
        return this;
    }

    public EntityRender setInvisible(boolean invis){
        byte b = mainMask;
        if (invis)
            b |= INVISIBLE_FLAG;
        else
            b &= ~INVISIBLE_FLAG;
        mainMask = b;
        MAIN_MASK.set(this, this.mainMask);
        pushMetadataUpdates();
        return this;
    }

    public EntityRender setCrouch(boolean crouch) {
        byte b = mainMask;
        if (crouch)
            b |= CROUCH_FLAG;
        else
            b &= ~CROUCH_FLAG;
        mainMask = b;
        MAIN_MASK.set(this, this.mainMask);
        pushMetadataUpdates();
        return this;
    }

    public EntityRender setFire(boolean fire) {
        byte b = mainMask;
        if (fire)
            b |= ON_FIRE_FLAG;
        b &= ~ON_FIRE_FLAG;
        mainMask = b;
        MAIN_MASK.set(this, this.mainMask);
        pushMetadataUpdates();
        return this;
    }

    public EntityRender setSprinting(boolean sprint) {
        byte b = mainMask;
        if (sprint)
            b |= SPRINTING_FLAG;
        else
            b &= ~SPRINTING_FLAG;
        mainMask = b;
        MAIN_MASK.set(this, Byte.valueOf(this.mainMask));
        pushMetadataUpdates();
        return this;
    }

    public EntityRender setCustomName(String customName) {
        if (!this.customName.equals(customName)) {
            this.customName = customName;
            CUSTOM_NAME.set(this, this.customName);
            pushMetadataUpdates();
        }
        return this;
    }

    public EntityRender setCustomNameVisible(boolean customNameVisible) {
        if (this.customNameVisible != customNameVisible) {
            this.customNameVisible = customNameVisible;
            CUSTOM_NAME_VISIBLE.set(this, (byte) (this.customNameVisible ? 1 : 0));
            pushMetadataUpdates();
        }
        return this;
    }

    public EntityRender setIsSilent(boolean isSilent) {
        if (this.isSilent != isSilent) {
            this.isSilent = isSilent;
            IS_SILENT.set(this, (byte) (this.isSilent ? 1 : 0));
            pushMetadataUpdates();
        }
        return this;
    }

    public boolean canSee(Player player)
    {
        return this.canSeeEntities.get(player) != null;
    }

    public void tick()
    {
        List<Player> players = new LinkedList<>();

        //send packet updates
        for (Player player : Bukkit.getOnlinePlayers())
            if(this.canSee(player)) players.add(player);

        boolean rotationChange = getEntityPuppeteer().getYaw() != this.lastYaw || getEntityPuppeteer().getPitch() != this.lastPitch;

        this.sendHeadRotationFor(players, getEntityPuppeteer().getYaw());

        this.lastPitch = entityPuppeteer.getPitch();
        this.lastYaw = entityPuppeteer.getYaw();


    }

    public void sendTeleportFor(List<Player> players, Location location) {
    }
}
