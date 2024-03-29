plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.takwolf.android.demo.loopviewpager"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.takwolf.android.demo.loopviewpager"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("io.coil-kt:coil:2.1.0")
    implementation(project(":loopviewpager2"))
}
