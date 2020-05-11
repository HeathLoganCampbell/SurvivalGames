package games.bevs.survivalgames.commons.entityengine.renders.objects.vehicles;

import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class MinecartRender extends VehicleRender
{
    private static final NMSMetadata SHAKE_POWER = new NMSMetadata(17, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata SHAKE_DIRECTION = new NMSMetadata(18, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata SHAKE_MULTIPLIER = new NMSMetadata(19, NMSMetadata.NMSMetadataType.FLOAT);

    private static final NMSMetadata BLOCK_ID = new NMSMetadata(20, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata BLOCK_Y = new NMSMetadata(21, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata SHOW_BLOCK = new NMSMetadata(22, NMSMetadata.NMSMetadataType.INT);

    private int blockId = 0;
    private int blockYoffset = 6;
    private int showBlock = 0;

    public MinecartRender()
    {
        super(EntityType.MINECART);

        this.registerMetadata(SHAKE_POWER.index, 0);
        this.registerMetadata(SHAKE_DIRECTION.index, 1);
        this.registerMetadata(SHAKE_MULTIPLIER.index, 0.0f);
//
        this.registerMetadata(BLOCK_ID.index, blockId);
        this.registerMetadata(BLOCK_Y.index, blockYoffset);
        this.registerMetadata(SHOW_BLOCK.index, (byte) showBlock);
    }

    public boolean isBlockVisible()
    {
        return this.showBlock != 0;
    }


    public void setBlockVisible(boolean visible)
    {
        this.showBlock = visible ? 1 : 0;
        this.registerMetadata(SHOW_BLOCK.index,  (byte) showBlock);
    }

    public void setBlock(Material material)
    {
        this.blockId = material.getId();
        this.registerMetadata(BLOCK_ID.index, blockId);
    }

    public void setBlockYOffset(int height)
    {
        this.blockYoffset = height;
        this.registerMetadata(BLOCK_Y.index, this.blockYoffset);
    }


}
