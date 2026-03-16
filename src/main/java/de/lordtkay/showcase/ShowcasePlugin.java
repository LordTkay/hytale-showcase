package de.lordtkay.showcase;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import de.lordtkay.showcase.asset.Rune;
import de.lordtkay.showcase.asset.RuneEffect;
import de.lordtkay.showcase.command.RuneCommandCollection;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class ShowcasePlugin extends JavaPlugin {

    @Nonnull
    private static final HytaleLogger logger = HytaleLogger.forEnclosingClass();
    private static volatile ShowcasePlugin instance;

    public ShowcasePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Nonnull
    public static ShowcasePlugin getInstance() {
        ShowcasePlugin result = instance;
        if (result == null) {
            throw new IllegalStateException("%s has not been initialized yet!".formatted(ShowcasePlugin.class.getSimpleName()));
        }
        return result;
    }

    /**
     * This method is called at first and is responsible for registering all types of classes.
     */
    @Override
    protected void setup() {
        logger.atInfo().log("[{}] Setting up...", getName());

        registerAssetStores();
        getCommandRegistry().registerCommand(new RuneCommandCollection());

        logger.atInfo().log("[{}] Setup complete!", getName());
    }

    /**
     * This method is called when the plugin successfully loaded and is ready to use.
     */
    @Override
    protected void start() {
        logger.atInfo().log("[{}] Started!", getName());
    }

    /**
     * This method is called when the plugin is unloaded or the server is shutting down.
     */
    @Override
    protected void shutdown() {
        logger.atInfo().log("[{}] Shut down!", getName());
        instance = null;
    }

    private void registerAssetStores() {
        var runeAssetStore = HytaleAssetStore
                .builder(
                        String.class,
                        Rune.class,
                        new DefaultAssetMap<>()
                )
                .setCodec(Rune.CODEC)
                .setKeyFunction(Rune::getId)
                .setReplaceOnRemove(Rune::new)
                .setPath(Rune.ASSET_PATH)
                .build();
        getAssetRegistry().register(runeAssetStore);

        var runeEffectAssetStore = HytaleAssetStore
                .builder(
                        String.class,
                        RuneEffect.class,
                        new DefaultAssetMap<>()
                )
                .setCodec(RuneEffect.CODEC)
                .setKeyFunction(RuneEffect::getId)
                .setReplaceOnRemove(RuneEffect::new)
                .setPath(RuneEffect.ASSET_PATH)
                .loadsBefore(Rune.class)
                .build();
        getAssetRegistry().register(runeEffectAssetStore);
    }
}