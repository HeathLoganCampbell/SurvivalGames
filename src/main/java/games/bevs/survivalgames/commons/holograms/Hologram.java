package games.bevs.survivalgames.commons.holograms;

import com.google.gson.annotations.Expose;
import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.entityengine.renders.objects.misc.ArmorStandRender;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Hologram
{
    private @Expose @NonNull  String id;
    private @Expose @NonNull List<String> text;
    private @Expose @NonNull Location location;
    private @Ignore List<ArmorStandRender> armorStandRenders;

    public World getWorld()
    {
        return this.getLocation().getWorld();
    }

    public void refresh()
    {
        this.destroy();
        this.spawn(this.getWorld().getPlayers());
    }

    public void destroy()
    {
        if(this.armorStandRenders != null)
        {
            for (ArmorStandRender armorStandRender : this.armorStandRenders)
                armorStandRender.removeFor(this.getWorld().getPlayers());
            this.armorStandRenders.clear();
            this.armorStandRenders = null;
        }
    }

    public void spawn(List<Player> players)
    {
        if(this.armorStandRenders == null)
        {
            this.armorStandRenders = new ArrayList<>();

            if(this.text.size() == 0)
            {
                ArmorStandRender hologramLine = new ArmorStandRender();

                hologramLine.setInvisible(true);
                hologramLine.setIsMarker(true);
                hologramLine.setCustomName("/holo message " + id + " append <Message>");
                hologramLine.setCustomNameVisible(true);

                this.armorStandRenders.add(hologramLine);
            }

            for (int i = this.getText().size() - 1; i >= 0; i--)
            {
                ArmorStandRender hologramLine = new ArmorStandRender();

                hologramLine.setInvisible(true);
                hologramLine.setIsMarker(true);
                hologramLine.setCustomName(this.getText().get(i));
                hologramLine.setCustomNameVisible(true);

                this.armorStandRenders.add(hologramLine);
            }
        }

        Location loc = this.getLocation().clone();
        for (ArmorStandRender armorStandRender : this.armorStandRenders)
            SurvivalGames.get().getEntityEngine().spawn(players, armorStandRender, loc.add(0, 0.25, 0));
    }
}
