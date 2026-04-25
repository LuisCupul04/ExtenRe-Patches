import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// No se aplican plugins de Android

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.annotation)
    compileOnly(libs.preference)

    implementation(libs.collections4)
    implementation(libs.gson)
    implementation(libs.lang3)
    implementation(libs.okhttp3)
    // implementation(libs.protobuf.javalite)  // comentado por ahora

    implementation("com.github.ynab:J2V8:6.2.1-16kb.2@aar")

    compileOnly(project(":extensions:shared:stub"))
}
