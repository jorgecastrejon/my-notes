plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.squareup.sqldelight")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sqldelight {
    database("NotesDatabase") {
        packageName = "org.jcastrejon.notes"
        verifyMigrations = true
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.sqldelight.jvm)
}