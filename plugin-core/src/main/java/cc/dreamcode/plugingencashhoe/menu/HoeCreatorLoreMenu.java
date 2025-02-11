package cc.dreamcode.plugingencashhoe.menu;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.base.BukkitMenuPaginated;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPaginatedPlayerSetup;
import cc.dreamcode.plugingencashhoe.GenCashHoePlugin;
import cc.dreamcode.plugingencashhoe.GenCashHoeService;
import cc.dreamcode.plugingencashhoe.HoeCreatorItem;
import cc.dreamcode.plugingencashhoe.config.MessageConfig;
import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
            }
        });

        final BukkitMenuPaginated bukkitMenuPaginated = bukkitMenu.toPaginated();

        if (this.pluginConfig.iconLoreMenuPreviousPageSlot != -1) {
            bukkitMenuPaginated.setPreviousPageSlot(this.pluginConfig.iconLoreMenuPreviousPageSlot, doer -> this.messageConfig.firstPageReach.send(doer));
        }
        if (this.pluginConfig.iconLoreMenuNextPageSlot != -1) {
            bukkitMenuPaginated.setNextPageSlot(this.pluginConfig.iconLoreMenuNextPageSlot, doer -> this.messageConfig.lastPageReach.send(doer));
        }

        if (this.hoeItem.getItemStack().hasItemMeta()) {
            List<String> lore = this.hoeItem.getItemStack().getItemMeta().getLore();

            IntStream.range(0, lore.size()).forEach(i -> {
                String line = lore.get(i);

                bukkitMenuPaginated.addStorageItem(ItemBuilder.of(XMaterial.PAPER.parseMaterial()).setName(!line.isEmpty() ? line : "&cPusta linia!").fixColors().toItemStack(), e -> {
                    if (e.getWhoClicked() instanceof Player) {
                        Player player = (Player) e.getWhoClicked();

                        switch (e.getClick()) {
                            case LEFT:
                            case RIGHT: {
                                this.genCashHoeService.addToLoreEditors(player.getUniqueId(), i, this.hoeItem);
                                player.closeInventory();

                                this.messageConfig.provideText.send(player);
                            }

                            case SHIFT_LEFT:
                            case SHIFT_RIGHT: {
                                ItemBuilder itemBuilder = ItemBuilder.of(this.hoeItem.getItemStack());
                                itemBuilder.setLore(lore.remove(i));

                                this.hoeItem.setItemStack(itemBuilder.toItemStack());

                                this.tasker.newChain()
                                        .supplyAsync(() -> this.build(player))
                                        .acceptSync(newMenu -> newMenu.open(0, player))
                                        .execute();
                            }
                        }
                    }
                });
            });
        }

        return bukkitMenuPaginated;
    }
}
