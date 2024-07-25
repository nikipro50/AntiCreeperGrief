package dev.nikipro50.antiCreeperGrief.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import dev.nikipro50.antiCreeperGrief.enums.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class TabCompleters implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("antigriefcreeper.admin"))
            return Lists.newArrayList();
        ImmutableList.Builder<String> suggestions = ImmutableList.builder();
        switch (args.length) {
            case 1:
                if ("on".startsWith(args[0].toLowerCase(Locale.ROOT)))
                    suggestions.add("on");
                if ("off".startsWith(args[0].toLowerCase(Locale.ROOT)))
                    suggestions.add("off");
        }
        return suggestions.build();
    }
}
