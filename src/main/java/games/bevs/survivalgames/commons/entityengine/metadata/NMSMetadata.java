package games.bevs.survivalgames.commons.entityengine.metadata;

import java.util.HashSet;
import java.util.Set;

import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Vector3f;

public class NMSMetadata {

    public final int index;
    public final int type;
    private final NMSMetadataType metaType;
    
    public NMSMetadata(int index, int type) {
        this.index = index;
        this.type = type;
        metaType = NMSMetadataType.get(type);
    }
    
    public NMSMetadata(int index, NMSMetadataType type) {
        this.index = index;
        this.type = type.typeID;
        metaType = type;
    }

    public <T> NMSMetadata init(EntityRender renderer, T value) {
        renderer.registerMetadata(index, value);
        return this;
    }
    
    public <T> NMSMetadata set(EntityRender renderer, T value) {
        renderer.noEWatch(index, value);
        return this;
    }
        
    public static enum NMSMetadataType {
        BYTE(0, byte.class, Byte.class),
        SHORT(1, short.class, Short.class),
        INT(2, int.class, Integer.class),
        FLOAT(3, float.class, Float.class),
        STRING(4, String.class),
        ITEM_STACK(5, ItemStack.class),
        BLOCK_POSITION(6, BlockPosition.class),
        VECTOR3F(7, Vector3f.class),;
        
        int typeID;
        Set<Class<?>> typeClasses = new HashSet<>();
        NMSMetadataType(int id, Class<?>...typeClasses){
            this.typeID = id;
            for (Class<?> c : typeClasses) {
                this.typeClasses.add(c);
            }
        }
        
        public static NMSMetadataType get(int id) {
            for (NMSMetadataType type : values()) {
                if (type.typeID == id)
                    return type;
            }
            return null;
        }
        
        public static NMSMetadataType get(Object object) {
            for (NMSMetadataType type : values()) {
                if (type.typeClasses.contains(object.getClass()))
                    return type;
            }
            return null;
        }
        
    }
    
}