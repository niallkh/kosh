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
        commonMain.dependencies {
            api(projects.domain)
            api(projects.eth.abi)

            implementation(libs.arrow)
            implementation(libs.arrow.fx)
            implementation(libs.arrow.resilience)
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
//            implementation(libs.wc2.android.web3wallet)
            implementation(libs.wc2.android.sign)
            implementation(libs.wc2.android.auth)
            implementation(libs.wc2.android)
        }
    }
}

android {
    namespace = "kosh.data.wc2"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
