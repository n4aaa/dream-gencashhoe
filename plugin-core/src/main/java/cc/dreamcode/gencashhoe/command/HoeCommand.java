package cc.dreamcode.gencashhoe.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.gencashhoe.GenCashHoePlugin;
import cc.dreamcode.gencashhoe.GenCashHoeService;
import cc.dreamcode.gencashhoe.HoeItem;
import cc.dreamcode.gencashhoe.config.MessageConfig;
import cc.dreamcode.gencashhoe.config.PluginConfig;
import cc.dreamcode.gencashhoe.menu.HoeCreatorMenu;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.InventoryUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

@Command(name = "hoe", aliases = "motyka")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCommand implements CommandBase {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @Async
    @Permission("dream-gencashhoe.give")
    @Completion(arg="target", value="@allplayers")
    @Executor(path = "give", description = "Nadaje podanemu graczu motykę.")
    public void give(Player player, @Arg Player target, @Arg int size) {
        List<HoeItem> hoeItems = genCashHoeService.getHoeList(size);

        if (hoeItems.isEmpty()) {
            this.messageConfig.hoeDoNotExits.send(player);

            return;
        }

        this.messageConfig.hoeGive.send(player, new MapBuilder<String, Object>().put("target", target.getName()).build());
        this.messageConfig.hoeReceive.send(target, new MapBuilder<String, Object>().put("player", player.getName()).build());

        hoeItems.forEach(hoeItem -> {
            ItemBuilder itemBuilder = ItemBuilder.of(hoeItem.getItemStack().clone());

            if (hoeItem.isDefaultLore()) {
                itemBuilder.setLore(pluginConfig.templateHoeLore);
            }

            InventoryUtil.giveItem(target, itemBuilder.withNbt(this.genCashHoePlugin, "id", String.valueOf(hoeItem.getId())).fixColors(new MapBuilder<String, Object>()
                    .put("size", hoeItem.getSize()).build())
                    .toItemStack()
            );
        });
    }

    @Async
    @Permission("dream-gencashhoe.custom")
    @Executor(path = "custom", description = "Tworzy customową motykę.")
    public void custom(Player player) {
        this.tasker.newChain()
                .supplyAsync(() -> {
                    final HoeCreatorMenu hoeCreatorMenu = this.genCashHoePlugin.createInstance(HoeCreatorMenu.class);
                    hoeCreatorMenu.setHoeItem(this.genCashHoeService.buildDefaultCreatorHoe(1));

                    return hoeCreatorMenu.build(player);
                })
                .acceptSync(bukkitMenu -> bukkitMenu.open(player))
                .execute();
    }

    @Async
    @Permission("dream-gencashhoe.create")
    @Executor(path = "create", description = "Tworzy motykę o podanym rozmiarze.")
    public void create(Player player, @Arg int size) {
        this.messageConfig.hoeCreate.send(player);

        this.pluginConfig.hoes.add(this.genCashHoeService.buildDefaultHoe(size));
        this.pluginConfig.save();
    }

    @Async
    @Permission("dream-gencashhoe.list")
    @Executor(path = "list", description = "Wyswietla liste motyk.")
    public void list(Player player) {
        this.messageConfig.hoeList.send(player);

        this.pluginConfig.hoes.forEach(hoeItem -> {
            this.messageConfig.hoeListElement.send(player, new MapBuilder<String, Object>()
                    .put("name", hoeItem.getItemStack().getItemMeta().getDisplayName())
                    .put("size", hoeItem.getSize() + "x" + hoeItem.getSize())
                    .build());
        });
    }

    @Async
    @Permission("dream-gencashhoe.reload")
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    BukkitNotice reload() {
        final long time = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            this.genCashHoeService.loadPresenters();

            return this.messageConfig.reloaded
                    .with("time", TimeUtil.format(System.currentTimeMillis() - time));
        }
        catch (NullPointerException | OkaeriException e) {
            e.printStackTrace();

            return this.messageConfig.reloadError
                    .with("error", e.getMessage());
        }
    }
}
