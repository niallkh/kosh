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
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        commonMain.dependencies {
            api(projects.domain)

            api(projects.eth.rpc)
            api(projects.libs.ipfs)
            api(projects.eth.wallet)
            api(projects.eth.proposals.multicall)
            api(projects.eth.proposals.erc20)
            api(projects.eth.proposals.erc721)
            api(projects.eth.proposals.erc1155)
            api(projects.eth.proposals.erc165)

            implementation(libs.arrow)
            implementation(libs.arrow.fx)
            implementation(libs.arrow.resilience)
            implementation(libs.arrow.collectors)
            implementation(libs.ktor.client)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.json.okio)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "kosh.data.web3"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
