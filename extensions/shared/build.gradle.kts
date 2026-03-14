plugins {
    id("com.android.application")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.extenre.extension"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.extenre.extension.shared"
        minSdk = 24
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            // Para evitar advertencias de firma, puedes usar el signingConfig de debug
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    compileOnly(libs.annotation)
    compileOnly(libs.preference)

    implementation(libs.collections4)
    implementation(libs.gson)
    implementation(libs.lang3)
    implementation(libs.okhttp3)
    implementation(libs.protobuf.javalite)

    //noinspection UseTomlInstead
    implementation("com.github.ynab:J2V8:6.2.1-16kb.2@aar")

    coreLibraryDesugaring(libs.desugar.jdk.libs)
    compileOnly(project(":extensions:shared:stub"))

    // Opcional, pero recomendado para multidex
    implementation("androidx.multidex:multidex:2.0.1")
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}