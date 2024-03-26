plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(31)

    defaultConfig {
        applicationId = "com.example.plantdoc"
        minSdkVersion(21)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0") // Kotlin extensions for Android
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    // other dependencies if needed
}



}
