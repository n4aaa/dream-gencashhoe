package cc.dreamcode.gencashhoe;

import cc.dreamcode.gencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.utilities.object.Duo;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenCashHoeService {

    private final PluginConfig pluginConfig;

    private final Map<UUID, HoeCreatorItem> nameEditors = new HashMap<>();
    private final Map<UUID, Duo<HoeCreatorItem, Integer>> loreEditors = new HashMap<>();
    private final Map<UUID, Duo<HoeCreatorItem, Enchantment>> enchantEditors = new HashMap<>();

    @Getter private final List<ItemStack> materialPresenters = new ArrayList<>();
    @Getter private final Map<ItemFlag, ItemStack> flagActivePresenters = new HashMap<>();
    @Getter private final Map<ItemFlag, ItemStack> flagNoActivePresenters = new HashMap<>();
    @Getter private final Map<Enchantment, ItemStack> enchantPresenters = new HashMap<>();

    @PostConstruct
    public void loadPresenters() {
        this.materialPresenters.clear();
        this.enchantPresenters.clear();
        this.flagActivePresenters.clear();
        this.flagNoActivePresenters.clear();

        this.pluginConfig.materials.forEach(material -> this.materialPresenters.add(ItemBuilder.of(this.pluginConfig.iconBreakablesMenuTemplate.clone()).fixColors(new MapBuilder<String, Object>().put("name", material.name().toLowerCase()).build()).setType(material).toItemStack()));
        Arrays.stream(Enchantment.values()).collect(Collectors.toList()).forEach(enchantment -> this.enchantPresenters.put(enchantment, ItemBuilder.of(this.pluginConfig.iconEnchantMenuTemplate.clone()).fixColors(new MapBuilder<String, Object>().put("name", enchantment.getName().toLowerCase()).build()).toItemStack()));

        Arrays.stream(ItemFlag.values()).collect(Collectors.toList()).forEach(flag -> this.flagActivePresenters.put(flag, ItemBuilder.of(this.pluginConfig.iconFlagActiveMenuTemplate.clone()).fixColors(new MapBuilder<String, Object>().put("name", flag.name().toLowerCase()).build()).toItemStack()));
        Arrays.stream(ItemFlag.values()).collect(Collectors.toList()).forEach(flag -> this.flagNoActivePresenters.put(flag, ItemBuilder.of(this.pluginConfig.iconFlagNoActiveMenuTemplate.clone()).fixColors(new MapBuilder<String, Object>().put("name", flag.name().toLowerCase()).build()).toItemStack()));
    }

    public HoeItem buildDefaultHoe(int size) {
        ItemBuilder itemBuilder = ItemBuilder.of(pluginConfig.templateHoeItemStack);
        itemBuilder.fixColors(new MapBuilder<String, Object>()
                .put("size", String.valueOf(size))
                .build());

        return new HoeItem(pluginConfig.hoes.size() + 1, itemBuilder.toItemStack(), true, size, new ArrayList<>());
    }
    public HoeCreatorItem buildDefaultCreatorHoe(int size) {
        ItemBuilder itemBuilder = ItemBuilder.of(pluginConfig.templateHoeItemStack);
        itemBuilder.fixColors(new MapBuilder<String, Object>()
                .put("size", String.valueOf(size))
                .build());

        return new HoeCreatorItem(pluginConfig.hoes.size() + 1, itemBuilder.toItemStack(), size, new ArrayList<>());
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

    public boolean isEnchantEditor(@NonNull UUID uuid) {
        return this.enchantEditors.containsKey(uuid);
    }
    public Duo<HoeCreatorItem, Enchantment> getEnchantEditor(@NonNull UUID uuid) {
        return this.enchantEditors.get(uuid);
    }
    public void addToEnchantEditors(@NonNull UUID uuid, @NonNull Enchantment enchantment, @NonNull HoeCreatorItem hoeCreatorItem) {
        this.enchantEditors.put(uuid, Duo.of(hoeCreatorItem, enchantment));
    }
    public void removeFromEnchantEditors(@NonNull UUID uuid) {
        this.enchantEditors.remove(uuid);
    }
    public HoeItem getHoe(int id) {
        return pluginConfig.hoes.stream().filter(hoeItem -> hoeItem.getId() == id).findFirst().orElse(null);
    }
    public List<HoeItem> getHoeList(int size) {
        return pluginConfig.hoes.stream().filter(hoeItem -> hoeItem.getSize() == size).collect(Collectors.toList());
    }
}
