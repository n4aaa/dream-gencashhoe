package cc.dreamcode.plugingencashhoe.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.plugingencashhoe.GenCashHoePlugin;
import cc.dreamcode.plugingencashhoe.GenCashHoeService;
import cc.dreamcode.plugingencashhoe.HoeItem;
import cc.dreamcode.plugingencashhoe.config.MessageConfig;
import cc.dreamcode.plugingencashhoe.config.PluginConfig;
import cc.dreamcode.plugingencashhoe.menu.HoeCreatorMenu;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.InventoryUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Command(name = "hoe", aliases = "motyka")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HoeCommand implements CommandBase {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @Async
    @Completion(arg="target", value="@allplayers")
    @Executor(path = "give", description = "Nadaje podanemu graczu motykę.")
    public void give(Player player, @Arg Player target, @Arg int size) {
        final HoeItem hoeItem = genCashHoeService.getHoeItem(size);

        if (hoeItem == null) {
            this.messageConfig.hoeDoNotExits.send(player);

            return;
        }

        this.messageConfig.hoeGive.send(player, new MapBuilder<String, Object>().put("target", target.getName()).build());
        this.messageConfig.hoeReceive.send(target, new MapBuilder<String, Object>().put("player", player.getName()).build());

        ItemBuilder itemBuilder = ItemBuilder.of(hoeItem.getItemStack().clone()).fixColors();

        InventoryUtil.giveItem(target, itemBuilder.toItemStack());
    }

    @Async
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
    @Executor(path = "create", description = "Tworzy motykę o podanym rozmiarze.")
    public void create(Player player, @Arg int size) {
        this.messageConfig.hoeCreate.send(player);

        this.pluginConfig.hoes.add(this.genCashHoeService.buildDefaultHoe(size));
        this.pluginConfig.save();
    }

    @Async
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    BukkitNotice reload() {
        final long time = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

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
