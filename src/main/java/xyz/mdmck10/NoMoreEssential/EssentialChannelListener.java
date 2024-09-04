package xyz.mdmck10.NoMoreEssential;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import net.kyori.adventure.text.Component;

import java.nio.charset.StandardCharsets;

public class EssentialChannelListener implements PacketListener {
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if(event.getPacketType() != PacketType.Play.Client.PLUGIN_MESSAGE) return;

        WrapperPlayClientPluginMessage pluginMessage = new WrapperPlayClientPluginMessage(event);

        String channel = pluginMessage.getChannelName();

        if(channel.equalsIgnoreCase("minecraft:register") || channel.equalsIgnoreCase("REGISTER")) {
            byte[] payload = pluginMessage.getData();

            String subChannel = new String(payload, StandardCharsets.ISO_8859_1);

            if (subChannel.equalsIgnoreCase("essential:")) {
                User user = event.getUser();
                ((NoMoreEssentialPlugin)PacketEvents.getAPI().getPlugin()).getLogger().info(String.format("Kicking Essential user %s", user.getName()));
                WrapperPlayServerDisconnect kickPacket = new WrapperPlayServerDisconnect(Component.text("This server doesn't allow players with Essential installed to join.\nPlease uninstall the Essential mod to play on this server."));
                user.sendPacket(kickPacket);
            }
        }
    }
}
