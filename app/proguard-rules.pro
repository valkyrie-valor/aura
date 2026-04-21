# Add project specific ProGuard rules here.
-keep class com.aura.installer.** { *; }

# Tink / security-crypto: errorprone annotations are compile-time only
-dontwarn com.google.errorprone.annotations.**
