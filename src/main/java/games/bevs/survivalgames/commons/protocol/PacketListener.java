package games.bevs.survivalgames.commons.protocol;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public abstract class PacketListener
{
    /**
     * Sennd via the server
     * @param receiver
     * @param channel
     * @param packet
     * @return
     */
    public abstract Object onPacketOutAsync(Player receiver, Channel channel, Object packet);

    /**
     * Recieved from the client
     * @param sender
     * @param channel
     * @param packet
     * @return
     */
    public abstract Object onPacketInAsync(Player sender, Channel channel, Object packet);
}
