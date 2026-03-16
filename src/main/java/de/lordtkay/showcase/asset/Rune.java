package de.lordtkay.showcase.asset;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.assetstore.AssetKeyValidator;
import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.codec.validation.ValidatorCache;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemTranslationProperties;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Rune implements JsonAssetWithMap<String, DefaultAssetMap<String, Rune>> {

    public static final AssetBuilderCodec<String, Rune> CODEC;
    private static AssetStore<String, Rune, DefaultAssetMap<String, Rune>> ASSET_STORE;
    public static final ValidatorCache<String> VALIDATOR_CACHE;
    public static final String ASSET_PATH = "Showcase/Rune";

    static {
        //noinspection DataFlowIssue
        var builder = AssetBuilderCodec.builder(
                Rune.class,
                Rune::new,
                Codec.STRING,
                (asset, id) -> asset.id = id,
                (asset) -> asset.id,
                (asset, data) -> asset.data = data,
                (asset) -> asset.data
        );

        //noinspection DataFlowIssue
        builder.appendInherited(
                        new KeyedCodec<>("TranslationProperties", ItemTranslationProperties.CODEC),
                        (asset, value) -> asset.translationProperties = value,
                        (asset) -> asset.translationProperties,
                        (asset, parent) -> asset.translationProperties = parent.translationProperties
                )
                .documentation("The translation properties for this rune asset.")
                .add();

        //noinspection DataFlowIssue
        builder.appendInherited(
                        new KeyedCodec<>("Type", Codec.STRING),
                        (asset, value) -> asset.type = value,
                        (asset) -> asset.type,
                        (asset, parent) -> asset.type = parent.type
                )
                .documentation("The type of this rune.")
                .add();

        //noinspection DataFlowIssue
        builder.appendInherited(
                        new KeyedCodec<>("Effects", new ArrayCodec<>(RuneEffect.CODEC, RuneEffect[]::new)),
                        (asset, value) -> asset.effects = value,
                        (asset) -> asset.effects,
                        (asset, parent) -> asset.effects = parent.effects
                )
                .documentation("The effects that this rune contain.")
                .add();

        CODEC = builder.build();
        VALIDATOR_CACHE = new ValidatorCache<>(new AssetKeyValidator<>(Rune::getAssetStore));
    }

    @Nonnull
    private String id = "";
    private AssetExtraInfo.Data data = null;
    private ItemTranslationProperties translationProperties = null;
    private String type = "Normal";
    @Nonnull
    private RuneEffect[] effects = new RuneEffect[0];

    public Rune() {
    }

    public Rune(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public static AssetStore<String, Rune, DefaultAssetMap<String, Rune>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(Rune.class);
            Objects.requireNonNull(ASSET_STORE);
        }

        return ASSET_STORE;
    }

    @Nonnull
    public static DefaultAssetMap<String, Rune> getAssetMap() {
        var assetMap = getAssetStore().getAssetMap();
        return Objects.requireNonNull(assetMap);
    }

    @Override
    @Nonnull
    public String getId() {
        return id;
    }

    public AssetExtraInfo.Data getData() {
        return data;
    }

    public ItemTranslationProperties getTranslationProperties() {
        return translationProperties;
    }

    public String getType() {
        return type;
    }

    @Nonnull
    public RuneEffect[] getEffects() {
        return effects;
    }
}
