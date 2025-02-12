package cc.dreamcode.gencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.gencashhoe.GenCashHoePlugin;
import cc.dreamcode.gencashhoe.HoeCreatorItem;
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

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCreatorSizeMenu implements BukkitMenuPlayerSetup {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final Tasker tasker;

    @Setter private HoeCreatorItem hoeItem;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.hoeCreatorSizeMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildWithItems(new MapBuilder<String, Object>()
                .put("size", this.hoeItem.getSize())
                .build());

        menuBuilder.getItems().forEach((slot, item) -> {
            if (this.pluginConfig.iconMenuDecreaseSizeSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (this.hoeItem.getSize() > 1) {
                        this.hoeItem.setSize(this.hoeItem.getSize() - 1);

                        bukkitMenu.setItem(this.pluginConfig.iconMenuPresenterSizeSlot, ItemBuilder.of(menuBuilder.getItems().get(this.pluginConfig.iconMenuPresenterSizeSlot))
                                .setAmount(this.hoeItem.getSize())
                                .fixColors(new MapBuilder<String, Object>()
                                        .put("size", this.hoeItem.getSize())
                                        .build())
                                .toItemStack());
                    }
                });

                return;
            }

            if (this.pluginConfig.iconMenuIncreaseSizeSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    if (this.hoeItem.getSize() < 64) {
                        this.hoeItem.setSize(this.hoeItem.getSize() + 1);

                        bukkitMenu.setItem(this.pluginConfig.iconMenuPresenterSizeSlot, ItemBuilder.of(menuBuilder.getItems().get(this.pluginConfig.iconMenuPresenterSizeSlot))
                                .setAmount(this.hoeItem.getSize())
                                .fixColors(new MapBuilder<String, Object>()
                                        .put("size", this.hoeItem.getSize())
                                        .build())
                                .toItemStack());
                    }
                });

                return;
            }

            if (this.pluginConfig.iconMenuSubmitSizeSlot == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    Player player = (Player) e.getWhoClicked();

                    this.tasker.newChain()
                            .supplyAsync(() -> {
                                final HoeCreatorMenu hoeCreatorMenu = this.genCashHoePlugin.createInstance(HoeCreatorMenu.class);
                                hoeCreatorMenu.setHoeItem(this.hoeItem);

                                return hoeCreatorMenu.build(player);
                            })
                            .acceptSync(newMenu -> newMenu.open(player))
                            .execute();
                });

                return;
            }

            if (this.pluginConfig.iconMenuPresenterSizeSlot == slot) {
                bukkitMenu.setItem(this.pluginConfig.iconMenuPresenterSizeSlot, ItemBuilder.of(menuBuilder.getItems().get(this.pluginConfig.iconMenuPresenterSizeSlot))
                        .setAmount(this.hoeItem.getSize())
                        .fixColors(new MapBuilder<String, Object>()
                                .put("size", this.hoeItem.getSize())
                                .build())
                        .toItemStack());

                return;
            }

            bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack());
        });

        return bukkitMenu;
    }
}
