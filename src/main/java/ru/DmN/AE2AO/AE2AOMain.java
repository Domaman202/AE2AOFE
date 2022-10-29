package ru.DmN.AE2AO;

import com.moandjiezana.toml.Toml;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Mod("ae2ao")
public class AE2AOMain {
    // Logger
    static final Logger LOGGER = LogManager.getLogger();
    // Config
    public static Config config = null;

    public AE2AOMain() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void serverStartingEvent(ServerStartingEvent event) {
        // Config init
        try {
            File conf = FMLPaths.GAMEDIR.get().resolve("config" + File.separator + "ae2ao.toml").toFile();

            if (conf.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(conf);
                stream.write("DisableChannels = false\nControllerLimits = false\nMax_X = 7\nMax_Y = 7\nMax_Z = 7\nSCFD = false".getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();

                config = new Config();
            } else {
                config = new Toml().read(conf).to(Config.class);
            }
        } catch (Exception e) {
            LOGGER.throwing(e);
        }
    }
}