package games.bevs.survivalgames.commons.protocol;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProtocolManager extends TinyProtocol
{
    private Map<Class<?>, List<PacketListener>> packetListeners = Maps.newHashMap();
    /**
     * Construct a new instance of TinyProtocol, and start intercepting packets for all connected clients and future clients.
     * <p>
     * You can construct multiple instances per plugin.
     *
     * @param plugin - the plugin.
     */
    public ProtocolManager(Plugin plugin) {
        super(plugin);
    }

    protected Object onPacketOutAsync(Player receiver, Channel channel, Object packet)
    {

        List<PacketListener> packetListenersOfType = packetListeners.get(packet.getClass());
        if(packetListenersOfType == null)
            return packet;


        for (PacketListener packetListener : packetListenersOfType)
        {
            packet = packetListener.onPacketOutAsync(receiver, channel, packet);
        }
        return packet;
    }

    protected Object onPacketInAsync(Player sender, Channel channel, Object packet)
    {
        List<PacketListener> packetListenersOfType = packetListeners.get(packet.getClass());
        if(packetListenersOfType == null)
            return packet;


        for (PacketListener packetListener : packetListenersOfType)
        {
            packet = packetListener.onPacketInAsync(sender, channel, packet);
        }
        return packet;
    }

    public void registerPacketListener(Class<?> packetClass, PacketListener listener)
    {
        List<PacketListener> listeners = this.packetListeners.get(packetClass);
        if(listeners == null) listeners = new ArrayList<>(2);

        listeners.add(listener);

        this.packetListeners.put(packetClass, listeners);
    }

    public void registerPacketListener(Class<?>[] packetClasses, PacketListener listener)
    {
        for (Class<?> packetClass : packetClasses)
            this.registerPacketListener(packetClass, listener);
    }
}