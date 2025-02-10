package cc.dreamcode.plugingencashhoe.config;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.utilities.MenuUtil;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.plugingencashhoe.HoeItem;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Configuration(child = "config.yml")
@Header("## Dream-GenCashHoe (Main-Config) ##")
public class PluginConfig extends OkaeriConfig {

    @Comment
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    @CustomKey("debug")
    public boolean debug = true;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki:")
    @CustomKey("hoe-creator-menu-builder")
    public BukkitMenuBuilder hoeCreatorMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki", 3, new MapBuilder<Integer, ItemStack>()
            .put(10, ItemBuilder.of(XMaterial.PAPER.parseMaterial())
                    .setName("&9&lUstaw rozmiar")
                    .setLore("&8» &7Obecny rozmiar: &a&l{size}x{size}",
                            "",
                            "&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(11, ItemBuilder.of(XMaterial.OAK_SIGN.parseMaterial())
                    .setName("&9&lUstaw nazwę")
                    .setLore("&8» &7Obecna nazwa: &a{name}",
                            "",
                            "&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(12, ItemBuilder.of(XMaterial.BOOK.parseMaterial())
                    .setName("&9&lUstaw opis")
                    .setLore("&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(13, ItemBuilder.of(XMaterial.ENCHANTED_BOOK.parseMaterial())
                    .setName("&9&lEnchanty")
                    .setLore("&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(14, ItemBuilder.of(XMaterial.GRASS_BLOCK.parseMaterial())
                    .setName("&9&lBloki")
                    .setLore("&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(16, ItemBuilder.of(XMaterial.LIME_DYE.parseMaterial())
                    .setName("&a&lStwórz motykę")
                    .setLore("&8» &7Kliknj &fPPM&7, aby stworzyć motykę!")
                    .toItemStack())
            .build());

    @Comment("Skonfiguruj slot przycisku ustawiania rozmiaru:")
    @CustomKey("icon-menu-set-size")
    public int iconMenuSetSize = 10;
    @Comment("Skonfiguruj slot przycisku ustawiania nazwy:")
    @CustomKey("icon-menu-set-name")
    public int iconMenuSetName = 11;
    @Comment("Skonfiguruj slot przycisku ustawiania opisu:")
    @CustomKey("icon-menu-set-lore")
    public int iconMenuSetLore = 12;
    @Comment("Skonfiguruj slot przycisku ustawiania enchantów:")
    @CustomKey("icon-menu-set-enchants")
    public int iconMenuSetEnchants = 13;
    @Comment("Skonfiguruj slot przycisku ustawiania dozwolonych bloków:")
    @CustomKey("icon-menu-set-blocks")
    public int iconMenuSetBlocks = 14;
    @Comment("Skonfiguruj slot przycisku tworzenia motyki:")
    @CustomKey("icon-menu-create-hoe")
    public int iconMenuCreateHoe = 15;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki:")
    @CustomKey("hoe-creator-menu-builder")
    public BukkitMenuBuilder hoeCreatorSizeMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (rozmiar)", 3, new MapBuilder<Integer, ItemStack>()
            .put(12, ItemBuilder.of(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial())
                    .setName("&c&l-1")
                    .toItemStack())
            .put(13, ItemBuilder.of(XMaterial.LIME_DYE.parseMaterial())
                    .setName("&9&lPotwierdź")
                    .setLore("&8» &7Rozmiar: &f{size}",
                            "",
                            "&8» &7Kliknj &fPPM&7, aby potwierdzić rozmiar!")
                    .toItemStack())
            .put(14, ItemBuilder.of(XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial())
                    .setName("&a&l+1")
                    .toItemStack())
            .build());
    @Comment("Skonfiguruj slot przycisku potwierdzania rozmiaru motyki:")
    @CustomKey(value="icon-menu-submit-size")
    public int iconMenuSubmitSize = 13;
    @Comment("Skonfiguruj slot przycisku powiększania rozmiaru motyki:")
    @CustomKey(value="icon-menu-decrease-size")
    public int iconMenuDecreaseSize = 12;
    @Comment("Skonfiguruj slot przycisku zmniejszania rozmiaru motyki:")
    @CustomKey(value="icon-menu-increase-size")
    public int iconMenuIncreaseSize = 14;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki (wybór dozwolonych bloków):")
    @CustomKey("hoe-breakables-menu-builder")
    public BukkitMenuBuilder hoeBreakablesMenuBuilder = new BukkitMenuBuilder("&8Wybierz bloki do niszczenia", 6, new MapBuilder<Integer, ItemStack>()
            .put(MenuUtil.countSlot(6, 4), ItemBuilder.of(XMaterial.ARROW.parseMaterial())
                    .setName("&cPowrot do porzedniej strony.")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone.")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 5), ItemBuilder.of(XMaterial.BARRIER.parseMaterial())
                    .setName("&cPowrot do menu glownego.")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 6), ItemBuilder.of(XMaterial.ARROW.parseMaterial())
                    .setName("&aPrzejdz do nastepnej strony.")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone.")
                    .toItemStack())
            .build());

    @CustomKey(value="icon-menu-next-page-slot")
    public int iconMenuNextPageSlot = MenuUtil.countSlot(6, 6);
    @CustomKey(value="icon-menu-previous-page-slot")
    public int iconMenuPreviousPageSlot = MenuUtil.countSlot(6, 4);

    @Comment
    @Comment("Skonfiguruj domyślny item motyki:")
    public ItemStack defaultHoeItemStack = ItemBuilder.of(XMaterial.NETHERITE_HOE.parseMaterial()).setName("&6&lMotyka {size}x{size}").setLore(Arrays.asList("", "&8» &7Ta motyka kopie: &f{size}x{size}")).addEnchant(Enchantment.DURABILITY, 4).toItemStack();

    @Comment("Skonfiguruj domyślne dozwolone do zniszczenia bloki przez motyke:")
    @CustomKey("default-hoe-breakables")
    public List<XMaterial> defaultHoeBreakables = Arrays.asList(XMaterial.GRASS_BLOCK, XMaterial.STONE);

    @Comment
    @Comment("Skonfiguruj motyki:")
    @CustomKey("hoes")
    public List<HoeItem> hoes = Arrays.asList(
            new HoeItem(
                    1,
                    ItemBuilder.of(XMaterial.NETHERITE_HOE.parseMaterial()).setName("&6&lMotyka 2x2").setLore(Arrays.asList("", "&8» &7Ta motyka kopie: &f2x2")).addEnchant(Enchantment.DURABILITY, 4).toItemStack(),
                    2,
                    Arrays.asList(
                            XMaterial.GRASS_BLOCK,
                            XMaterial.STONE)
            )
    );
}

