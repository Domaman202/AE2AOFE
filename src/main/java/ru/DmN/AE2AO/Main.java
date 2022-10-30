package ru.DmN.AE2AO;

import com.moandjiezana.toml.Toml;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;

@Mod("ae2ao")
public class Main {
    // Logger
    static final Logger LOGGER = LogManager.getLogger();
    // Config
    public static Config config;

    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void serverStartingEvent(FMLServerStartingEvent event) {
        // Config init
        try {
            File conf = FMLPaths.GAMEDIR.get().resolve("config" + File.separator + "ae2ao.toml").toFile();

            if (conf.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(conf);
                stream.write("""
                        # Отключение каналов
                        # Disable channels
                        DisableChannels = false
                        # Лимиты количества контроллеров в сети
                        # Limits on the number of controllers in the network
                        ControllerLimits = true
                        # Размеры контроллера
                        # Controller size
                        Max_X = 7
                        Max_Y = 7
                        Max_Z = 7
                        # Антисгорание ячеек
                        # Cell anti-burning
                        CellFireResistance = false
                        PortableCellFireResistance = false
                        """.getBytes());
                stream.flush();
                stream.close();
            }
            config = new Toml().read(conf).to(Config.class);
        } catch (Exception e) {
            System.out.println("[DMN]");
            LOGGER.throwing(e);
        }
    }
}