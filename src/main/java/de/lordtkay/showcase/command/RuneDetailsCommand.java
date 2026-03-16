package de.lordtkay.showcase.command;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemTranslationProperties;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.util.MessageUtil;
import de.lordtkay.showcase.asset.Rune;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

public class RuneDetailsCommand extends AbstractCommand {
    @Nonnull
    private final RequiredArg<String> runeId;

    public RuneDetailsCommand() {
        super("details", "server.commands.rune.details");
        this.runeId = withRequiredArg("runeId", "server.commands.rune.details.runeId", ArgTypes.STRING);
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext context) {
        String runeId = Objects.requireNonNull(this.runeId.get(context));

        var rune = Rune.getAssetMap().getAsset(runeId);
        if (rune == null) {
            Message message = Message.translation("server.commands.rune.details.notFound")
                    .param("runeId", runeId);
            context.sendMessage(message);
            return CompletableFuture.completedFuture(null);
        }

        StringJoiner builder = new StringJoiner("\n");

        var runeName = getName(runeId, rune.getTranslationProperties());
        builder.add("%s:".formatted(runeName));
        builder.add("Type: %s".formatted(rune.getType()));
        builder.add("Effects:");

        for (var effect : rune.getEffects()) {
            if (effect == null) continue;

            var runeEffectName = getName(effect.getId(), effect.getTranslationProperties());
            builder.add("- Name:   %s".formatted(runeEffectName));
            builder.add("  Power:  %s".formatted(effect.getPower()));
            builder.add("  Damage: %s".formatted(effect.getDamageCause()));
        }

        String messageText = Objects.requireNonNull(builder.toString());
        context.sendMessage(Message.raw(messageText));

        return CompletableFuture.completedFuture(null);
    }

    private String getName(String id, ItemTranslationProperties translationProperties) {
        if (translationProperties == null) return id;
        var nameMessage = Message.translation(Objects.requireNonNull(translationProperties.getName()));
        return MessageUtil.toAnsiString(nameMessage).toString();
    }
}
