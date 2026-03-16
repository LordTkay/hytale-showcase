package de.lordtkay.showcase;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

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

        // Registration of your assets, interactions, systems and more

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
}