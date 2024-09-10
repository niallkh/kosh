import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
            implementation(libs.okio)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.arrow)
            implementation(libs.arrow.fx)
            implementation(libs.arrow.resilience)
            implementation(libs.kermit)
        }

        androidMain.dependencies {
        }

        jvmMain.dependencies {
            implementation(libs.usb4java)
            implementation(libs.usb4java.javax)
        }
    }
}

android {
    namespace = "kosh.libs.usb"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
