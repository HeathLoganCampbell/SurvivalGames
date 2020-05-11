package games.bevs.survivalgames.commons.entityengine.protocol;

import games.bevs.survivalgames.commons.protocol.PacketListener;
import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public class ChunkSendProtocolListener extends PacketListener
{

    @Override
    public Object onPacketOutAsync(Player receiver, Channel channel, Object packet)
    {
//        System.out.println(packet.getClass().getSimpleName());
        return receiver;
    }

    @Override
    public Object onPacketInAsync(Player sender, Channel channel, Object packet)
    {
        return null;
    }
}
