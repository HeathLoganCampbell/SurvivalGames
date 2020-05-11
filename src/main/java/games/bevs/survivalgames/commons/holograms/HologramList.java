package games.bevs.survivalgames.commons.holograms;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@ToString
public class HologramList
{
    @Getter
    private List<Hologram> holograms = new LinkedList<>();
}
