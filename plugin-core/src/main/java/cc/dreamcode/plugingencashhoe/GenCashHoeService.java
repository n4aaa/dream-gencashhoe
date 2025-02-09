package cc.dreamcode.plugingencashhoe;

import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenCashHoeService {

    private final PluginConfig pluginConfig;

    public HoeItem buildDefaultHoe(int size) {
        ItemBuilder itemBuilder = ItemBuilder.of(pluginConfig.defaultHoeItemStack);
        itemBuilder.setName(pluginConfig.defaultHoeName.replace("{size}", String.valueOf(size)));
        itemBuilder.setLore(pluginConfig.defaultHoeLore.stream().map(line -> line.replace("{size}", String.valueOf(size))).collect(Collectors.toList()));

        return new HoeItem(itemBuilder.toItemStack(), size, pluginConfig.defaultHoeBreakables);
    }

    public HoeItem getHoeItem(int size) {
        return pluginConfig.hoes.stream().filter(hoeItem -> hoeItem.getSize() == size).findFirst().orElse(null);
    }
    public boolean containsHoeItem(int size) {
        return pluginConfig.hoes.stream().anyMatch(hoeItem -> hoeItem.getSize() == size);
    }
}
