package surajkumar.discord.commands.slash.music;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class SlashCommandOption {
    private final String name;
    private final String description;
    private final OptionType type;

    public SlashCommandOption(String name, String description, OptionType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public OptionType getType() {
        return type;
    }
}
