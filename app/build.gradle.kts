plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "jp.ac.jec.cm0199.currentlocationdemojava"
    compileSdk = 35

    defaultConfig {
        applicationId = "jp.ac.jec.cm0199.currentlocationdemojava"
        minSdk = 30
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // TODO STEP-1 位置情報取得に必要なライブラリ - START
    // implementation("com.google.android.gms:play-services-location:21.3.0")
    // END
}