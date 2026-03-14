import java.lang.Boolean.TRUE

plugins {
    alias(libs.plugins.android.library)
}

extension {
    name = "extensions/all/misc/signature/spoof-signature.re"
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
}

dependencies {
    implementation(libs.hiddenapi)
}
