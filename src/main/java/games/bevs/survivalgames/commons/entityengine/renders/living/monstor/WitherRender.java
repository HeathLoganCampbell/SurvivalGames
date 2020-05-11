package games.bevs.survivalgames.commons.entityengine.renders.living.monstor;

import games.bevs.survivalgames.commons.entityengine.metadata.NMSMetadata;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class WitherRender extends MonsterRender
{
    private static final NMSMetadata LEFT_HEAD_TARGET = new NMSMetadata(17, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata CENTER_HEAD_TARGET = new NMSMetadata(18, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata RIGHT_HEAD_TARGET = new NMSMetadata(19, NMSMetadata.NMSMetadataType.INT);
    private static final NMSMetadata INVULNERABLE_TIME = new NMSMetadata(20, NMSMetadata.NMSMetadataType.INT);

    private int leftHeadWatchedTarget, centerHeadWatchedTarget, rightHeadWatchedTarget, invulnerableTime;

    public WitherRender()
    {
        super(EntityType.WITHER);

        LEFT_HEAD_TARGET.init(this, leftHeadWatchedTarget);
        CENTER_HEAD_TARGET.init(this, centerHeadWatchedTarget);
        RIGHT_HEAD_TARGET.init(this, rightHeadWatchedTarget);
        INVULNERABLE_TIME.init(this, invulnerableTime);
    }

    /**
     * Set his size
     * @param invulnerableTime
     * @return
     */
    public WitherRender setInvulnerableTime(int invulnerableTime) {
        if (this.invulnerableTime != invulnerableTime)
        {
            this.invulnerableTime = invulnerableTime;
            INVULNERABLE_TIME.set(this, invulnerableTime);
            this.pushMetadataUpdates();
        }
        return this;
    }

    public WitherRender lookAt(Entity entity, WitherHead head) {
        return this.lookAt(entity.getEntityId(), head);
    }
    public WitherRender lookAt(int entity, WitherHead head) {
        if (head == null)
            return this;
        switch (head) {
            case LEFT:
                if (leftHeadWatchedTarget != entity) {
                    leftHeadWatchedTarget = entity;
                    LEFT_HEAD_TARGET.set(this, leftHeadWatchedTarget);
                    this.pushMetadataUpdates();
                }
                break;
            case CENTER:
                if (centerHeadWatchedTarget != entity) {
                    centerHeadWatchedTarget = entity;
                    CENTER_HEAD_TARGET.set(this, centerHeadWatchedTarget);
                    this.pushMetadataUpdates();
                }
                break;
            case RIGHT:
                if (rightHeadWatchedTarget != entity) {
                    rightHeadWatchedTarget = entity;
                    RIGHT_HEAD_TARGET.set(this, rightHeadWatchedTarget);
                    this.pushMetadataUpdates();
                }
                break;
        }
        return this;
    }

    public int getWatchedID(WitherHead head)
    {
        switch (head) {
            case LEFT:
                return leftHeadWatchedTarget;
            case CENTER:
                return centerHeadWatchedTarget;
            case RIGHT:
                return rightHeadWatchedTarget;
        }
        return 0;
    }

    public static enum WitherHead
    {
        LEFT, CENTER, RIGHT;
    }
}
