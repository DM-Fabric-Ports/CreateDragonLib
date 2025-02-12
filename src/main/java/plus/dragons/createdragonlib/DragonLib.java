package plus.dragons.createdragonlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.dragons.createdragonlib.tag.TagGen;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;

public class DragonLib implements ModInitializer, DataGeneratorEntrypoint {
    public static final String ID = "create_dragon_lib";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Create: Dragon Lib " +
                FabricLoader.getInstance().getModContainer(ID).get().getMetadata().getName() +
                " has initialized, ready to support your Create add-ons!");

    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        TagGen.genAll();
    }

}
