
import org.jetbrains.compose.internal.utils.getLocalProperty
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    explicitApiWarning()

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
            baseName = "App"
            isStatic = true

            export(projects.domain)
            export(projects.libs.reown)

            export(libs.arrow)
            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.essenty.lifecycle)
            export(libs.essenty.state.keeper)
            export(libs.essenty.instance.keeper)
            export(libs.essenty.back.handler)
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                optIn("kotlinx.cinterop.BetaInteropApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.io.bytestring.unsafe.UnsafeByteStringApi")
                optIn("com.arkivanov.decompose.ExperimentalDecomposeApi")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.startup)
            implementation(libs.androidx.splash)
            implementation(libs.androidx.work)
            implementation(libs.androidx.lifecycle.service)
            implementation(libs.androidx.lifecycle.process)
            implementation(libs.decompose.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.secp256k1.android)
            implementation(libs.okhttp)
            implementation(libs.okhttp.logging)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)

            api(projects.domain)
            api(projects.libs.reown)
            implementation(projects.ui)
            implementation(projects.presentation)
            implementation(projects.data)
            implementation(projects.data.trezor)
            implementation(projects.data.ledger)
            implementation(projects.data.web3)
            implementation(projects.data.reown)
            implementation(projects.datastore)
            implementation(projects.eth.wallet)
            implementation(projects.eth.abi)
            implementation(projects.libs.trezor)
            implementation(projects.libs.ledger)
            implementation(projects.libs.usb)
            implementation(projects.libs.ble)
            implementation(projects.libs.ipfs)
            implementation(projects.libs.keystore)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.cbor)
            implementation(libs.ktor.client)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.encoding)

            api(libs.arrow)
            api(libs.decompose)
            api(libs.essenty.lifecycle)
            api(libs.essenty.lifecycle)
            api(libs.essenty.state.keeper)
            api(libs.essenty.instance.keeper)
            api(libs.essenty.back.handler)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
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
        versionCode = 7
        versionName = "0.0.3"
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
                "REOWN_PROJECT",
                "\"${getLocalProperty("reown.project")}\""
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
                "REOWN_PROJECT",
                "\"${getLocalProperty("reown.project")}\""
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
