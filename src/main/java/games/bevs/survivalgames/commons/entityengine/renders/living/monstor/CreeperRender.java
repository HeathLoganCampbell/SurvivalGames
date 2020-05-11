package games.bevs.survivalgames.commons.entityengine.renders.living.monstor;

import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.EntityType;

public class CreeperRender extends MonsterRender
{
    //FUSED 1, IDLE - 1
    private static final NMSMetadata STATE = new NMSMetadata(16, NMSMetadata.NMSMetadataType.BYTE);
    private static final NMSMetadata CHARGED = new NMSMetadata(17, NMSMetadata.NMSMetadataType.BYTE);

    @Getter
    private CreeperState creeperState = CreeperState.IDLE;
    @Getter
    private boolean charged = false;

    public CreeperRender()
    {
        super(EntityType.CREEPER);
        STATE.init(this, (byte) -1);
        CHARGED.init(this, (byte) 0);
    }

    public CreeperRender setState(CreeperState creeperState)
    {
        if (creeperState != null && this.creeperState != creeperState)
        {
            this.creeperState = creeperState;
            STATE.set(this, this.creeperState.getStateId());
            this.pushMetadataUpdates();
        }
        return this;
    }

    public CreeperRender setCharged(boolean isCharged)
    {
        if (this.charged != isCharged)
        {
            this.charged = isCharged;
            CHARGED.set(this,  this.charged ? (byte) 1 : (byte) 0);
            this.pushMetadataUpdates();
        }
        return this;
    }



    @Getter
    @AllArgsConstructor
    public enum CreeperState
    {
        IDLE((byte) -1),
        FUSED((byte) 1);

        private byte stateId;
    }
}
