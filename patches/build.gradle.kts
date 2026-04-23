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
    implementation(libs.morphe.patcher)
}

tasks {
    // Bundle de parches (.mpp) que incluye las extensiones .mpe
    jar {
        archiveExtension.set("mpp")
        exclude("app/morphe/generator")
        // Incluye todos los archivos .mpe generados en la carpeta extensions/
        from(rootProject.rootDir) {
            include("extensions/**/*.mpe")
        }
    }

    // JAR estándar para publicación como biblioteca
    register<Jar>("libraryJar") {
        archiveClassifier.set("")
        from(sourceSets.main.get().output)
        exclude("app/morphe/generator")
    }

    // Genera patches-list.json
    register<JavaExec>("generatePatchesList") {
        description = "Generate patches list JSON"
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath
        // Asegúrate de que esta clase exista
        mainClass.set("app.morphe.generator.MainKt")
    }

    named<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
    }

    publish {
        dependsOn("generatePatchesList")
    }
}

kotlin {
    compilerOptions {
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
