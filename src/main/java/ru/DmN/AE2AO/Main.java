package ru.DmN.AE2AO;

import com.moandjiezana.toml.Toml;
import com.mojang.brigadier.Command;
import net.minecraft.command.Commands;
import net.minecraft.command.impl.GiveCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.DmN.AE2AO.mixin.GridNodeMixin;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import static net.minecraft.command.Commands.literal;

@Mod("ae2ao")
public class Main {
    // Logger
    static final Logger LOGGER = LogManager.getLogger();
    // Config
    public static Config lcc = null;
    public static Config lc = null;

    public Main() throws Exception {
        //
        MinecraftForge.EVENT_BUS.register(this);

        //
        Networking.registerMessages();

        // Config init
        try {
            File conf = FMLPaths.GAMEDIR.get().resolve("config" + File.separator + "ae2ao.toml").toFile();

            if (conf.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(conf);
                stream.write("DisableChannels = false\nControllerLimits = false\nMax_X = 7\nMax_Y = 7\nMax_Z = 7\nSCFD = false".getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();

                lcc = new Config();
                lc = new Config();
            } else {
                lcc = new Toml().read(conf).to(Config.class);
                lc = lcc.clone();
            }

            System.out.println();
        } catch (Exception e) {
            LOGGER.throwing(e);
        }
    }

    @SubscribeEvent
    public void serverStartingEvent(FMLServerStartingEvent event) {
        event.getServer().getCommandManager().getDispatcher().register(
                Commands.literal("loadConfig").executes(context -> {
                    Networking.INSTANCE.sendTo(Main.lcc, context.getSource().asPlayer().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
}