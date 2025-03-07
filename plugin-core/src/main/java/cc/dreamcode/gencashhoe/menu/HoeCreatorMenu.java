package cc.dreamcode.gencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.gencashhoe.GenCashHoePlugin;
import cc.dreamcode.gencashhoe.GenCashHoeService;
import cc.dreamcode.gencashhoe.HoeCreatorItem;
import cc.dreamcode.gencashhoe.HoeItem;
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

import java.util.stream.Collectors;

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
            if (this.pluginConfig.iconMenuSetSizeSlot == slot) {
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

                return;
            }

            if (this.pluginConfig.iconMenuSetNameSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors(new MapBuilder<String, Object>()
                        .put("name", hoeItem.getItemStack().getItemMeta().getDisplayName())
                        .build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        player.closeInventory();
                        this.messageConfig.provideText.send(player);

                        this.genCashHoeService.addToNameEditors(player.getUniqueId(), this.hoeItem);
                    }
                });

                return;
            }

            if (this.pluginConfig.iconMenuSetLoreSlot == slot) {
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

                return;
            }

            if (this.pluginConfig.iconMenuSetEnchantsSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors(new MapBuilder<String, Object>()
                        .put("enchants", (!hoeItem.getItemStack().getItemMeta().getEnchants().isEmpty()) ? hoeItem.getItemStack().getItemMeta().getEnchants().entrySet().stream().map(ench -> ench.getKey().getName().toLowerCase() + " - " + ench.getValue()).collect(Collectors.joining(", ")) : "&cBrak!")
                        .build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.tasker.newChain()
                                .supplyAsync(() -> {
                                    final HoeCreatorEnchantMenu hoeCreatorEnchantMenu = this.genCashHoePlugin.createInstance(HoeCreatorEnchantMenu.class);
                                    hoeCreatorEnchantMenu.setHoeItem(hoeItem);

                                    return hoeCreatorEnchantMenu.build(player);
                                })
                                .acceptSync(newMenu -> newMenu.open(0, player))
                                .execute();
                    }
                });

                return;
            }

            if (this.pluginConfig.iconMenuSetBlocksSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors(new MapBuilder<String, Object>()
                        .put("blocks", (!hoeItem.getBreakables().isEmpty()) ? hoeItem.getBreakables().stream().map(breakable -> breakable.name().toLowerCase()).collect(Collectors.joining(", ")) : "&cBrak!")
                        .build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.tasker.newChain()
                                .supplyAsync(() -> {
                                    final HoeCreatorBlocksMenu hoeCreatorBlocksMenu = this.genCashHoePlugin.createInstance(HoeCreatorBlocksMenu.class);
                                    hoeCreatorBlocksMenu.setHoeItem(hoeItem);

                                    return hoeCreatorBlocksMenu.build(player);
                                })
                                .acceptSync(newMenu -> newMenu.open(0, player))
                                .execute();
                    }
                });

                return;
            }

            if (this.pluginConfig.iconMenuSetFlagsSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors(new MapBuilder<String, Object>()
                        .put("flags", (this.hoeItem.getItemStack().getItemMeta() != null || !this.hoeItem.getItemStack().getItemMeta().getItemFlags().isEmpty()) ? this.hoeItem.getItemStack().getItemMeta().getItemFlags().stream().map(breakable -> breakable.name().toLowerCase()).collect(Collectors.joining(", ")) : "&cBrak!")
                        .build()).toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        this.tasker.newChain()
                                .supplyAsync(() -> {
                                    final HoeCreatorFlagMenu hoeCreatorFlagMenu = this.genCashHoePlugin.createInstance(HoeCreatorFlagMenu.class);
                                    hoeCreatorFlagMenu.setHoeItem(this.hoeItem);

                                    return hoeCreatorFlagMenu.build(player);
                                })
                                .acceptSync(newMenu -> newMenu.open(0, player))
                                .execute();
                    }
                });

                return;
            }

            if (this.pluginConfig.iconMenuCreateHoeSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        player.closeInventory();
                        this.messageConfig.hoeCreate.send(player);

                        this.pluginConfig.hoes.add(new HoeItem(hoeItem.getId(), hoeItem.getItemStack(), false, hoeItem.getSize(), hoeItem.getBreakables()));
                        this.pluginConfig.save();
                    }
                });

                return;
            }

            bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack());
        });

        return bukkitMenu;
    }
}
