import java.lang.Boolean.TRUE

plugins {
    alias(libs.plugins.android.library)
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    compileOnly(libs.annotation)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
