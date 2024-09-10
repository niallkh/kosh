import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.wire)
}

kotlin {
    explicitApi()
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.okio)
                implementation(libs.bignum)
                implementation(libs.crypto.sha3)
                implementation(libs.ktor.client)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.client.negotiation.json)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.arrow)
                implementation(libs.wire)
                implementation(libs.uri)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        jvmTest {
            dependencies {
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging)
            }
        }
    }
}

android {
    namespace = "kosh.libs.ipfs"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

wire {
    kotlin {}
}
