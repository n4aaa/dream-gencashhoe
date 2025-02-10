package cc.dreamcode.plugingencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.plugingencashhoe.GenCashHoeService;
import cc.dreamcode.plugingencashhoe.HoeCreatorItem;
import cc.dreamcode.plugingencashhoe.HoeItem;
import cc.dreamcode.plugingencashhoe.config.MessageConfig;
import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCreatorMenu implements BukkitMenuPlayerSetup {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;

    @Setter private HoeCreatorItem hoeItem;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.hoeCreatorMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildWithItems(new MapBuilder<String, Object>()
                .put("size", hoeItem.getSize())
                .put("name", hoeItem.getItemStack().getItemMeta().getDisplayName())
                .build());

        menuBuilder.getItems().forEach((slot, item) -> {
            if (pluginConfig.iconMenuSetSize == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuSetName == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuSetLore == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuSetEnchants == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuSetBlocks == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {

                });
            }

            if (pluginConfig.iconMenuCreateHoe == slot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), e -> {
                    Player player = (Player) e.getWhoClicked();

                    this.messageConfig.hoeCreate.send(player);

                    this.pluginConfig.hoes.add(new HoeItem(hoeItem.getId(), hoeItem.getItemStack(), hoeItem.getSize(), hoeItem.getBreakables()));
                    this.pluginConfig.save();
                });
            }
        });

        return bukkitMenu;
    }
}
