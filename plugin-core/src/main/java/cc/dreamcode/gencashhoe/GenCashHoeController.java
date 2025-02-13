package cc.dreamcode.gencashhoe;

import cc.dreamcode.platform.bukkit.hook.PluginHookManager;
import cc.dreamcode.gencashhoe.config.MessageConfig;
import cc.dreamcode.gencashhoe.config.PluginConfig;
import cc.dreamcode.gencashhoe.menu.HoeCreatorEnchantMenu;
import cc.dreamcode.gencashhoe.menu.HoeCreatorLoreMenu;
import cc.dreamcode.gencashhoe.menu.HoeCreatorMenu;
import cc.dreamcode.gencashhoe.worldguard.WorldGuardHook;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.utilities.bukkit.nbt.ItemNbtUtil;
import cc.dreamcode.utilities.object.Duo;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenCashHoeController implements Listener {

    private final GenCashHoePlugin genCashHoePlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final GenCashHoeService genCashHoeService;
    private final PluginHookManager pluginHookManager;
    private final Tasker tasker;

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (this.genCashHoeService.isNameEditor(player.getUniqueId())) {
            event.setCancelled(true);

            HoeCreatorItem hoeCreatorItem = this.genCashHoeService.getNameEditor(player.getUniqueId());
            String message = event.getMessage();

            ItemBuilder itemBuilder = ItemBuilder.of(hoeCreatorItem.getItemStack());
            hoeCreatorItem.setItemStack(itemBuilder.setName(message).toItemStack());

            this.messageConfig.nameChanged.send(player);

            this.tasker.newChain().supplyAsync(() -> {
                HoeCreatorMenu hoeCreatorMenu = this.genCashHoePlugin.createInstance(HoeCreatorMenu.class);
                hoeCreatorMenu.setHoeItem(hoeCreatorItem);
                return hoeCreatorMenu.build(player);
            }).acceptSync(menu -> menu.open(player)).execute();

            this.genCashHoeService.removeFromNameEditors(player.getUniqueId());

            return;
        }

        if (this.genCashHoeService.isLoreEditor(player.getUniqueId())) {
            event.setCancelled(true);

            Duo<HoeCreatorItem, Integer> values = this.genCashHoeService.getLoreEditor(player.getUniqueId());
            HoeCreatorItem hoeCreatorItem = values.getFirst();
            int index = values.getSecond();

            String message = event.getMessage();

            if (hoeCreatorItem.getItemStack().hasItemMeta()) {
                List<String> lore = hoeCreatorItem.getItemStack().getItemMeta().getLore();
                if (!lore.isEmpty()) {
                    lore.set(index, message);
                }

                ItemBuilder itemBuilder = ItemBuilder.of(hoeCreatorItem.getItemStack());
                itemBuilder.setLore(lore);

                hoeCreatorItem.setItemStack(itemBuilder.toItemStack());
            }

            this.messageConfig.loreLineChanged.send(player);

            this.tasker.newChain().supplyAsync(() -> {
                HoeCreatorLoreMenu hoeCreatorLoreMenu = this.genCashHoePlugin.createInstance(HoeCreatorLoreMenu.class);
                hoeCreatorLoreMenu.setHoeItem(hoeCreatorItem);
                return hoeCreatorLoreMenu.build(player);
            }).acceptSync(menu -> menu.open(0, player)).execute();

            this.genCashHoeService.removeFromLoreEditors(player.getUniqueId());

            return;
        }

        if (this.genCashHoeService.isEnchantEditor(player.getUniqueId())) {
            event.setCancelled(true);

            Duo<HoeCreatorItem, Enchantment> values = this.genCashHoeService.getEnchantEditor(player.getUniqueId());
            HoeCreatorItem hoeCreatorItem = values.getFirst();
            Enchantment enchantment = values.getSecond();

            String message = event.getMessage();

            try {
                int number = Integer.parseInt(message);

                if (hoeCreatorItem.getItemStack().hasItemMeta()) {
                    ItemBuilder itemBuilder = ItemBuilder.of(hoeCreatorItem.getItemStack());
                    itemBuilder.addEnchant(enchantment, number);

                    hoeCreatorItem.setItemStack(itemBuilder.toItemStack());
                }

                this.messageConfig.enchantAdd.send(player);
            } catch (NumberFormatException e) {
                this.messageConfig.numberIsNotValid.send(player);
            }

            this.tasker.newChain().supplyAsync(() -> {
                HoeCreatorEnchantMenu hoeCreatorEnchantMenu = this.genCashHoePlugin.createInstance(HoeCreatorEnchantMenu.class);
                hoeCreatorEnchantMenu.setHoeItem(hoeCreatorItem);
                return hoeCreatorEnchantMenu.build(player);
            }).acceptSync(menu -> menu.open(0, player)).execute();

            this.genCashHoeService.removeFromEnchantEditors(player.getUniqueId());

            return;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.genCashHoeService.removeFromNameEditors(player.getUniqueId());
        this.genCashHoeService.removeFromLoreEditors(player.getUniqueId());
        this.genCashHoeService.removeFromEnchantEditors(player.getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();

            if (item != null) {
                Optional<String> optionalId = ItemNbtUtil.getValueByPlugin(item, "id");

                if (optionalId.isPresent()) {
                    HoeItem hoeItem = this.genCashHoeService.getHoe(Integer.parseInt(optionalId.get()));
                    if (hoeItem == null) {
                        return;
                    }

                    event.setCancelled(true);

                    int size = hoeItem.getSize();
                    int radius = size / 2;

                    boolean displayWarning = false;

                    for (int x = -radius; x < radius + (size % 2 == 0 ? 0 : 1); x++) {
                        for (int z = -radius; z < radius + (size % 2 == 0 ? 0 : 1); z++) {
                            Block block = event.getClickedBlock().getLocation().clone().add(x, 0, z).getBlock();

                            if (block.getType() != Material.AIR) {
                                if (this.checkRegion(event.getClickedBlock().getLocation())) {
                                    continue;
                                }

                                if (!hoeItem.getBreakables().isEmpty() && !hoeItem.getBreakables().contains(XMaterial.matchXMaterial(block.getType()))) {
                                    displayWarning = true;
                                    continue;
                                }

                                block.breakNaturally();
                                this.genCashHoePlugin.getServer().getPluginManager().callEvent(new BlockBreakEvent(block, player));
                            }
                        }
                    }

                    if (displayWarning) {
                        this.messageConfig.cantDestory.send(player);
                    }
                }
            }
        }
    }

    private boolean checkRegion(Location location) {
        if (this.pluginHookManager.get(WorldGuardHook.class).isPresent()) {
            WorldGuardHook worldGuardHook = this.pluginHookManager.get(WorldGuardHook.class).get();

            return worldGuardHook.isProtected(worldGuardHook, pluginConfig.blockedRegions, location);
        }

        return false;
    }
}
