package cc.dreamcode.plugingencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.plugingencashhoe.GenCashHoePlugin;
import cc.dreamcode.plugingencashhoe.GenCashHoeService;
import cc.dreamcode.plugingencashhoe.HoeCreatorItem;
import cc.dreamcode.plugingencashhoe.HoeItem;
import cc.dreamcode.plugingencashhoe.config.MessageConfig;
import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCreatorMenu implements BukkitMenuPlayerSetup {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @Setter private HoeCreatorItem hoeItem;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.hoeCreatorMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildWithItems(new MapBuilder<String, Object>()
                .put("size", hoeItem.getSize())
                .put("name", hoeItem.getItemStack().getItemMeta().getDisplayName())
                .build());

        menuBuilder.getItems().forEach((slot, item) -> {
            if (pluginConfig.iconMenuSetSizeSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors(new MapBuilder<String, Object>()
                        .put("size", hoeItem.getSize())
                        .build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.tasker.newChain()
                                .supplyAsync(() -> {
                                    final HoeCreatorSizeMenu hoeCreatorSizeMenu = this.genCashHoePlugin.createInstance(HoeCreatorSizeMenu.class);
                                    hoeCreatorSizeMenu.setHoeItem(hoeItem);

                                    return hoeCreatorSizeMenu.build(player);
                                })
                                .acceptSync(newMenu -> newMenu.open(player))
                                .execute();
                    }
                });
            }

            if (pluginConfig.iconMenuSetNameSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors(new MapBuilder<String, Object>()
                        .put("name", hoeItem.getItemStack().getItemMeta().getDisplayName())
                        .build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.genCashHoeService.addToNameEditors(player.getUniqueId(), this.hoeItem);
                        player.closeInventory();

                        this.messageConfig.provideText.send(player);
                    }
                });
            }

            if (pluginConfig.iconMenuSetLoreSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.tasker.newChain()
                                .supplyAsync(() -> {
                                    final HoeCreatorLoreMenu hoeCreatorLoreMenu = this.genCashHoePlugin.createInstance(HoeCreatorLoreMenu.class);
                                    hoeCreatorLoreMenu.setHoeItem(hoeItem);

                                    return hoeCreatorLoreMenu.build(player);
                                })
                                .acceptSync(newMenu -> newMenu.open(0, player))
                                .execute();
                    }
                });
            }

            if (pluginConfig.iconMenuSetEnchantsSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuSetBlocksSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuCreateHoeSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.messageConfig.hoeCreate.send(player);

                        this.pluginConfig.hoes.add(new HoeItem(hoeItem.getId(), hoeItem.getItemStack(), hoeItem.getSize(), hoeItem.getBreakables()));
                        this.pluginConfig.save();
                    }
                });
            }
        });

        return bukkitMenu;
    }
}
