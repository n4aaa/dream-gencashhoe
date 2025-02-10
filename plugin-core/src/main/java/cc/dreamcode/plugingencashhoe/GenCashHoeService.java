package cc.dreamcode.plugingencashhoe;

import cc.dreamcode.plugingencashhoe.config.MessageConfig;
import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.utilities.object.Duo;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenCashHoeService {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    private final Map<UUID, HoeCreatorItem> nameEditors = new HashMap<>();
    private final Map<UUID, Duo<HoeCreatorItem, Integer>> loreEditors = new HashMap<>();

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

    public boolean isNameEditor(@NonNull UUID uuid) {
        return this.nameEditors.containsKey(uuid);
    }
    public HoeCreatorItem getNameEditor(@NonNull UUID uuid) {
        return this.nameEditors.get(uuid);
    }
    public void addToNameEditors(@NonNull UUID uuid, @NonNull HoeCreatorItem hoeCreatorItem) {
        this.nameEditors.put(uuid, hoeCreatorItem);
    }
    public void removeFromNameEditors(@NonNull UUID uuid) {
        this.nameEditors.remove(uuid);
    }

    public boolean isLoreEditor(@NonNull UUID uuid) {
        return this.loreEditors.containsKey(uuid);
    }
    public Duo<HoeCreatorItem, Integer> getLoreEditor(@NonNull UUID uuid) {
        return this.loreEditors.get(uuid);
    }
    public void addToLoreEditors(@NonNull UUID uuid, @NonNull Integer index, @NonNull HoeCreatorItem hoeCreatorItem) {
        this.loreEditors.put(uuid, Duo.of(hoeCreatorItem, index));
    }
    public void removeFromLoreEditors(@NonNull UUID uuid) {
        this.loreEditors.remove(uuid);
    }

    public HoeItem getHoeItem(int size) {
        return pluginConfig.hoes.stream().filter(hoeItem -> hoeItem.getSize() == size).findFirst().orElse(null);
    }
}
