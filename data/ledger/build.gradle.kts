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

            implementation(projects.libs.ledger)
            implementation(projects.eth.abi)
            implementation(projects.eth.proposals.erc20)
            implementation(projects.eth.proposals.erc721)
            implementation(projects.eth.proposals.erc1155)
            implementation(projects.eth.wallet)

            implementation(libs.okio)
            implementation(libs.arrow)
            implementation(libs.arrow.fx)
            implementation(libs.arrow.resilience)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.json.okio)
            implementation(libs.crypto.sha2)
            implementation(libs.ktor.client)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "kosh.data.ledger"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
