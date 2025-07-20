import java.util.Properties

val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    val properties = Properties()
    properties.load(localPropertiesFile.inputStream())

    properties.forEach { key, value ->
        project.extensions.extraProperties[key.toString()] = value.toString()
    }
}

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField(
            "String",
            "CLOUDINARY_CLOUD_NAME",
            "\"${project.findProperty("CLOUDINARY_CLOUD_NAME")?.toString() ?: ""}\""
        )
        buildConfigField(
            "String",
            "CLOUDINARY_UPLOAD_PRESET",
            "\"${project.findProperty("CLOUDINARY_UPLOAD_PRESET")?.toString() ?: ""}\""
        )
        buildConfigField(
            "String",
            "BASE_URL",
            "\"${project.findProperty("BASE_URL")?.toString() ?: ""}\""
        )
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "CLOUDINARY_CLOUD_NAME",
                "\"${project.findProperty("CLOUDINARY_CLOUD_NAME")?.toString() ?: ""}\""
            )
            buildConfigField(
                "String",
                "CLOUDINARY_UPLOAD_PRESET",
                "\"${project.findProperty("CLOUDINARY_UPLOAD_PRESET")?.toString() ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${project.findProperty("BASE_URL")?.toString() ?: ""}\""
            )
        }
        debug {
            buildConfigField(
                "String",
                "CLOUDINARY_CLOUD_NAME",
                "\"${project.findProperty("CLOUDINARY_CLOUD_NAME")?.toString() ?: ""}\""
            )
            buildConfigField(
                "String",
                "CLOUDINARY_UPLOAD_PRESET",
                "\"${project.findProperty("CLOUDINARY_UPLOAD_PRESET")?.toString() ?: ""}\""
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${project.findProperty("BASE_URL")?.toString() ?: ""}\""
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
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.coil.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.okhttp)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.jetbrains.kotlinx.coroutines.android)
    testImplementation(libs.junit)
    implementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}