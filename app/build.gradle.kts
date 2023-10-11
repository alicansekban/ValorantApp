plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.caseapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.caseapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation ("androidx.navigation:navigation-testing:2.6.0")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation( platform("androidx.compose:compose-bom:2023.04.01"))

    implementation ("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation ("androidx.compose.material:material-icons-extended")

    // Optional - Integration with activities
    implementation ("androidx.activity:activity-compose:1.7.2")
    // Optional - Integration with ViewModels
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Optional - Integration with RxJava
    implementation ("androidx.compose.runtime:runtime-rxjava2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1.")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.4")

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")


    // hilt
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-compiler:2.46")

    // networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    implementation("com.airbnb.android:lottie-compose:5.2.0")

    implementation ("com.google.android.exoplayer:exoplayer:2.18.1")

    // Coroutine and Lifecycle
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")


    // Moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


    // Generic Utils
    implementation("com.blankj:utilcodex:1.30.6")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    implementation ("androidx.draganddrop:draganddrop:1.0.0")

    // Youtube Player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")


    // Glide
    val glideVersion = "4.12.0"
    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.5")
    ksp("com.github.bumptech.glide:compiler:$glideVersion")


    // Room
    implementation("androidx.room:room-runtime:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
}