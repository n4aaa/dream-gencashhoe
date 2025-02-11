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
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.base.XBase;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCreatorBlocksMenu implements BukkitMenuPaginatedPlayerSetup {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @Setter private HoeCreatorItem hoeItem;

    @Override
    public BukkitMenuPaginated build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.hoeCreatorBreakablesMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildEmpty();

        menuBuilder.getItems().forEach((slot, item) -> {
            if (slot == this.pluginConfig.iconBreakablesMenuCloseSlot) {
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

        if (this.pluginConfig.iconBreakablesMenuPreviousPageSlot != -1) {
            bukkitMenuPaginated.setPreviousPageSlot(this.pluginConfig.iconBreakablesMenuPreviousPageSlot, doer -> this.messageConfig.firstPageReach.send(doer));
        }
        if (this.pluginConfig.iconBreakablesMenuNextPageSlot != -1) {
            bukkitMenuPaginated.setNextPageSlot(this.pluginConfig.iconBreakablesMenuNextPageSlot, doer -> this.messageConfig.lastPageReach.send(doer));
        }

        bukkitMenuPaginated.getStorageItemSlots().removeIf(slot -> this.pluginConfig.iconBreakablesMenuIgnoreSlots.contains(slot));

        List<XMaterial> breakables = hoeItem.getBreakables();
        Arrays.stream(Material.values()).filter(Material::isSolid).collect(Collectors.toList()).stream().filter(material -> !breakables.contains(XMaterial.matchXMaterial(material))).forEach(material -> {
            bukkitMenuPaginated.addStorageItem(ItemBuilder.of(this.pluginConfig.iconBreakablesMenuTemplate.clone()).fixColors(new MapBuilder<String, Object>().put("name", material.name().toLowerCase()).build()).setType(material).toItemStack(), e -> {
                if (e.getWhoClicked() instanceof Player) {
                    Player player = (Player) e.getWhoClicked();

                    this.hoeItem.getBreakables().add(XMaterial.matchXMaterial(material));
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
        });

        return bukkitMenuPaginated;
    }
}