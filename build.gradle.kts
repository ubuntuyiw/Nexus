buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}
plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    id("com.google.dagger.hilt.android") version "2.45" apply false

    kotlin("plugin.serialization") version "1.8.10" apply false
}