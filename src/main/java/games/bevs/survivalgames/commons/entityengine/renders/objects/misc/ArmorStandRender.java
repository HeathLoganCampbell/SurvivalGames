package games.bevs.survivalgames.commons.entityengine.renders.objects.misc;

import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.entity.EntityType;

public class ArmorStandRender extends EntityRender
{
    private static final NMSMetadata BIT_MASK = new NMSMetadata(10, NMSMetadata.NMSMetadataType.BYTE);
    private static final NMSMetadata HEAD_POSITION = new NMSMetadata(11, NMSMetadata.NMSMetadataType.VECTOR3F);
    private static final NMSMetadata BODY_POSITION = new NMSMetadata(12, NMSMetadata.NMSMetadataType.VECTOR3F);
    private static final NMSMetadata LEFT_ARM_POSITION = new NMSMetadata(13, NMSMetadata.NMSMetadataType.VECTOR3F);
    private static final NMSMetadata RIGHT_ARM_POSITION = new NMSMetadata(14, NMSMetadata.NMSMetadataType.VECTOR3F);
    private static final NMSMetadata LEFT_LEG_POSITION = new NMSMetadata(15, NMSMetadata.NMSMetadataType.VECTOR3F);
    private static final NMSMetadata RIGHT_LEG_POSITION = new NMSMetadata(16, NMSMetadata.NMSMetadataType.VECTOR3F);

    private static final byte IS_SMALL_FLAG = 0x01;
    private static final byte HAS_GRAVITY = 0x02;
    private static final byte HAS_ARMS_FLAG = 0x04;
    private static final byte NO_BASEPLAY_FLAG = 0x08;
    private static final byte SET_MARKER_FLAG = 0x10;

    private byte bitMask;

    public ArmorStandRender()
    {
        super(EntityType.ARMOR_STAND);

        this.registerMetadata(BIT_MASK.index, bitMask);
        this.registerMetadata(HEAD_POSITION.index, new Vector3f(0,0,0));
        this.registerMetadata(BODY_POSITION.index, new Vector3f(0,0,0));
        this.registerMetadata(LEFT_ARM_POSITION.index, new Vector3f(0,0,0));
        this.registerMetadata(RIGHT_ARM_POSITION.index, new Vector3f(0,0,0));
        this.registerMetadata(LEFT_LEG_POSITION.index, new Vector3f(0,0,0));
        this.registerMetadata(RIGHT_LEG_POSITION.index, new Vector3f(0,0,0));
    }

    public ArmorStandRender setIsSmall(boolean isSmall) {
        byte b = bitMask;
        if (isSmall)
            b |= IS_SMALL_FLAG;
        else
            b &= ~IS_SMALL_FLAG;
        bitMask = b;
        this.noEWatch(BIT_MASK.index, bitMask);
        pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setHasGravity(boolean hasGravity) {
        byte b = bitMask;
        if (hasGravity)
            b |= HAS_GRAVITY;
        else
            b &= ~HAS_GRAVITY;
        bitMask = b;
        this.noEWatch(BIT_MASK.index, bitMask);
        pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setHasArms(boolean hasArms) {
        byte b = bitMask;
        if (hasArms)
            b |= HAS_ARMS_FLAG;
        else
            b &= ~HAS_ARMS_FLAG;
        bitMask = b;
        this.noEWatch(BIT_MASK.index, bitMask);
        pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setHasNoBaseplate(boolean noBaseplate) {
        byte b = bitMask;
        if (noBaseplate)
            b |= NO_BASEPLAY_FLAG;
        else
            b &= ~NO_BASEPLAY_FLAG;
        bitMask = b;
        this.noEWatch(BIT_MASK.index,  bitMask);
        pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setIsMarker(boolean isMarker) {
        byte b = bitMask;
        if (isMarker)
            b |= SET_MARKER_FLAG;
        else
            b &= ~SET_MARKER_FLAG;
        bitMask = b;
        this.noEWatch(BIT_MASK.index, bitMask);
        pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setHeadRotation(float yaw, float pitch, float roll) {
        this.noEWatch(HEAD_POSITION.index, new Vector3f(yaw,pitch,roll));
        this.pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setBodyRotation(float yaw, float pitch, float roll) {
        this.noEWatch(BODY_POSITION.index, new Vector3f(yaw,pitch,roll));
        this.pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setLeftArmRotation(float yaw, float pitch, float roll) {
        this.noEWatch(LEFT_ARM_POSITION.index, new Vector3f(yaw,pitch,roll));
        this.pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setRightArmRotation(float yaw, float pitch, float roll) {
        this.noEWatch(RIGHT_ARM_POSITION.index, new Vector3f(yaw,pitch,roll));
        this.pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setLeftLegRotation(float yaw, float pitch, float roll) {
        this.noEWatch(LEFT_LEG_POSITION.index, new Vector3f(yaw,pitch,roll));
        this.pushMetadataUpdates();
        return this;
    }

    public ArmorStandRender setRightLegRotation(float yaw, float pitch, float roll) {
        this.noEWatch(RIGHT_LEG_POSITION.index, new Vector3f(yaw,pitch,roll));
        this.pushMetadataUpdates();
        return this;
    }

}
