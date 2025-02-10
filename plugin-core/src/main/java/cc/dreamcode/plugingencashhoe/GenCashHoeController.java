package cc.dreamcode.plugingencashhoe;

import cc.dreamcode.plugingencashhoe.config.MessageConfig;
import cc.dreamcode.plugingencashhoe.menu.HoeCreatorLoreMenu;
import cc.dreamcode.plugingencashhoe.menu.HoeCreatorMenu;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.utilities.object.Duo;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenCashHoeController implements Listener {

    private final GenCashHoePlugin genCashHoePlugin;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final Tasker tasker;

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (this.genCashHoeService.isNameEditor(player.getUniqueId())) {
            event.setCancelled(true);

            HoeCreatorItem hoeCreatorItem = this.genCashHoeService.getNameEditor(player.getUniqueId());
            String message = event.getMessage();

            this.tasker.newChain().sync(() -> {
                ItemBuilder itemBuilder = ItemBuilder.of(hoeCreatorItem.getItemStack());
                hoeCreatorItem.setItemStack(itemBuilder.setName(message).fixColors().toItemStack());

                this.messageConfig.nameChanged.send(player);

                final HoeCreatorMenu hoeCreatorMenu = this.genCashHoePlugin.createInstance(HoeCreatorMenu.class);
                hoeCreatorMenu.setHoeItem(hoeCreatorItem);
                hoeCreatorMenu.build(player).open(player);
            }).execute();

            this.genCashHoeService.removeFromNameEditors(player.getUniqueId());
        }

        if (this.genCashHoeService.isLoreEditor(player.getUniqueId())) {
            event.setCancelled(true);

            Duo<HoeCreatorItem, Integer> values = this.genCashHoeService.getLoreEditor(player.getUniqueId());
            HoeCreatorItem hoeCreatorItem = values.getFirst();
            int index = values.getSecond();

            String message = event.getMessage();

            this.tasker.newChain().sync(() -> {
                ItemBuilder itemBuilder = ItemBuilder.of(hoeCreatorItem.getItemStack());

                if (hoeCreatorItem.getItemStack().hasItemMeta()) {
                    List<String> lore = hoeCreatorItem.getItemStack().getItemMeta().getLore();
                    lore.set(index, message);

                    itemBuilder.setLore(lore);
                }

                hoeCreatorItem.setItemStack(itemBuilder.fixColors().toItemStack());

                this.messageConfig.loreLineChanged.send(player);

                final HoeCreatorLoreMenu hoeCreatorLoreMenu = this.genCashHoePlugin.createInstance(HoeCreatorLoreMenu.class);
                hoeCreatorLoreMenu.setHoeItem(hoeCreatorItem);
                hoeCreatorLoreMenu.build(player).open(0, player);
            }).execute();

            this.genCashHoeService.removeFromLoreEditors(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.genCashHoeService.removeFromNameEditors(player.getUniqueId());
        this.genCashHoeService.removeFromLoreEditors(player.getUniqueId());
    }
}
