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
                    .setLore("", "&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(13, ItemBuilder.of(XMaterial.ENCHANTED_BOOK.parseMaterial())
                    .setName("&9&lEnchanty")
                    .setLore("", "&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(14, ItemBuilder.of(XMaterial.GRASS_BLOCK.parseMaterial())
                    .setName("&9&lBloki")
                    .setLore("", "&8» &7Kliknj &fPPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(16, ItemBuilder.of(XMaterial.LIME_DYE.parseMaterial())
                    .setName("&a&lStwórz motykę")
                    .setLore("", "&8» &7Kliknj &fPPM&7, aby stworzyć motykę!")
                    .toItemStack())
            .build());

    @Comment("Skonfiguruj slot przycisku ustawiania rozmiaru:")
    @CustomKey("icon-menu-set-size-slot")
    public int iconMenuSetSizeSlot = 10;
    @Comment("Skonfiguruj slot przycisku ustawiania nazwy:")
    @CustomKey("icon-menu-set-name-slot")
    public int iconMenuSetNameSlot = 11;
    @Comment("Skonfiguruj slot przycisku ustawiania opisu:")
    @CustomKey("icon-menu-set-lore-slot")
    public int iconMenuSetLoreSlot = 12;
    @Comment("Skonfiguruj slot przycisku ustawiania enchantów:")
    @CustomKey("icon-menu-set-enchants-slot")
    public int iconMenuSetEnchantsSlot = 13;
    @Comment("Skonfiguruj slot przycisku ustawiania dozwolonych bloków:")
    @CustomKey("icon-menu-set-blocks-slot")
    public int iconMenuSetBlocksSlot = 14;
    @Comment("Skonfiguruj slot przycisku tworzenia motyki:")
    @CustomKey("icon-menu-create-hoe-slot")
    public int iconMenuCreateHoeSlot = 15;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki:")
    @CustomKey("hoe-creator-menu-size-builder")
    public BukkitMenuBuilder hoeCreatorSizeMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (rozmiar)", 3, new MapBuilder<Integer, ItemStack>()
            .put(11, ItemBuilder.of(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial())
                    .setName("&c&l-1")
                    .toItemStack())
            .put(12, ItemBuilder.of(XMaterial.STICK.parseMaterial())
                    .setName("&9&l{size}x{size}")
                    .toItemStack())
            .put(13, ItemBuilder.of(XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial())
                    .setName("&a&l+1")
                    .toItemStack())
            .put(15, ItemBuilder.of(XMaterial.LIME_DYE.parseMaterial())
                    .setName("&aPotwierdz rozmiar")
                    .toItemStack())
            .build());
    @Comment("Skonfiguruj slot przedmiotu powiększania rozmiaru motyki:")
    @CustomKey(value="icon-menu-decrease-size-slot")
    public int iconMenuDecreaseSizeSlot = 11;
    @Comment("Skonfiguruj slot przedmiotu zmniejszania rozmiaru motyki:")
    @CustomKey(value="icon-menu-increase-size-slot")
    public int iconMenuIncreaseSizeSlot = 13;
    @Comment("Skonfiguruj slot przedmiotu wizualnego rozmiaru motyki:")
    @CustomKey(value="icon-menu-presenter-size-slot")
    public int iconMenuPresenterSizeSlot = 12;
    @Comment("Skonfiguruj slot przedmiotu potwierdzania rozmiaru motyki:")
    @CustomKey(value="icon-menu-submit-size-slot")
    public int iconMenuSubmitSizeSlot = 16;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki (edytor opisu):")
    @CustomKey("hoe-creator-lore-menu-builder")
    public BukkitMenuBuilder hoeCreatorLoreMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (opis)", 6, new MapBuilder<Integer, ItemStack>()
            .put(MenuUtil.countSlot(6, 7), ItemBuilder.of(XMaterial.LIME_DYE.parseMaterial())
                    .setName("&aDodaj nową linie")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 3), ItemBuilder.of(XMaterial.ARROW.parseMaterial())
                    .setName("&cPowrot do poprzedniej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 4), ItemBuilder.of(XMaterial.BARRIER.parseMaterial())
                    .setName("&cPowrot do menu glownego")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 5), ItemBuilder.of(XMaterial.ARROW.parseMaterial())
                    .setName("&aPrzejdz do nastepnej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .build());
    @CustomKey(value="icon-lore-menu-next-page-slot")
    public int iconLoreMenuNextPageSlot = MenuUtil.countSlot(6, 5);
    @CustomKey(value="icon-lore-menu-previous-page-slot")
    public int iconLoreMenuPreviousPageSlot = MenuUtil.countSlot(6, 3);
    @CustomKey(value="icon-lore-menu-close-slot")
    public int iconLoreMenuCloseSlot = MenuUtil.countSlot(6, 4);
    @CustomKey(value="icon-lore-menu-add-line-slot")
    public int iconLoreMenuAddLineSlot = MenuUtil.countSlot(6, 7);

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

