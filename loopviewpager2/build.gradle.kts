plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    compileOnly("androidx.recyclerview:recyclerview:1.2.1")
    compileOnly("androidx.viewpager2:viewpager2:1.0.0")
}

tasks {
    register("sourcesJar", Jar::class) {
        from(android.sourceSets["main"].java.srcDirs)
        archiveClassifier.set("sources")
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.takwolf.android.loopviewpager2"
                artifactId = "loopviewpager"
                version = "0.0.1"

                from(components["release"])
                artifact(tasks.named("sourcesJar"))
            }
        }
    }
}