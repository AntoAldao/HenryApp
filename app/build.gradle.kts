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
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android) // Agregar Hilt
    alias(libs.plugins.kotlin.kapt) // Agregar Kapt

}

android {
    namespace = "com.example.henryapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.henryapp"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = true
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
    implementation(libs.hilt.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.coil.compose)
    implementation(libs.hilt.android) // Hilt
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.okhttp)
    kapt(libs.hilt.android.compiler) // Kapt para Hilt
    kapt(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}