plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "kr.tmsoft.gptchat"
    compileSdk = Version.compileSdk

    defaultConfig {
        applicationId = "kr.tmsoft.gptchat"
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
        versionCode = Version.versionCode
        versionName = Version.versionName

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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":tmDialog"))

    implementation(Dep.AndroidX.core)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.material)
    implementation(Dep.AndroidX.constraintLayout)
    implementation(Dep.AndroidX.activity)
    implementation(Dep.AndroidX.fragment)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espressoCore)

    implementation(Dep.TnkFactory.core)

    implementation(Dep.Timber.core)

    // Room 사용을 위한 의존성 추가
    implementation(Dep.Room.runtime)
    implementation(Dep.Room.ktx)
    kapt(Dep.Room.compiler)

    // ViewModel 사용을 위한 의존성 추가
    implementation(Dep.Lifecycle.viewModel)

    //retrofit2 & okhttp
    implementation(Dep.Square.retrofit)
    implementation(Dep.Square.converterGson)
    implementation(Dep.Square.converterScalars)
    implementation(Dep.Square.okhttp3)
    implementation(Dep.Square.loggingInterceptor)

    // Gilde 관련
    implementation(Dep.Gilde.okhttp3)
}