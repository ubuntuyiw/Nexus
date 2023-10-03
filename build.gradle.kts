buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath(kotlin("gradle-plugin", version = "1.9.10"))


    }

}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false

    id("com.google.dagger.hilt.android") version "2.48" apply false

    kotlin("plugin.serialization") version "1.9.0" apply false

    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false

    id("com.google.protobuf") version "0.9.4" apply false

    id("com.google.firebase.crashlytics") version "2.9.9" apply false

    id("com.google.firebase.firebase-perf") version "1.4.2" apply false

}