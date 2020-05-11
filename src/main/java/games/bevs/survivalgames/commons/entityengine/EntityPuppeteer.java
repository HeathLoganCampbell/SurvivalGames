package games.bevs.survivalgames.commons.entityengine;

import games.bevs.survivalgames.commons.entityengine.behavior.EntityBehavior;
import games.bevs.survivalgames.commons.entityengine.renders.EntityRender;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class EntityPuppeteer
{
    @Getter
    private EntityRender render;
    @Getter
    private final Map<Class<? extends EntityBehavior>, EntityBehavior> behaviors = new HashMap<>();

    @Getter @Setter
    private Vector position;
    @Getter @Setter
    private float yaw, pitch;
    @Getter @Setter
    private World world;

    public EntityPuppeteer()
    {
    }

    public EntityPuppeteer addBehavior(EntityBehavior behavior) {
        EntityBehavior previous = this.behaviors.put(behavior.getClass(), behavior);
        if (previous != null)
            previous.onDetach();

        behavior.attached = this;

        try {
            behavior.onAttach();
        } catch (Exception e) {
            e.printStackTrace();
//            this.remove();
        }
        return this;
    }

    public <T extends EntityBehavior> T getBehavior(Class<T> behaviorClass) {
        return (T) this.behaviors.get(behaviorClass);
    }

    public void setRender(EntityRender render)
    {
        render.entityPuppeteer = this;
        this.render = render;
    }

    public void tick()
    {
        if (this.render != null) {
            try {
                for (EntityBehavior value : this.behaviors.values()) {
                    value.tick();
                }
            } catch (Exception e) {
                e.printStackTrace();
//                this.remove();
            }

            this.render.tick();
        }

    }

}
