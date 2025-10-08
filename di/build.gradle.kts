import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "di"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)


            implementation(projects.core.datastore)
            implementation(projects.core.network)

            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.shared)

            implementation(projects.feature.auth)
            implementation(projects.feature.base)
            implementation(projects.feature.validation)
            implementation(projects.feature.user.home)
            implementation(projects.feature.user.search)
            implementation(projects.feature.user.stadiumDetail)
            implementation(projects.feature.user.more)
        }
    }
}

android {
    namespace = "dev.soul.di"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}