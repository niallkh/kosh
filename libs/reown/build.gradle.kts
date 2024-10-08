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
        all {
            languageSettings {
                optIn("kotlinx.io.bytestring.unsafe.UnsafeByteStringApi")
                optIn("kotlin.ExperimentalStdlibApi")
            }
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.io)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.arrow)
            implementation(libs.arrow.fx)
            implementation(libs.arrow.resilience)
            implementation(libs.kermit)
        }

        androidMain.dependencies {
            implementation(libs.reown.android.core)
            implementation(libs.reown.wallet.kit)
            implementation(libs.reown.sign)
        }
    }
}

android {
    namespace = "kosh.libs.reown"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
