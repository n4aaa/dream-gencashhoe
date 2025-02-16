import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://repo.codemc.io/repository/maven-public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    // -- spigot api -- (base)
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")

    // -- dream-platform --
    implementation("cc.dreamcode.platform:core:1.12.9")
    implementation("cc.dreamcode.platform:bukkit:1.12.9")
    implementation("cc.dreamcode.platform:bukkit-hook:1.12.9")
    implementation("cc.dreamcode.platform:bukkit-config:1.12.9")
    implementation("cc.dreamcode.platform:dream-command:1.12.9")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.5.1")
    implementation("cc.dreamcode:utilities-bukkit-adventure:1.5.1")

    // -- dream-notice --
    implementation("cc.dreamcode.notice:core:1.6.6")
    implementation("cc.dreamcode.notice:bukkit-adventure:1.6.6")
    implementation("cc.dreamcode.notice:bukkit-adventure-serializer:1.6.6")

    // -- notice mini-messages --
    implementation("net.kyori:adventure-text-minimessage:4.17.0")

    // -- dream-command --
    implementation("cc.dreamcode.command:core:2.1.3")
    implementation("cc.dreamcode.command:bukkit:2.1.3")

    // -- dream-menu --
    implementation("cc.dreamcode.menu:core:1.3.9")
    implementation("cc.dreamcode.menu:bukkit-adventure:1.3.7")
    implementation("cc.dreamcode.menu:bukkit-adventure-serializer:1.3.7")

    // -- configs--
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.2")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.2")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:5.0.2")

    // -- injector --
    implementation("eu.okaeri:okaeri-injector:2.1.0")

    // -- placeholders --
    implementation("eu.okaeri:okaeri-placeholders-core:5.0.1")

    // -- tasker (easy sync/async scheduler) --
    implementation("eu.okaeri:okaeri-tasker-bukkit:2.1.0-beta.3")

    // -- Multi-Version Items helper --
    implementation("com.github.cryptomorin:XSeries:13.0.0")

    // -- WorldGuard api --
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.0")
}

tasks.withType<ShadowJar> {

    archiveFileName.set("Dream-GenCashHoe-${project.version}.jar")

    relocate("com.cryptomorin", "cc.dreamcode.gencashhoe.libs.com.cryptomorin")
    relocate("eu.okaeri", "cc.dreamcode.gencashhoe.libs.eu.okaeri")
    relocate("net.kyori", "cc.dreamcode.gencashhoe.libs.net.kyori")

    relocate("cc.dreamcode.platform", "cc.dreamcode.gencashhoe.libs.cc.dreamcode.platform")
    relocate("cc.dreamcode.utilities", "cc.dreamcode.gencashhoe.libs.cc.dreamcode.utilities")
    relocate("cc.dreamcode.menu", "cc.dreamcode.gencashhoe.libs.cc.dreamcode.menu")
    relocate("cc.dreamcode.command", "cc.dreamcode.gencashhoe.libs.cc.dreamcode.command")
    relocate("cc.dreamcode.notice", "cc.dreamcode.gencashhoe.libs.cc.dreamcode.notice")
}