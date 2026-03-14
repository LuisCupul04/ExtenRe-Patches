import java.lang.Boolean.TRUE

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

extension {
    name = "extensions/all/connectivity/wifi/spoof/spoof-wifi.re"
}

android {
    namespace = "com.extenre.extension"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    buildTypes {
        release {
            isMinifyEnabled = TRUE
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":extensions:shared"))
    compileOnly(libs.annotation)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}