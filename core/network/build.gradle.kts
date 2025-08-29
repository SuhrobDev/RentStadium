import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "network"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.android.client)
        }
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.kotlinx.serialization)

            implementation(libs.koin.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.resources)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.mock)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)

            implementation(projects.shared)
            implementation(projects.core.datastore)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin.client)
        }
    }
}

android {
    namespace = "dev.soul.network"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Use debug variant for debug builds
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")

    // Use no-op variant for release builds
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
}
