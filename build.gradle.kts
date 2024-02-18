buildscript {
    val agp_version by extra("8.2.2")
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.android.library") version "8.1.1" apply false
}