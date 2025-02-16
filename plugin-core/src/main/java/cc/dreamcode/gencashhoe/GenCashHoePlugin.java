package cc.dreamcode.gencashhoe;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.adventure.BukkitMenuProvider;
import cc.dreamcode.menu.adventure.serializer.MenuBuilderSerializer;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.serializer.BukkitNoticeSerializer;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.ConfigurationResolver;
import cc.dreamcode.platform.bukkit.hook.PluginHookManager;
import cc.dreamcode.platform.component.ComponentService;
import cc.dreamcode.platform.other.component.DreamCommandExtension;
import cc.dreamcode.gencashhoe.command.HoeCommand;
import cc.dreamcode.gencashhoe.command.handler.InvalidInputHandlerImpl;
import cc.dreamcode.gencashhoe.command.handler.InvalidPermissionHandlerImpl;
import cc.dreamcode.gencashhoe.command.handler.InvalidSenderHandlerImpl;
import cc.dreamcode.gencashhoe.command.handler.InvalidUsageHandlerImpl;
import cc.dreamcode.gencashhoe.command.result.BukkitNoticeResolver;
import cc.dreamcode.gencashhoe.config.MessageConfig;
import cc.dreamcode.gencashhoe.config.PluginConfig;
import cc.dreamcode.gencashhoe.worldguard.WorldGuardHook;
import cc.dreamcode.utilities.bukkit.nbt.ItemNbtUtil;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class GenCashHoePlugin extends DreamBukkitPlatform implements DreamBukkitConfig {

    @Getter private static GenCashHoePlugin instance;

    @Override
    public void load(@NonNull ComponentService componentService) {
        instance = this;
        ItemNbtUtil.setPlugin(this);
    }

    @Override
    public void enable(@NonNull ComponentService componentService) {
        componentService.setDebug(false);

        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));

        this.registerInjectable(BukkitCommandProvider.create(this));
        componentService.registerExtension(DreamCommandExtension.class);

        componentService.registerResolver(ConfigurationResolver.class);
        componentService.registerComponent(MessageConfig.class);

        componentService.registerComponent(BukkitNoticeResolver.class);
        componentService.registerComponent(InvalidInputHandlerImpl.class);
        componentService.registerComponent(InvalidPermissionHandlerImpl.class);
        componentService.registerComponent(InvalidSenderHandlerImpl.class);
        componentService.registerComponent(InvalidUsageHandlerImpl.class);

        componentService.registerComponent(PluginConfig.class, pluginConfig -> {
            // enable additional logs and debug messages
            componentService.setDebug(pluginConfig.debug);
        });

        // hook
        componentService.registerComponent(PluginHookManager.class, dreamHookManager ->
                dreamHookManager.registerHook(WorldGuardHook.class)
        );

        // service
        componentService.registerComponent(GenCashHoeService.class);

        // controller
        componentService.registerComponent(GenCashHoeController.class);

        // command
        componentService.registerComponent(HoeCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-GenCashHoe", "1.0.1", "n4a_");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerializer());
            registry.register(new MenuBuilderSerializer());
            registry.register(new HoeItemSerializer());
        };
    }
}
