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
include(":database")

include(":eth:rpc")
include(":eth:wallet")
include(":eth:abi")
include(":eth:proposals")
include(":eth:proposals:erc20")
include(":eth:proposals:eip55")
include(":eth:proposals:erc165")
include(":eth:proposals:erc721")
include(":eth:proposals:erc1155")
include(":eth:proposals:multicall")

include(":libs:ipfs")
include(":libs:usb")
include(":libs:trezor")
include(":libs:ledger")
include(":libs:keystore")
