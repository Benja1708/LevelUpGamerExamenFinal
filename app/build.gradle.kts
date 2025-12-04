plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.example.levelupgamer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.levelupgamer"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    testOptions {
        // Esto le dice a Gradle que incluya los recursos y assets de Android
        // al ejecutar pruebas unitarias locales (src/test).
        unitTests.isIncludeAndroidResources = true

        // Esto es opcional, pero ayuda a manejar mocks de algunas APIs de Android
        unitTests.isReturnDefaultValues = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation("junit:junit:4.13.2")
    val coroutinesVersion = "1.7.3"
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    val archVersion = "2.2.0"
    testImplementation("androidx.arch.core:core-testing:$archVersion")
    val roomVersion = "2.6.1"
    testImplementation("androidx.room:room-testing:$roomVersion")
    testImplementation("androidx.test.ext:junit:1.1.5")
    val mockkVersion = "1.13.8"
    testImplementation("io.mockk:mockk:$mockkVersion")
    val robolectricVersion = "4.12.1" // Puedes usar una versión reciente
    testImplementation("org.robolectric:robolectric:$robolectricVersion")

    testImplementation("androidx.room:room-testing:2.6.1")
    testImplementation("com.google.truth:truth:1.1.3")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("app.cash.turbine:turbine:1.0.0")



    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.5.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.4")
    implementation("androidx.compose.material:material-icons-extended")
}