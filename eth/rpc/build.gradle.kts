import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    explicitApi()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.io.bytestring.unsafe.UnsafeByteStringApi")
                optIn("kotlin.ExperimentalStdlibApi")
            }
        }

        commonMain {
            dependencies {
                implementation(projects.eth.abi)

                implementation(libs.kotlinx.io)
                implementation(libs.bignum)
                implementation(libs.uri)
                implementation(libs.crypto.sha3)
                implementation(libs.ktor.client)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.client.negotiation.json)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.arrow)
                implementation(libs.arrow.resilience)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "kosh.eth.rpc"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
