package games.bevs.survivalgames.commons.entityengine.behavior;

import games.bevs.survivalgames.commons.entityengine.EntityPuppeteer;

public abstract class EntityBehavior {

    public EntityPuppeteer attached;
    boolean remove;

    public void onAttach() {}
    public void onDetach() {}
    public void tick() {}
    boolean tick0()
    {
        tick();
        return remove;
    }
    
    public EntityPuppeteer getAttached() {
        return this.attached;
    }
}