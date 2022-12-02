package plus.dragons.createdragonlib.advancement;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import plus.dragons.createdragonlib.advancement.critereon.TriggerFactory;

public class AdvancementFactory {
    private final String modid;
    private final AdvancementGen advancementGen;
    private final TriggerFactory triggerFactory = new TriggerFactory();
    private final Runnable preTask;

    private AdvancementFactory(String name, String modid, Runnable preTask) {
        this.modid = modid;
        this.advancementGen = new AdvancementGen(name, modid);
        this.preTask = preTask;
    }

    @Deprecated(since = "1.1.1", forRemoval = true)
    public static AdvancementFactory create(String name, String modid) {
        return new AdvancementFactory(name, modid, () -> {
        });
    }

    public static AdvancementFactory create(String name, String modid, Runnable preTask) {
        return new AdvancementFactory(name, modid, preTask);
    }

    public AdvancementHolder.Builder builder(String id) {
        return new AdvancementHolder.Builder(modid, id, triggerFactory);
    }

    public TriggerFactory getTriggerFactory() {
        return triggerFactory;
    }

    public void datagen(final FabricDataGenerator datagen) {
        preTask.run();
        advancementGen.generator = datagen;
        datagen.addProvider(advancementGen);
    }

    public void register() {
        triggerFactory.register();
    }

}
