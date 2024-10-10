plugins {
    kotlin("jvm") version "2.0.10"
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = "ca.uwaterloo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()    // Maven Central repository
    google()          // Google's Maven repository for AndroidX artifacts
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }  // JetBrains Compose repository
}


dependencies {
    implementation(libs.datetime)
    implementation(compose.desktop.currentOs)

    // implementation("androidx.navigation:navigation-compose:2.8.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

