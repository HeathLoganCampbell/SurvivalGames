package games.bevs.survivalgames.commons.entityengine.renders.living.monstor;

import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import org.bukkit.entity.EntityType;

public class SkeletonRender extends MonsterRender
{
    private static final NMSMetadata TYPE = new NMSMetadata(13, NMSMetadata.NMSMetadataType.BYTE);
    private SkeletonType type = SkeletonType.LIGHT;

    public SkeletonRender()
    {
        super(EntityType.SKELETON);
        TYPE.init(this, this.type.value);

    }

    public SkeletonRender setType(SkeletonType type) {
        if (type != null && this.type != type) {
            this.type = type;
            TYPE.set(this, this.type.value);
            this.pushMetadataUpdates();
        }
        return this;
    }

    public SkeletonType getType() {
        return this.type;
    }

    public static enum SkeletonType
    {
        LIGHT(0),
        DARK(1);

        byte value;
        SkeletonType(int value)
        {
            this.value = (byte) value;
        }
    }
}
