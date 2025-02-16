package cc.dreamcode.gencashhoe.menu;

import cc.dreamcode.gencashhoe.GenCashHoePlugin;
import cc.dreamcode.gencashhoe.GenCashHoeService;
import cc.dreamcode.gencashhoe.HoeCreatorItem;
import cc.dreamcode.gencashhoe.config.MessageConfig;
import cc.dreamcode.gencashhoe.config.PluginConfig;
import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.base.BukkitMenuPaginated;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPaginatedPlayerSetup;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCreatorFlagMenu implements BukkitMenuPaginatedPlayerSetup {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @Setter private HoeCreatorItem hoeItem;

    @Override
    public BukkitMenuPaginated build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.hoeCreatorFlagMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildEmpty();

        menuBuilder.getItems().forEach((slot, item) -> {
            if (slot == this.pluginConfig.iconFlagMenuCloseSlot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.tasker.newChain()
                                .supplyAsync(() -> {
                                    HoeCreatorMenu hoeCreatorMenu = this.genCashHoePlugin.createInstance(HoeCreatorMenu.class);
                                    hoeCreatorMenu.setHoeItem(this.hoeItem);

                                    return hoeCreatorMenu.build(player);
                                })
                                .acceptSync(newMenu -> newMenu.open(player))
                                .execute();
                    }
                });

                return;
            }

            bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack());
        });

        final BukkitMenuPaginated bukkitMenuPaginated = bukkitMenu.toPaginated();

        if (this.pluginConfig.iconFlagMenuPreviousPageSlot != -1) {
            bukkitMenuPaginated.setPreviousPageSlot(this.pluginConfig.iconFlagMenuPreviousPageSlot, doer -> this.messageConfig.firstPageReach.send(doer));
        }
        if (this.pluginConfig.iconFlagMenuNextPageSlot != -1) {
            bukkitMenuPaginated.setNextPageSlot(this.pluginConfig.iconFlagMenuNextPageSlot, doer -> this.messageConfig.lastPageReach.send(doer));
        }

        bukkitMenuPaginated.getStorageItemSlots().removeIf(slot -> this.pluginConfig.iconFlagMenuIgnoreSlots.contains(slot));

        if (this.hoeItem.getItemStack().hasItemMeta()) {
            Set<ItemFlag> flags = this.getFlags();

            this.genCashHoeService.getFlagActivePresenters().entrySet().stream().filter(flag -> flags.contains(flag.getKey())).forEach(activeFlag -> {
                bukkitMenuPaginated.addStorageItem(activeFlag.getValue().clone(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        if (this.hoeItem.getItemStack().hasItemMeta()) {
                            this.removeItemFlag(activeFlag.getKey());
                        }

                        this.tasker.newChain()
                                .supplyAsync(() -> this.build(player))
                                .acceptSync(newMenu -> newMenu.open(0, player))
                                .execute();
                    }
                });
            });
            this.genCashHoeService.getFlagNoActivePresenters().entrySet().stream().filter(flag -> !flags.contains(flag.getKey())).forEach(noActiveFlag -> {
                bukkitMenuPaginated.addStorageItem(noActiveFlag.getValue().clone(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        if (this.hoeItem.getItemStack().hasItemMeta()) {
                            this.addItemFlag(noActiveFlag.getKey());
                        }

                        this.tasker.newChain()
                                .supplyAsync(() -> this.build(player))
                                .acceptSync(newMenu -> newMenu.open(0, player))
                                .execute();
                    }
                });
            });
        }

        return bukkitMenuPaginated;
    }

    private Set<ItemFlag> getFlags() {
        ItemMeta itemMeta = this.hoeItem.getItemStack().getItemMeta();
        assert itemMeta != null;

        return itemMeta.getItemFlags();
    }

    public void addItemFlag(@NonNull ItemFlag flag) {
        ItemMeta meta = this.hoeItem.getItemStack().getItemMeta();
        assert meta != null;

        Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();

        if (modifiers == null) {
            modifiers = HashMultimap.create();
            meta.setAttributeModifiers(modifiers);
        }

        meta.addItemFlags(flag);
        this.hoeItem.getItemStack().setItemMeta(meta);
    }
    public void removeItemFlag(@NonNull ItemFlag itemFlag) {
        ItemMeta itemMeta = this.hoeItem.getItemStack().getItemMeta();
        assert itemMeta != null;

        itemMeta.removeItemFlags(itemFlag);
        this.hoeItem.getItemStack().setItemMeta(itemMeta);
    }
}