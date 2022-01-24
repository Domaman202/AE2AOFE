package ru.DmN.AE2AO;

import com.moandjiezana.toml.Toml;
import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Mod("ae2ao")
public class Main {
    // Logger
    static final Logger LOGGER = LogManager.getLogger();
    // Config
    public static Config lcc = null;
    public static Config lc = null;

    public Main() {
        //
        MinecraftForge.EVENT_BUS.register(this);

        //
        Networking.registerMessages();

        // Config init
        try {
            File conf = FMLPaths.GAMEDIR.get().resolve("config" + File.separator + "ae2ao.toml").toFile();

            if (conf.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(conf);
                stream.write("DisableChannels = false\nControllerLimits = false\nMax_X = 7\nMax_Y = 7\nMax_Z = 7\nSCFD = false\nChatInfo = true".getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();

                lcc = new Config();
                lc = new Config();
            } else {
                lcc = new Toml().read(conf).to(Config.class);
                lc = lcc.clone();
            }
        } catch (Exception e) {
            LOGGER.throwing(e);
        }
    }

    @SubscribeEvent
    public void serverStartingEvent(ServerStartedEvent event) {
        event.getServer().getCommands().getDispatcher().register(
                Commands.literal("loadConfig").executes(context -> {
                    Networking.INSTANCE.sendTo(Main.lcc, context.getSource().getPlayerOrException().connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
}