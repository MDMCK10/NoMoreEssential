plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "xyz.mdmck10"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }
}

tasks {
    runServer {
        systemProperty("Paper.IgnoreJavaVersion", true)
        minecraftVersion("1.8.8")
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }

    shadowJar {
        relocate("com.github.retrooper.packetevents", "xyz.mdmck10.NoMoreEssential.packetevents.api")
        relocate("io.github.retrooper.packetevents", "xyz.mdmck10.NoMoreEssential.packetevents.impl")
        minimize()
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation("com.github.retrooper:packetevents-spigot:2.4.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}