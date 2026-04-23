group = "app.morphe"
version = rootProject.properties["version"] as? String ?: "0.0.0"

patches {
    about {
        name = "ExtenRe Patches"
        description = "Patches for ExtenRe"
        source = "git@github.com:luiscupul04/extenre-patches.git"
        author = "LuisCupul04 (Luis Cupul 04)"
        license = "GNU General Public License v3.0"
        contact = "LuisCupul04@outlook.com"
        website = "https://github.com/LuisCupul04/ExtenRe-patches"
    }
}

dependencies {
    implementation(libs.gson)
    implementation(libs.morphe.patcher)   // Asegúrate de que esta dependencia existe en libs.versions.toml
}

tasks {
    // ✅ Cambio crucial: incluir las extensiones (.mpe) dentro del .mpp
    jar {
        archiveExtension.set("mpp")
        exclude("app/morphe/generator")
        // 👇 Esto empaqueta todos los .mpe generados en la carpeta extensions/
        from(rootProject.rootDir) {
            include("extensions/**/*.mpe")
        }
    }

    // JAR estándar para publicación como biblioteca (no es necesario para el .mpp, pero lo dejo)
    register<Jar>("libraryJar") {
        archiveClassifier.set("")
        from(sourceSets.main.get().output)
        exclude("app/morphe/generator")
    }

    // ✅ Cambio de nombre y clase principal (para generar patches-list.json)
    register<JavaExec>("generatePatchesList") {
        description = "Generate patches list JSON"
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath
        // Asegúrate de que esta clase exista en tu proyecto. Si usas el generador de Morphe, debería ser:
        mainClass.set("app.morphe.generator.MainKt")
        // Alternativa si tienes una clase diferente: mainClass.set("app.morphe.util.PatchListGeneratorKt")
    }

    // Configurar la tarea sourcesJar existente
    named<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
    }

    // Tarea usada por gradle-semantic-release-plugin
    publish {
        dependsOn("generatePatchesList")   // ✅ Cambiado para que use la nueva tarea
    }
}

kotlin {
    compilerOptions {
        // ✅ Parámetro recomendado para Morphe (ya no se usa -Xcontext-receivers)
        freeCompilerArgs = listOf("-Xcontext-parameters")
    }
}

publishing {
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
    publications {
        create<MavenPublication>("patches") {
            artifactId = "extenre-patches-library"
            artifact(tasks["libraryJar"])
            artifact(tasks["sourcesJar"])
            pom {
                name.set("ExtenRe Patches")
                description.set("Patches for ExtenRe")
                url.set("https://github.com/LuisCupul04/ExtenRe-Patches")
                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("LuisCupul04")
                        name.set("Luis Cupul")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/LuisCupul04/ExtenRe-Patches.git")
                    developerConnection.set("scm:git:git@github.com:LuisCupul04/ExtenRe-Patches.git")
                    url.set("https://github.com/LuisCupul04/ExtenRe-Patches")
                }
            }
        }
    }
}
