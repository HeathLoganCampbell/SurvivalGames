package games.bevs.survivalgames.commons.entityengine.renders.objects.misc;

import games.bevs.survivalgames.commons.entityengine.ReflectionUtil;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import games.bevs.survivalgames.commons.entityengine.renders.objects.ObjectRender;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityExperienceOrb;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public class ExpOrbRender extends ObjectRender
{
    private static final Class<PacketPlayOutSpawnEntityExperienceOrb> SPAWN_EXP_ORB_PACKET = PacketPlayOutSpawnEntityExperienceOrb.class;

    private static Field ENTITY_ID_FIELD;

    private static Field LOCATION_X_FIELD;
    private static Field LOCATION_Y_FIELD;
    private static Field LOCATION_Z_FIELD;

    private static Field EXP_AMOUNT_FIELD;

    static
    {
        ENTITY_ID_FIELD = ReflectionUtil.getField(SPAWN_EXP_ORB_PACKET, "a");

        LOCATION_X_FIELD = ReflectionUtil.getField(SPAWN_EXP_ORB_PACKET, "b");
        LOCATION_Y_FIELD = ReflectionUtil.getField(SPAWN_EXP_ORB_PACKET, "c");
        LOCATION_Z_FIELD = ReflectionUtil.getField(SPAWN_EXP_ORB_PACKET, "d");

        EXP_AMOUNT_FIELD = ReflectionUtil.getField(SPAWN_EXP_ORB_PACKET, "e");
    }

    @Getter @Setter
    private short expAmount;

    public ExpOrbRender()
    {
        super(EntityType.UNKNOWN);
    }


    @Override
    protected EntityRender spawnFor(List<Player> players)
    {
        PacketPlayOutSpawnEntityExperienceOrb spawnOrb = new PacketPlayOutSpawnEntityExperienceOrb();

        try {
            ENTITY_ID_FIELD.setInt(spawnOrb, this.getEntityId());

            LOCATION_X_FIELD.setInt(spawnOrb, this.toFloatingPointNum(this.getX()));
            LOCATION_Y_FIELD.setInt(spawnOrb, this.toFloatingPointNum(this.getY()));
            LOCATION_Z_FIELD.setInt(spawnOrb, this.toFloatingPointNum(this.getZ()));

            EXP_AMOUNT_FIELD.setInt(spawnOrb, this.getExpAmount());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.broadcastPacket(players, spawnOrb);
        return null;
    }
}
