package de.lordtkay.showcase.command;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

public class RuneCommandCollection extends AbstractCommandCollection {

    public RuneCommandCollection() {
        super("rune", "server.commands.rune");
        addSubCommand(new RuneGetCommand());
        addSubCommand(new RuneDetailsCommand());
    }
}
