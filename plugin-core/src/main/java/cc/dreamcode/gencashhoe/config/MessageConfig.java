package cc.dreamcode.gencashhoe.config;

import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-GenCashHoe (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
public class MessageConfig extends OkaeriConfig {

    @CustomKey("provide-text")
    public BukkitNotice provideText = BukkitNotice.subtitle("&aWprowadz tekst na chat");
    @CustomKey("provide-text-line")
    public BukkitNotice provideTextLine = BukkitNotice.subtitle("&aWprowadz tekst lini na chat");
    @CustomKey("provide-enchant-level")
    public BukkitNotice provideEnchantLevel = BukkitNotice.subtitle("&aWprowadz level enchantu na chat");
    @CustomKey("name-changed")
    public BukkitNotice nameChanged = BukkitNotice.chat("&7Nazwa zostala &azmieniona&7!");
    @CustomKey("lore-line-changed")
    public BukkitNotice loreLineChanged = BukkitNotice.chat("&7Linia opisu zostala &azmieniona&7!");
    @CustomKey("enchant-add")
    public BukkitNotice enchantAdd = BukkitNotice.chat("&7Enchant został &adodany&7!");
    @CustomKey("cant-destroy")
    public BukkitNotice cantDestory = BukkitNotice.chat("&cNie możesz niszczyć tego motyką!");
    @CustomKey("hoe-list")
    public BukkitNotice hoeList = BukkitNotice.chat("&6&lLista motyk&8:");
    @CustomKey("hoe-list-element")
    public BukkitNotice hoeListElement = BukkitNotice.chat("&8- &f{name} &8: &e{size}");

    @CustomKey(value="hoe-do-not-exist")
    public BukkitNotice hoeDoNotExits = BukkitNotice.chat("&cPodana motyka nie istnieje!");
    @CustomKey(value="hoe-create")
    public BukkitNotice hoeCreate = BukkitNotice.chat("&aStworzono motykę!");
    @CustomKey(value="hoe-give")
    public BukkitNotice hoeGive = BukkitNotice.chat("&aNadano motykę dla gracza {target}!");
    @CustomKey(value="hoe-receive")
    public BukkitNotice hoeReceive = BukkitNotice.chat("&aOtrzymano motykę od gracza {player}!");

    @CustomKey(value="first-page-reach")
    public BukkitNotice firstPageReach = BukkitNotice.chat("&cOsiagnieto pierwsza strone!");
    @CustomKey(value="last-page-reach")
    public BukkitNotice lastPageReach = BukkitNotice.chat("&cOsiagnieto ostatnia strone!");

    @CustomKey("command-usage")
    public BukkitNotice usage = BukkitNotice.chat("&7Przyklady uzycia komendy: &c{label}");
    @CustomKey("command-usage-help")
    public BukkitNotice usagePath = BukkitNotice.chat("&f{usage} &8- &7{description}");

    @CustomKey("command-usage-not-found")
    public BukkitNotice usageNotFound = BukkitNotice.chat("&cNie znaleziono pasujacych do kryteriow komendy.");
    @CustomKey("command-path-not-found")
    public BukkitNotice pathNotFound = BukkitNotice.chat("&cTa komenda jest pusta lub nie posiadasz dostepu do niej.");
    @CustomKey("command-no-permission")
    public BukkitNotice noPermission = BukkitNotice.chat("&cNie posiadasz uprawnien.");
    @CustomKey("command-not-player")
    public BukkitNotice notPlayer = BukkitNotice.chat("&cTa komende mozna tylko wykonac z poziomu gracza.");
    @CustomKey("command-not-console")
    public BukkitNotice notConsole = BukkitNotice.chat("&cTa komende mozna tylko wykonac z poziomu konsoli.");
    @CustomKey("command-invalid-format")
    public BukkitNotice invalidFormat = BukkitNotice.chat("&cPodano nieprawidlowy format argumentu komendy. ({input})");

    @CustomKey("player-not-found")
    public BukkitNotice playerNotFound = BukkitNotice.chat("&cPodanego gracza nie znaleziono.");
    @CustomKey("world-not-found")
    public BukkitNotice worldNotFound = BukkitNotice.chat("&cPodanego swiata nie znaleziono.");
    @CustomKey("cannot-do-at-my-self")
    public BukkitNotice cannotDoAtMySelf = BukkitNotice.chat("&cNie mozesz tego zrobic na sobie.");
    @CustomKey("number-is-not-valid")
    public BukkitNotice numberIsNotValid = BukkitNotice.chat("&cPodana liczba nie jest cyfra.");

    @CustomKey("config-reloaded")
    public BukkitNotice reloaded = BukkitNotice.chat("&aPrzeladowano! &7({time})");
    @CustomKey("config-reload-error")
    public BukkitNotice reloadError = BukkitNotice.chat("&cZnaleziono problem w konfiguracji: &6{error}");

}
