plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    buildToolsVersion = libs.versions.androidBuildTools.get()
    compileSdk = Integer.parseInt(libs.versions.androidComplieSdk.get())

    defaultConfig {
        minSdk = Integer.parseInt(libs.versions.androidMinSdk.get())
        targetSdk = Integer.parseInt(libs.versions.androidTargetSdk.get())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {

    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}