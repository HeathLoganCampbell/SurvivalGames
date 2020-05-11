package games.bevs.survivalgames.commons.entityengine.behavior.behaviors;

import games.bevs.survivalgames.commons.entityengine.behavior.EntityBehavior;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LookAtNearest extends EntityBehavior {

        protected Vector lookAt = new Vector();
        protected double lastDistanceCheck = 1000D;
        protected Location loc = new Location(null, 0, 0, 0);

        @Override
        public void tick() {
            lastDistanceCheck = 1000D;
            Bukkit.getOnlinePlayers().forEach(player -> {
                Vector location = player.getLocation().toVector();
                double tempDistance = location.distance(this.getAttached().getPosition());
                if (tempDistance < lastDistanceCheck && tempDistance < 25 && getAttached().getRender().canSee(player))
                {
                    lastDistanceCheck = tempDistance;
                    lookAt = location;
                }
            });
            Vector diff = lookAt.subtract(this.getAttached().getPosition()).normalize();
            loc.setDirection(diff);
            this.getAttached().setYaw(loc.getYaw());
            this.getAttached().setPitch(loc.getPitch());
        }
    }
    