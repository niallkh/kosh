import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.libs.usb)
                api(projects.eth.abi)
                api(projects.eth.wallet)
                api(projects.eth.proposals.eip55)

                implementation(libs.bignum)
                implementation(libs.okio)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.arrow)
                implementation(libs.arrow.fx)
                implementation(libs.arrow.resilience)
                implementation(libs.kermit)
                implementation(libs.kotlinx.serialization.core)
            }
        }
    }
}

android {
    namespace = "kosh.libs.ledger"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}