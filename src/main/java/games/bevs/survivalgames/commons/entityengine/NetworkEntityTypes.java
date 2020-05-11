package games.bevs.survivalgames.commons.entityengine;

import org.bukkit.entity.EntityType;

/**
 * ref
 *  https://github.com/Bukkit/mc-dev/blob/c1627dc9cc7505581993eb0fa15597cb36e94244/net/minecraft/server/EntityTrackerEntry.java
 */
public class NetworkEntityTypes
{
    public static int getNetworkEntityFromEntityType(EntityType entityType)
    {
        int id = -1;
        switch (entityType)
        {
            case DROPPED_ITEM:
                break;
            case EXPERIENCE_ORB:
                break;
            case LEASH_HITCH:
                break;
            case PAINTING:
                break;
            case ARROW:
                id = 60;
                break;
            case SNOWBALL:
                id = 61;
                break;
            case FIREBALL:
                break;
            case SMALL_FIREBALL:
                break;
            case ENDER_PEARL:
                id = 65;
                break;
            case ENDER_SIGNAL:
                id = 72;
                break;
            case THROWN_EXP_BOTTLE:
                break;
            case ITEM_FRAME:
                break;
            case WITHER_SKULL:
                break;
            case PRIMED_TNT:
                id = 50;
                break;
            case FALLING_BLOCK:
                id = 70;
                break;
            case FIREWORK:
                id = 76;
                break;
            case ARMOR_STAND:
                id = 78;
                break;
            case BOAT:
                id = 1;
                break;
            case MINECART_FURNACE:
            case MINECART_CHEST:
            case MINECART_COMMAND:
            case MINECART_TNT:
            case MINECART_HOPPER:
            case MINECART_MOB_SPAWNER:
            case MINECART:
                id = 10;
                break;
            case CREEPER:
                break;
            case SKELETON:
                break;
            case SPIDER:
                break;
            case GIANT:
                break;
            case ZOMBIE:
                id = 54;
                break;
            case SLIME:
                break;
            case GHAST:
                break;
            case PIG_ZOMBIE:
                break;
            case ENDERMAN:
                break;
            case CAVE_SPIDER:
                break;
            case SILVERFISH:
                break;
            case BLAZE:
                break;
            case MAGMA_CUBE:
                break;
            case ENDER_DRAGON:
                break;
            case WITHER:
                id = 83;
                break;
            case BAT:
                break;
            case WITCH:
                break;
            case ENDERMITE:
                break;
            case GUARDIAN:
                break;
            case PIG:
                break;
            case SHEEP:
                break;
            case COW:
                break;
            case CHICKEN:
                break;
            case SQUID:
                break;
            case WOLF:
                break;
            case MUSHROOM_COW:
                break;
            case SNOWMAN:
                break;
            case OCELOT:
                break;
            case IRON_GOLEM:
                break;
            case HORSE:
                break;
            case RABBIT:
                break;
            case VILLAGER:
                break;
            case ENDER_CRYSTAL:
                id = 51;
                break;
            case SPLASH_POTION:
                break;
            case EGG:
                id = 62;
                break;
            case FISHING_HOOK:
                id = 90;
                break;
            case LIGHTNING:
                break;
            case WEATHER:
                break;
            case PLAYER:
                break;
            case COMPLEX_PART:
                break;
            case UNKNOWN:
                break;
        }

        return id;
    }
}
