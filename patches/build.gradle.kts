import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.lang.Boolean.TRUE  // si necesitas TRUE

plugins {
    id("com.android.library")                // <-- Agregar
    id("org.jetbrains.kotlin.android")        // <-- Agregar (reemplaza a kotlin("jvm"))
    id("maven-publish")
    // Elimina kotlin("jvm") si lo tenías
}

group = "com.extenre"

patches {
    about {
        name = "ExtenRe Patches"
        author = "LuisCupul04"
        license = "GNU General Public License v3.0"
    }
}

android {
    namespace = "com.extenre.patches"          // Ajusta según tu paquete
    compileSdk = 35                             // Misma versión que en shared
    defaultConfig {
        minSdk = 24                              // Misma que shared
    }
    // No necesitas buildTypes si no compilas variantes, pero puedes dejarlo vacío
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"                          // Equivalente a compilerOptions
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

dependencies {
    implementation(project(":extensions:shared"))   // Dependencia local
    implementation("com.extenre:extenre-patcher:20.0.1.RE")
    implementation(libs.gson)
}

tasks {
    jar {
        archiveExtension.set("EXRE")
        exclude("com/extenre/generator")
    }
    register<JavaExec>("generatePatchesFiles") {
        description = "Generate patches files"
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("com.extenre.generator.MainKt")
    }
    publish {
        dependsOn("generatePatchesFiles")
    }
}

// El bloque kotlin { compilerOptions } se reemplaza por android.kotlinOptions
// Por tanto, elimina el bloque kotlin que tenías abajo.

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["release"])           // Publica la variante release
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/LuisCupul04/ExtenRe-patches")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}