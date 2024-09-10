import org.jetbrains.compose.internal.utils.getLocalProperty
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Kosh"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.startup)
            implementation(libs.androidx.splash)
            implementation(libs.decompose.android)
            implementation(libs.ktor.client.okhttp)
//            implementation(libs.wc2.android.web3wallet)
            implementation(libs.wc2.android)
            implementation(libs.secp256k1.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)

            implementation(projects.ui)
            implementation(projects.presentation)
            implementation(projects.domain)
            implementation(projects.libs.ipfs)
            implementation(projects.data)
            implementation(projects.data.trezor)
            implementation(projects.data.ledger)
            implementation(projects.data.web3)
            implementation(projects.data.wc2)
            implementation(projects.datastore)
            implementation(projects.libs.trezor)
            implementation(projects.libs.ledger)
            implementation(projects.libs.usb)
            implementation(projects.libs.keystore)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.cbor)
            implementation(libs.ktor.client)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.encoding)
        }
    }
}

android {
    namespace = "kosh.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "eth.kosh.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.compileSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        debug {

            buildConfigField(
                "String",
                "GROVE_KEY",
                "\"${getLocalProperty("grove.key")}\""
            )
            buildConfigField(
                "String",
                "WC_PROJECT",
                "\"${getLocalProperty("wc.project")}\""
            )

            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
        }

        release {
            buildConfigField(
                "String",
                "GROVE_KEY",
                "\"${getLocalProperty("grove.key")}\""
            )
            buildConfigField(
                "String",
                "WC_PROJECT",
                "\"${getLocalProperty("wc.project")}\""
            )

            isDebuggable = false
            isMinifyEnabled = true

            signingConfig = signingConfigs.getByName("debug")

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
