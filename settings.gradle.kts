import java.net.URI

rootProject.name = "kosh"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

include(":app")

include(":ui")
include(":ui:resources")
include(":presentation")
include(":domain")
include(":data")
include(":data:wc2")
include(":data:trezor")
include(":data:ledger")
include(":data:web3")
include(":datastore")

include(":eth:rpc")
include(":eth:wallet")
include(":eth:abi")
include(":eth:proposals")

include(":libs:transport")
include(":libs:ipfs")
include(":libs:usb")
include(":libs:ble")
include(":libs:trezor")
include(":libs:ledger")
include(":libs:keystore")
include(":libs:reown")
