import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

val key: String = gradleLocalProperties(rootDir).getProperty("BASE_URL")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.satria.dicoding.submission.mygithubapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.satria.dicoding.submission.mygithubapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val projectProperties = readProperties(project.rootProject.file("local.properties"))
        buildConfigField("String", "BASE_URL", projectProperties["BASE_URL"] as String)
        buildConfigField("String", "TOKEN", projectProperties["PERSONAL_KEY"] as String)
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

fun readProperties(properties: File) = Properties().apply {
    properties.inputStream().use { fis ->
        load(fis)
    }
}