package cc.dreamcode.gencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.base.BukkitMenuPaginated;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPaginatedPlayerSetup;
import cc.dreamcode.gencashhoe.GenCashHoePlugin;
import cc.dreamcode.gencashhoe.GenCashHoeService;
import cc.dreamcode.gencashhoe.HoeCreatorItem;
import cc.dreamcode.gencashhoe.config.MessageConfig;
import cc.dreamcode.gencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCreatorLoreMenu implements BukkitMenuPaginatedPlayerSetup {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @Setter private HoeCreatorItem hoeItem;

    @Override
    public BukkitMenuPaginated build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.hoeCreatorLoreMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildEmpty();

        menuBuilder.getItems().forEach((slot, item) -> {
            if (slot == this.pluginConfig.iconLoreMenuCloseSlot) {
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

            if (slot == this.pluginConfig.iconLoreMenuAddLineSlot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        if (this.hoeItem.getItemStack().hasItemMeta()) {
                            ItemBuilder itemBuilder = ItemBuilder.of(this.hoeItem.getItemStack());
                            itemBuilder.appendLore("");

                            this.hoeItem.setItemStack(itemBuilder.toItemStack());
                            this.tasker.newChain()
                                    .supplyAsync(() -> this.build(player))
                                    .acceptSync(newMenu -> newMenu.open(0, player))
                                    .execute();
                        }
                    }
                });

                return;
            }

            bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack());
        });

        final BukkitMenuPaginated bukkitMenuPaginated = bukkitMenu.toPaginated();

        if (this.pluginConfig.iconLoreMenuPreviousPageSlot != -1) {
            bukkitMenuPaginated.setPreviousPageSlot(this.pluginConfig.iconLoreMenuPreviousPageSlot, doer -> this.messageConfig.firstPageReach.send(doer));
        }
        if (this.pluginConfig.iconLoreMenuNextPageSlot != -1) {
            bukkitMenuPaginated.setNextPageSlot(this.pluginConfig.iconLoreMenuNextPageSlot, doer -> this.messageConfig.lastPageReach.send(doer));
        }

        bukkitMenuPaginated.getStorageItemSlots().removeIf(slot -> this.pluginConfig.iconLoreMenuIgnoreSlots.contains(slot));

        if (this.hoeItem.getItemStack().hasItemMeta()) {
            List<String> lore = this.hoeItem.getItemStack().getItemMeta().getLore();

            IntStream.range(0, lore.size()).forEach(i -> {
                String line = lore.get(i);

                bukkitMenuPaginated.addStorageItem(ItemBuilder.of(pluginConfig.iconLoreMenuTemplate.clone()).fixColors(new MapBuilder<String, Object>().put("line", !line.isEmpty() ? line : "&cPusta linia!").build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        if (!e.isShiftClick()) {
                            this.genCashHoeService.addToLoreEditors(player.getUniqueId(), i, this.hoeItem);
                            player.closeInventory();

                            this.messageConfig.provideTextLine.send(player);

                            return;
                        }

                        lore.remove(i);

                        this.hoeItem.setItemStack(ItemBuilder.of(this.hoeItem.getItemStack()).setLore(lore).toItemStack());
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
}