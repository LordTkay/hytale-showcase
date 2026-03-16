# Hytale Showcase

This repository is a collection of Hytale examples, showcasing on how to implement specific features to create a Hytale 
plugin.

## Custom Asset Type

Hytale has a lot of built-in assets like Items, Loot Tables, Effects, Stats and more. However, sometimes you might want
to
create your own asset type that contains information that can't be represented by the built-in assets or because they
have a different
context than the built-in asset type.

After the asset type is registered, you or even users of your plugin will be able to create assets of that type as you
do with the built-in assets.

## Showcase

### Asset Types

This showcase has two asset types `Rune` and `RuneEffect`. The rune represents some kind of magical object that has a
name, description, type and effects. The effects have a name, description, power and damage type.

### Commands

To check if the custom asset types are working and also demonstrate how to fetch your assets, I created two commands.

1. `/rune get` - Get a list of all runes by their translated name and ID.
2. `/rune details <id>` - Gets the details of a specific rune


