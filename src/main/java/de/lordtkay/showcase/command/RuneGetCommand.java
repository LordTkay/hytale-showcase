package de.lordtkay.showcase.command;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.util.MessageUtil;
import de.lordtkay.showcase.asset.Rune;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

public class RuneGetCommand extends AbstractCommand {


    public RuneGetCommand() {
        super("get", "server.commands.rune.get");
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext context) {
        StringJoiner builder = new StringJoiner("\n");

        builder.add("Available runes:");

        Collection<Rune> allRunesList = Rune.getAssetMap().getAssetMap().values();

        for (Rune rune : allRunesList) {
            if (rune == null) continue;

            var name = "- %s".formatted(rune.getId());
            var translationProperties = rune.getTranslationProperties();
            if (translationProperties != null) {
                var nameMessage = Message.translation(Objects.requireNonNull(translationProperties.getName()));
                var nameTranslated = MessageUtil.toAnsiString(nameMessage);
                name = "- %s (%s)".formatted(nameTranslated, rune.getId());
            }

            builder.add(name);
        }

        String messageText = Objects.requireNonNull(builder.toString());
        context.sendMessage(Message.raw(messageText));

        return CompletableFuture.completedFuture(null);
    }
}
