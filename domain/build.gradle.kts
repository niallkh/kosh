import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
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
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        commonMain.dependencies {
            api(compose.runtime)

            implementation(projects.eth.abi)
            implementation(projects.eth.proposals.eip55)
            implementation(projects.eth.proposals.erc20)
            implementation(projects.eth.proposals.erc721)
            implementation(projects.eth.proposals.erc1155)

            api(libs.kotlinx.coroutines)
            api(libs.kotlinx.serialization.core)
            api(libs.kermit)
            api(libs.arrow)
            api(libs.arrow.serialization)
            api(libs.arrow.resilience)
            api(libs.arrow.fx)
            api(libs.arrow.optics)
            api(libs.bignum)
            api(libs.kotlinx.collections.immutable)

            api(libs.kotlinx.datetime)
            api(libs.uuid)
            api(libs.uri)

            implementation(libs.okio)
        }

        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.arrow.optics.ksp)
}

tasks.withType<KotlinCompile> {
    dependsOn("kspCommonMainKotlinMetadata")
}

android {
    namespace = "kosh.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}