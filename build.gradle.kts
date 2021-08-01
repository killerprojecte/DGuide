plugins {
    java
    id("io.izzel.taboolib") version "1.12"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

group = "com.xbaimiao.taboolib"

taboolib {
    description {
        contributors {
            name("小白").description("TabooLib Developer")
        }
        dependencies {
            name("AuthMe")
        }
    }
    install("common")
    install("platform-bukkit")
    install("module-nms-util")
    install("module-nms")
    install("module-configuration")
//    Bungee
//    install("platform-bungee")
    version = "6.0.0-pre18"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}