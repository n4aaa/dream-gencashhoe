package cc.dreamcode.plugingencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.plugingencashhoe.GenCashHoePlugin;
import cc.dreamcode.plugingencashhoe.GenCashHoeService;
import cc.dreamcode.plugingencashhoe.HoeCreatorItem;
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
public class HoeCreatorSizeMenu implements BukkitMenuPlayerSetup {

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
                .build());

        menuBuilder.getItems().forEach((slot, item) -> {
            if (pluginConfig.iconMenuDecreaseSize == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    hoeItem.setSize(hoeItem.getSize() - 1);
                });
            }

            if (pluginConfig.iconMenuIncreaseSize == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    hoeItem.setSize(hoeItem.getSize() + 1);
                });
            }

            if (pluginConfig.iconMenuSubmitSize == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    Player player = (Player) e.getWhoClicked();

                    this.tasker.newChain()
                            .supplyAsync(() -> {
                                final HoeCreatorMenu hoeCreatorMenu = this.genCashHoePlugin.createInstance(HoeCreatorMenu.class);
                                hoeCreatorMenu.setHoeItem(hoeItem);

                                return hoeCreatorMenu.build(player);
                            })
                            .acceptSync(newMenu -> newMenu.open(player))
                            .execute();
                });
            }
        });

        return bukkitMenu;
    }
}
