plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    buildToolsVersion = libs.versions.androidBuildTools.get()
    compileSdk = Integer.parseInt(libs.versions.androidComplieSdk.get())

    defaultConfig {
        applicationId = "org.jcastrejon.mynotes"
        minSdk = Integer.parseInt(libs.versions.androidMinSdk.get())
        targetSdk = Integer.parseInt(libs.versions.androidTargetSdk.get())
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":foundation:theme"))
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}