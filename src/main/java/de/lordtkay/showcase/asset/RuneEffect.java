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
import com.hypixel.hytale.codec.validation.ValidatorCache;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemTranslationProperties;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RuneEffect implements JsonAssetWithMap<String, DefaultAssetMap<String, RuneEffect>> {

    public static final AssetBuilderCodec<String, RuneEffect> CODEC;
    private static AssetStore<String, RuneEffect, DefaultAssetMap<String, RuneEffect>> ASSET_STORE;
    public static final ValidatorCache<String> VALIDATOR_CACHE;
    public static final String ASSET_PATH = "Showcase/RuneEffect";

    static {
        //noinspection DataFlowIssue
        var builder = AssetBuilderCodec.builder(
                RuneEffect.class,
                RuneEffect::new,
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
                        new KeyedCodec<>("Power", Codec.FLOAT),
                        (asset, value) -> asset.power = value,
                        (asset) -> asset.power,
                        (asset, parent) -> asset.power = parent.power
                )
                .addValidator(Validators.nonNull())
                .addValidator(Validators.min(0f))
                .documentation("The power of this rune.")
                .add();

        //noinspection DataFlowIssue
        builder.appendInherited(
                        new KeyedCodec<>("DamageCause", Codec.STRING),
                        (asset, value) -> asset.damageCause = value,
                        (asset) -> asset.damageCause,
                        (asset, parent) -> asset.damageCause = parent.damageCause
                )
                .addValidator(Validators.nonNull())
                .addValidator(DamageCause.VALIDATOR_CACHE.getValidator())
                .documentation("The power of this rune.")
                .add();

        CODEC = builder.build();
        VALIDATOR_CACHE = new ValidatorCache<>(new AssetKeyValidator<>(RuneEffect::getAssetStore));
    }

    @Nonnull
    private String id = "";
    private AssetExtraInfo.Data data = null;
    private ItemTranslationProperties translationProperties = null;
    private float power = 0.0f;
    @Nonnull
    private String damageCause = "";

    public RuneEffect() {
    }

    public RuneEffect(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public static AssetStore<String, RuneEffect, DefaultAssetMap<String, RuneEffect>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(RuneEffect.class);
            Objects.requireNonNull(ASSET_STORE);
        }

        return ASSET_STORE;
    }

    @Nonnull
    public static DefaultAssetMap<String, RuneEffect> getAssetMap() {
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

    public float getPower() {
        return power;
    }

    @Nonnull
    public String getDamageCause() {
        return damageCause;
    }
}
