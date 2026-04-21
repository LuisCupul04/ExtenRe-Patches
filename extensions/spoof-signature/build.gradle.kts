plugins {
    id("com.android.application")
}

extension {
    name = "extensions/all/misc/signature/spoof-signature.mpe"
}

android {
    namespace = "app.morphe.extension"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = true   // ← Cambiado de TRUE a true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.hiddenapi)
}
