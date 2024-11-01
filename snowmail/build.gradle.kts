import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.0.10"
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

group = "ca.uwaterloo"
version = "0.10"

repositories {
    mavenCentral()    // Maven Central repository
    google()          // Google's Maven repository for AndroidX artifacts
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }  // JetBrains Compose repository
}

dependencies {
    implementation(libs.datetime)
    implementation(compose.desktop.currentOs)
    implementation(libs.json)
    implementation(libs.ktor.client)
    testImplementation(kotlin("test"))

    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgres)
    implementation(libs.supabase.realtime)
    implementation(libs.supabase.auth)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "ca.uwaterloo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ca.uwaterloo"
            packageVersion = "1.0.0"
        }
    }
}


