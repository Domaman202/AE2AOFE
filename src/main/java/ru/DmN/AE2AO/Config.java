package ru.DmN.AE2AO;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class Config implements Cloneable {
    public Config() { }
    //
    public boolean DisableChannels = false;
    public boolean ControllerLimits = false;
    //
    public int Max_X = 7;
    public int Max_Y = 7;
    public int Max_Z = 7;
    // Storage Cell Fire Damage
    public boolean SCFD = false;
    //
    public Config clone() {
        Config c = new Config();
        c.DisableChannels = DisableChannels;
        c.ControllerLimits = ControllerLimits;
        c.SCFD = SCFD;
        c.Max_X = Max_X;
        c.Max_Y = Max_Y;
        c.Max_Z = Max_Z;
        return c;
    }
    // Networking
    public Config(PacketBuffer buf) {
        DisableChannels = buf.readBoolean();
        ControllerLimits = buf.readBoolean();
        SCFD = buf.readBoolean();
        Max_X = buf.readInt();
        Max_Y = buf.readInt();
        Max_Z = buf.readInt();

        Main.lc = this;

        Config config = Main.lc;
        Minecraft.getInstance().player.sendMessage(new StringTextComponent(
                "AE2AO config loaded!\nControllerLimits = " + config.ControllerLimits +
                        "\nDisableChannels = " + config.DisableChannels +
                        "\nSCFD = " + config.SCFD +
                        "\nMax_X = " + config.Max_X +
                        "\nMax_Y = " + config.Max_Y +
                        "\nMax_Z = " + config.Max_Z
        ), new UUID(0, 0));
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(DisableChannels);
        buf.writeBoolean(ControllerLimits);
        buf.writeBoolean(SCFD);
        buf.writeInt(Max_X);
        buf.writeInt(Max_Y);
        buf.writeInt(Max_Z);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) { }
}