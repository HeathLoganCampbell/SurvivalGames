package games.bevs.survivalgames.commons.entityengine;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.entityengine.listeners.AttackListener;
import games.bevs.survivalgames.commons.entityengine.listeners.RightClickListener;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import games.bevs.survivalgames.commons.protocol.PacketListener;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
//import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Allows you to create entities with only packets
 * - Holograms
 * - Fake Players
 * - Pets
 * - Disgisies
 */
@AllArgsConstructor
public class EntityEngine
{
    private static final Field ENTITY_ID_FIELD = ReflectionUtil.getField(PacketPlayInUseEntity.class, "a");
    private Map<Integer, EntityRender> entities = new HashMap<>();
    private JavaPlugin plugin;

    public static EntityEngine enable(JavaPlugin plugin)
    {
        return new EntityEngine(plugin);
    }

    public EntityEngine(JavaPlugin plugin)
    {
        this.plugin = plugin;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            for (Map.Entry<Integer, EntityRender> integerEntityRenderEntry : this.entities.entrySet()) {
                if(integerEntityRenderEntry.getValue().getEntityPuppeteer() != null)
                    integerEntityRenderEntry.getValue().tick();
            }
        }, 1l, 1l);

//        Library.getProtocolManager().registerPacketListener(new Class<?>[] { PacketPlayOutMapChunk.class }, new ChunkSendProtocolListener());

        SurvivalGames.get().getProtocolManager().registerPacketListener(new Class<?>[] {
                PacketPlayInUseEntity.class
        }, new PacketListener() {

            @Override
            public Object onPacketOutAsync(Player receiver, Channel channel, Object packet)
            {
                return receiver;
            }

            @Override
            public Object onPacketInAsync(Player sender, Channel channel, Object packet)
            {
                if(packet.getClass() == PacketPlayInUseEntity.class)
                {
                    int id = ReflectionUtil.getValue(ENTITY_ID_FIELD, packet);
                    EntityRender entity = entities.get(id);
                    if(entity == null) return packet;

                    PacketPlayInUseEntity useEntityPacket = (PacketPlayInUseEntity) packet;
                    PacketPlayInUseEntity.EnumEntityUseAction action = useEntityPacket.a();
                    Vec3D interactLoc = useEntityPacket.b();

                    if (action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT || action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT)
                    {
                        for (RightClickListener rightClickListener : entity.getRightClickListeners()) {
                            rightClickListener.onRightClicked(sender, action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT);
                        }
                    }
                    else if (action == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK)
                    {
                        for (AttackListener attackListener : entity.getAttackListeners()) {
                            attackListener.onAttacked(sender);
                        }
                    }

                    return packet;
                }

                return packet;
            }
        });
    }

    public void spawn(List<Player> viewers, EntityRender entityRender, Location location)
    {
        entityRender.spawn(viewers, location);
        entities.put(entityRender.getEntityId(), entityRender);
    }

    public void destroyEntity(List<Player> players, Entity entity)
    {
       this.destroyEntity(players, entity.getEntityId());
    }

    public void destroyEntity(List<Player> players, int entityId)
    {
        PacketPlayOutEntityDestroy destroyEntityPacket = new PacketPlayOutEntityDestroy(entityId);
        broadcastPacket(players, destroyEntityPacket);
    }

    public void removeEntity(List<Player> players,  int entityId)
    {
        this.destroyEntity(players, entityId);
        entities.remove(entityId);
    }

    public void removeEntity(int entityId)
    {
        entities.remove(entityId);
    }


//    public void example()
//    {
//        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
//        ArmorStandRender expOrb = new ArmorStandRender();
//        expOrb.setCustomName("Dawg");
//        expOrb.setCustomNameVisible(true);
//        expOrb.setInvisible(true);
//        expOrb.spawn(players, new Location(Bukkit.getWorlds().get(0), 0.5, 67, -0.5, 0, 0));
////        expOrb.setIsSilent(true);
////        expOrb.spawn(players, new Location(Bukkit.getWorlds().get(0), 0.5, 67, -0.5, 0, 0));
////        expOrb.forcePushMetadataUpdates(players);
//    }

    public static void sendPacket(Player player, Packet... packet)
    {
        CraftPlayer cp = (CraftPlayer) player;
        for (Packet iteratorPacket : packet)
            cp.getHandle().playerConnection.sendPacket(iteratorPacket);
    }

    public static void broadcastPacket(List<Player> players, Packet... packet)
    {
        for (Player player : players)
        {
            CraftPlayer cp = (CraftPlayer) player;
            for (Packet iteratorPacket : packet)
                cp.getHandle().playerConnection.sendPacket(iteratorPacket);
        }
    }
}
