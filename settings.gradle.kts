pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.library") {
                useVersion("8.14.0")
            }
            if (requested.id.id == "org.jetbrains.kotlin.android") {
                useVersion("2.0.0")   // Fuerza la versión que necesitas
            }
        }
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal() // ¡Importante!
        maven { setUrl("https://jitpack.io") }
        // tus repositorios con credenciales
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/LuisCupul04/ExtenRe-patcher")
            credentials {
                username = providers.gradleProperty("gpr.user").getOrElse(System.getenv("GITHUB_ACTOR"))
                password = providers.gradleProperty("gpr.key").getOrElse(System.getenv("GITHUB_TOKEN"))
            }
        }
        maven {
            name = "GitHubPackages2"
            url = uri("https://maven.pkg.github.com/LuisCupul04/smali-RE")
            credentials {
                username = providers.gradleProperty("gpr.user").getOrElse(System.getenv("GITHUB_ACTOR"))
                password = providers.gradleProperty("gpr.key").getOrElse(System.getenv("GITHUB_TOKEN"))
            }
        }
    }
}

plugins {
    id("com.extenre.patches") version "1.0.4.dev-RE"
}

rootProject.name = "extenre-patches"

include(":patches", ":extensions:shared:stub", ":extensions:shared", ":extensions:spoof-signature", ":extensions:spoof-wifi")