package cc.dreamcode.plugingencashhoe;

import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenCashHoeService {

    private final PluginConfig pluginConfig;

    public HoeItem buildDefaultHoe(int size) {
        ItemBuilder itemBuilder = ItemBuilder.of(pluginConfig.defaultHoeItemStack);
        itemBuilder.fixColors(new MapBuilder<String, Object>()
                .put("size", String.valueOf(size))
                .build());

        return new HoeItem(pluginConfig.hoes.size() + 1, itemBuilder.toItemStack(), size, pluginConfig.defaultHoeBreakables);
    }
    public HoeCreatorItem buildDefaultCreatorHoe(int size) {
        ItemBuilder itemBuilder = ItemBuilder.of(pluginConfig.defaultHoeItemStack);
        itemBuilder.fixColors(new MapBuilder<String, Object>()
                .put("size", String.valueOf(size))
                .build());

        return new HoeCreatorItem(pluginConfig.hoes.size() + 1, itemBuilder.toItemStack(), size, pluginConfig.defaultHoeBreakables);
    }

    public HoeItem getHoeItem(int size) {
        return pluginConfig.hoes.stream().filter(hoeItem -> hoeItem.getSize() == size).findFirst().orElse(null);
    }
//    public boolean containsHoeItem(int size) {
//        return pluginConfig.hoes.stream().anyMatch(hoeItem -> hoeItem.getSize() == size);
//    }
}
