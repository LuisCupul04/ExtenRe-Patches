plugins {
    id("com.android.application")
}

android {
    namespace = "com.extenre.extension"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.extenre.extension.spoofwifi"
        minSdk = 21
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation("androidx.multidex:multidex:2.0.1")
}