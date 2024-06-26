import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id ("kotlin-kapt")
}



android {
    namespace = "com.example.retrofit_intro"
    compileSdk = 33

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.retrofit_intro"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties().apply { load(project.rootProject.file("local.properties").inputStream()) }
        val mapsApiKey: String = properties.getProperty("mapsApiKey")
        val weatherApiKey: String = properties.getProperty("weatherApiKey")
        val currencyApiKey : String = properties.getProperty("currencyApiKey")

        buildConfigField("String", "weatherApiKey", "\"$weatherApiKey\"")
        buildConfigField("String", "currencyApiKey", "\"$currencyApiKey\"")

        manifestPlaceholders["mapsApiKey"] = mapsApiKey
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.android.material:material:1.3-0-alpha02")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    // Activity KTX for viewModels()
    implementation ("androidx.activity:activity-ktx:1.1.0")
    // Architectural Components
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    // Retrofit
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
}