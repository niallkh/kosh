-include rules/reown.pro
-include rules/missing.pro
-include rules/secp256k1.pro
-include rules/ktor.pro

-dontobfuscate

-keep class kosh.ui.navigation.Router
-keep class kosh.ui.navigation.stack.StackRouter
-keep class kosh.ui.navigation.listdetails.ListDetailRouter

# Crashlytics
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
