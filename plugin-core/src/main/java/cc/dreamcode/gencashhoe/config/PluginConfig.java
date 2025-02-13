package cc.dreamcode.gencashhoe.config;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.utilities.MenuUtil;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.gencashhoe.HoeItem;
import cc.dreamcode.utilities.builder.ListBuilder;
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
    @Comment("Skonfiguruj zablokowane regiony:")
    @CustomKey("blocked-regions")
    public List<String> blockedRegions = Arrays.asList(
            "spawn",
            "pvp"
    );

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki:")
    @CustomKey("hoe-creator-menu-builder")
    public BukkitMenuBuilder hoeCreatorMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki", 3, new MapBuilder<Integer, ItemStack>()
            .put(10, ItemBuilder.of(XMaterial.PAPER.parseItem())
                    .setName("&9&lUstaw rozmiar")
                    .setLore("&8» &7Obecny rozmiar: &a&l{size}x{size}",
                            "",
                            "&8» &7Kliknj &fLPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(11, ItemBuilder.of(XMaterial.OAK_SIGN.parseItem())
                    .setName("&9&lUstaw nazwę")
                    .setLore("&8» &7Obecna nazwa: &f{name}",
                            "",
                            "&8» &7Kliknj &fLPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(12, ItemBuilder.of(XMaterial.BOOK.parseItem())
                    .setName("&9&lUstaw opis")
                    .setLore("", "&8» &7Kliknj &fLPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(13, ItemBuilder.of(XMaterial.ENCHANTED_BOOK.parseItem())
                    .setName("&9&lEnchanty")
                    .setLore("&8» &7Enchanty: &b{enchants}",
                            "",
                            "&8» &7Kliknj &fLPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(14, ItemBuilder.of(XMaterial.GRASS_BLOCK.parseItem())
                    .setName("&9&lBloki")
                    .setLore("&8» &7Bloki: &c{blocks}",
                            "",
                            "&8» &7Kliknj &fLPM&7, aby przejść dalej!")
                    .toItemStack())
            .put(16, ItemBuilder.of(XMaterial.LIME_DYE.parseItem())
                    .setName("&a&lStwórz motykę")
                    .setLore("", "&8» &7Kliknj &fLPM&7, aby stworzyć motykę!")
                    .toItemStack())
            .build());

    @Comment("Skonfiguruj slot przycisku zmiany rozmiaru w kreatorze motyki:")
    @CustomKey("icon-menu-set-size-slot")
    public int iconMenuSetSizeSlot = 10;
    @Comment("Skonfiguruj slot przycisku zmiany nazwy w kreatorze motyki:")
    @CustomKey("icon-menu-set-name-slot")
    public int iconMenuSetNameSlot = 11;
    @Comment("Skonfiguruj slot przycisku zmiany opisu w kreatorze motyki:")
    @CustomKey("icon-menu-set-lore-slot")
    public int iconMenuSetLoreSlot = 12;
    @Comment("Skonfiguruj slot przycisku zmiany enchantów w kreatorze motyki:")
    @CustomKey("icon-menu-set-enchants-slot")
    public int iconMenuSetEnchantsSlot = 13;
    @Comment("Skonfiguruj slot przycisku zmiany dozwolonych bloków w kreatorze motyki:")
    @CustomKey("icon-menu-set-blocks-slot")
    public int iconMenuSetBlocksSlot = 14;
    @Comment("Skonfiguruj slot przycisku tworzenia motyki w kreatorze motyki:")
    @CustomKey("icon-menu-create-hoe-slot")
    public int iconMenuCreateHoeSlot = 16;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki (rozmiar):")
    @CustomKey("hoe-creator-menu-size-builder")
    public BukkitMenuBuilder hoeCreatorSizeMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (rozmiar)", 3, new MapBuilder<Integer, ItemStack>()
            .put(11, ItemBuilder.of(XMaterial.RED_STAINED_GLASS_PANE.parseItem())
                    .setName("&c&l-1")
                    .toItemStack())
            .put(12, ItemBuilder.of(XMaterial.STICK.parseItem())
                    .setName("&9&l{size}x{size}")
                    .toItemStack())
            .put(13, ItemBuilder.of(XMaterial.GREEN_STAINED_GLASS_PANE.parseItem())
                    .setName("&a&l+1")
                    .toItemStack())
            .put(15, ItemBuilder.of(XMaterial.LIME_DYE.parseItem())
                    .setName("&aPotwierdz rozmiar")
                    .toItemStack())
            .build());
    @Comment("Skonfiguruj slot przedmiotu powiększania rozmiaru motyki w kreatorze motyki (rozmiar):")
    @CustomKey(value="icon-menu-decrease-size-slot")
    public int iconMenuDecreaseSizeSlot = 11;
    @Comment("Skonfiguruj slot przedmiotu zmniejszania rozmiaru motyki w kreatorze motyki (rozmiar):")
    @CustomKey(value="icon-menu-increase-size-slot")
    public int iconMenuIncreaseSizeSlot = 13;
    @Comment("Skonfiguruj slot przedmiotu wizualnego rozmiaru motyki w kreatorze motyki (rozmiar):")
    @CustomKey(value="icon-menu-presenter-size-slot")
    public int iconMenuPresenterSizeSlot = 12;
    @Comment("Skonfiguruj slot przedmiotu potwierdzania rozmiaru motyki w kreatorze motyki (rozmiar):")
    @CustomKey(value="icon-menu-submit-size-slot")
    public int iconMenuSubmitSizeSlot = 15;

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki (opisu):")
    @CustomKey("hoe-creator-lore-menu-builder")
    public BukkitMenuBuilder hoeCreatorLoreMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (opis)", 6, new MapBuilder<Integer, ItemStack>()
            .put(MenuUtil.countSlot(6, 7), ItemBuilder.of(XMaterial.LIME_DYE.parseItem())
                    .setName("&aDodaj nową linie")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 3), ItemBuilder.of(XMaterial.ARROW.parseItem())
                    .setName("&cPowrot do poprzedniej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 4), ItemBuilder.of(XMaterial.BARRIER.parseItem())
                    .setName("&cPowrot do menu glownego")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 5), ItemBuilder.of(XMaterial.ARROW.parseItem())
                    .setName("&aPrzejdz do nastepnej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .build());
    @Comment("Skonfiguruj ignorowane sloty w menu dla kreatora motyki (opis):")
    @CustomKey(value="icon-lore-menu-ignore-slots")
    public List<Integer> iconLoreMenuIgnoreSlots = ListBuilder.of(MenuUtil.countSlot(1, 1), MenuUtil.countSlot(1, 2), MenuUtil.countSlot(1, 3), MenuUtil.countSlot(1, 4), MenuUtil.countSlot(1, 5), MenuUtil.countSlot(1, 6), MenuUtil.countSlot(1, 7), MenuUtil.countSlot(1, 8), MenuUtil.countSlot(1, 9), MenuUtil.countSlot(2, 1), MenuUtil.countSlot(2, 9), MenuUtil.countSlot(3, 1), MenuUtil.countSlot(3, 9), MenuUtil.countSlot(4, 1), MenuUtil.countSlot(4, 9), MenuUtil.countSlot(5, 1), MenuUtil.countSlot(5, 9), MenuUtil.countSlot(6, 1), MenuUtil.countSlot(6, 2), MenuUtil.countSlot(6, 3), MenuUtil.countSlot(6, 4), MenuUtil.countSlot(6, 5), MenuUtil.countSlot(6, 6), MenuUtil.countSlot(6, 7), MenuUtil.countSlot(6, 8), MenuUtil.countSlot(6, 9));
    @Comment("Skonfiguruj przykładowy item w edytorze opisu:")
    @CustomKey(value="icon-lore-menu-template")
    public ItemStack iconLoreMenuTemplate = ItemBuilder.of(XMaterial.PAPER.parseItem())
            .setName("&bWybierz linie")
            .setLore("&8» &7Linia: &r{line}", "", "&8» &7Kliknij &fLPM&7, aby edytować linię!", "&8» &7Kliknij &fSHIFT + LPM&7, aby usunąć linię!")
            .toItemStack();
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk nastepnej strony w kreatorze motyki (opis):")
    @CustomKey(value="icon-lore-menu-next-page-slot")
    public int iconLoreMenuNextPageSlot = MenuUtil.countSlot(6, 5);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk poprzedniej strony w kreatorze motyki (opis):")
    @CustomKey(value="icon-lore-menu-previous-page-slot")
    public int iconLoreMenuPreviousPageSlot = MenuUtil.countSlot(6, 3);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk zamkniecia menu w kreatorze motyki (opis):")
    @CustomKey(value="icon-lore-menu-close-slot")
    public int iconLoreMenuCloseSlot = MenuUtil.countSlot(6, 4);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk dodania nowej lini w kreatorze motyki (opis):")
    @CustomKey(value="icon-lore-menu-add-line-slot")
    public int iconLoreMenuAddLineSlot = MenuUtil.countSlot(6, 7);

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki (enchanty):")
    @CustomKey("hoe-creator-enchant-menu-builder")
    public BukkitMenuBuilder hoeCreatorEnchantMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (enchanty)", 6, new MapBuilder<Integer, ItemStack>()
            .put(MenuUtil.countSlot(6, 4), ItemBuilder.of(XMaterial.ARROW.parseItem())
                    .setName("&cPowrot do poprzedniej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 5), ItemBuilder.of(XMaterial.BARRIER.parseItem())
                    .setName("&cPowrot do menu glownego")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 6), ItemBuilder.of(XMaterial.ARROW.parseItem())
                    .setName("&aPrzejdz do nastepnej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .build());
    @Comment("Skonfiguruj ignorowane sloty w menu dla kreatora motyki (enchanty):")
    @CustomKey(value="icon-enchant-menu-ignore-slots")
    public List<Integer> iconEnchantMenuIgnoreSlots = ListBuilder.of(MenuUtil.countSlot(1, 1), MenuUtil.countSlot(1, 2), MenuUtil.countSlot(1, 3), MenuUtil.countSlot(1, 4), MenuUtil.countSlot(1, 5), MenuUtil.countSlot(1, 6), MenuUtil.countSlot(1, 7), MenuUtil.countSlot(1, 8), MenuUtil.countSlot(1, 9), MenuUtil.countSlot(2, 1), MenuUtil.countSlot(2, 9), MenuUtil.countSlot(3, 1), MenuUtil.countSlot(3, 9), MenuUtil.countSlot(4, 1), MenuUtil.countSlot(4, 9), MenuUtil.countSlot(5, 1), MenuUtil.countSlot(5, 9), MenuUtil.countSlot(6, 1), MenuUtil.countSlot(6, 2), MenuUtil.countSlot(6, 3), MenuUtil.countSlot(6, 4), MenuUtil.countSlot(6, 5), MenuUtil.countSlot(6, 6), MenuUtil.countSlot(6, 7), MenuUtil.countSlot(6, 8), MenuUtil.countSlot(6, 9));
    @Comment("Skonfiguruj przykładowy item w edytorze enchantów:")
    @CustomKey(value="icon-enchant-menu-template")
    public ItemStack iconEnchantMenuTemplate = ItemBuilder.of(XMaterial.ENCHANTED_BOOK.parseItem())
            .setName("&bWybierz enchant")
            .setLore("&8» &7Enchant: &9{name}", "", "&8» &7Kliknij &fLPM&7, aby dodać ten enchant")
            .toItemStack();
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk nastepnej strony w kreatorze motyki (enchanty):")
    @CustomKey(value="icon-enchant-menu-next-page-slot")
    public int iconEnchantMenuNextPageSlot = MenuUtil.countSlot(6, 6);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk poprzedniej strony w kreatorze motyki (enchanty):")
    @CustomKey(value="icon-enchant-menu-previous-page-slot")
    public int iconEnchantMenuPreviousPageSlot = MenuUtil.countSlot(6, 4);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk zamkniecia menu w kreatorze motyki (enchanty):")
    @CustomKey(value="icon-enchant-menu-close-slot")
    public int iconEnchantMenuCloseSlot = MenuUtil.countSlot(6, 5);

    @Comment
    @Comment("Skonfiguruj menu dla kreatora motyki (bloki):")
    @CustomKey("hoe-creator-breakables-menu-builder")
    public BukkitMenuBuilder hoeCreatorBreakablesMenuBuilder = new BukkitMenuBuilder("&8Tworzenie motyki (bloki)", 6, new MapBuilder<Integer, ItemStack>()
            .put(MenuUtil.countSlot(6, 4), ItemBuilder.of(XMaterial.ARROW.parseItem())
                    .setName("&cPowrot do poprzedniej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 5), ItemBuilder.of(XMaterial.BARRIER.parseItem())
                    .setName("&cPowrot do menu glownego")
                    .toItemStack())
            .put(MenuUtil.countSlot(6, 6), ItemBuilder.of(XMaterial.ARROW.parseItem())
                    .setName("&aPrzejdz do nastepnej strony")
                    .setLore("", "&8» &7Kliknij, aby zmienic strone!")
                    .toItemStack())
            .build());
    @Comment("Skonfiguruj ignorowane sloty w menu dla kreatora motyki (bloki):")
    @CustomKey(value="icon-breakables-menu-ignore-slots")
    public List<Integer> iconBreakablesMenuIgnoreSlots = ListBuilder.of(MenuUtil.countSlot(1, 1), MenuUtil.countSlot(1, 2), MenuUtil.countSlot(1, 3), MenuUtil.countSlot(1, 4), MenuUtil.countSlot(1, 5), MenuUtil.countSlot(1, 6), MenuUtil.countSlot(1, 7), MenuUtil.countSlot(1, 8), MenuUtil.countSlot(1, 9), MenuUtil.countSlot(2, 1), MenuUtil.countSlot(2, 9), MenuUtil.countSlot(3, 1), MenuUtil.countSlot(3, 9), MenuUtil.countSlot(4, 1), MenuUtil.countSlot(4, 9), MenuUtil.countSlot(5, 1), MenuUtil.countSlot(5, 9), MenuUtil.countSlot(6, 1), MenuUtil.countSlot(6, 2), MenuUtil.countSlot(6, 3), MenuUtil.countSlot(6, 4), MenuUtil.countSlot(6, 5), MenuUtil.countSlot(6, 6), MenuUtil.countSlot(6, 7), MenuUtil.countSlot(6, 8), MenuUtil.countSlot(6, 9));
    @Comment("Skonfiguruj item w edytorze bloków:")
    @CustomKey(value="icon-breakables-menu-template")
    public ItemStack iconBreakablesMenuTemplate = ItemBuilder.of(XMaterial.DIRT.parseItem())
            .setName("&bWybierz blok")
            .setLore("&8» &7Blok: &9{name}", "", "&8» &7Kliknij &fLPM&7, aby dodać ten blok")
            .toItemStack();
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk nastepnej strony w kreatorze motyki (bloki):")
    @CustomKey(value="icon-breakables-menu-next-page-slot")
    public int iconBreakablesMenuNextPageSlot = MenuUtil.countSlot(6, 6);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk poprzedniej strony w kreatorze motyki (bloki):")
    @CustomKey(value="icon-breakables-menu-previous-page-slot")
    public int iconBreakablesMenuPreviousPageSlot = MenuUtil.countSlot(6, 4);
    @Comment("Skonfiguruj slot, pod ktorym znajduje sie przycisk zamkniecia menu w kreatorze motyki (bloki):")
    @CustomKey(value="icon-breakables-menu-close-slot")
    public int iconBreakablesMenuCloseSlot = MenuUtil.countSlot(6, 5);

    @Comment
    @Comment("Skonfiguruj przykładowy item motyki:")
    @CustomKey("default-hoe-itemstack")
    public ItemStack templateHoeItemStack = ItemBuilder.of(XMaterial.NETHERITE_HOE.parseItem()).setName("&6&lMotyka {size}x{size}").setLore(Arrays.asList("", "&8» &7Ta motyka kopie: &f{size}x{size}")).addEnchant(Enchantment.DURABILITY, 4).toItemStack();

    @Comment
    @Comment("Skonfiguruj przykładowy opis motyki:")
    @CustomKey("default-hoe-lore")
    public List<String> templateHoeLore = Arrays.asList("", "&8» &7Ta motyka kopie: &f{size}x{size}");

    @Comment
    @Comment("Skonfiguruj motyki:")
    @CustomKey("hoes")
    public List<HoeItem> hoes = Arrays.asList(
            new HoeItem(
                    1,
                    ItemBuilder.of(XMaterial.NETHERITE_HOE.parseItem()).setName("&6&lMotyka 2x2").addEnchant(Enchantment.DURABILITY, 4).toItemStack(),
                    true,
                    2,
                    Arrays.asList()
            )
    );
}

