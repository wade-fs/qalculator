import com.android.build.api.variant.FilterConfiguration.FilterType.ABI
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose)
}

val splitApks = !project.hasProperty("noSplits")

val abiCodes = mapOf("armeabi-v7a" to 1, "arm64-v8a" to 2, "x86" to 3, "x86_64" to 4)

val abiFilterList = (properties["ABI_FILTERS"] as String).split(';')

android {
    namespace = "com.jherkenhoff.qalculate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jherkenhoff.qalculate"
        minSdk = 26
        targetSdk = 35
        versionCode = 30
        versionName = "0.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {useSupportLibrary = true }

        if (splitApks) {
            splits {
                abi {
                    isEnable = true
                    reset()
                    include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
                    isUniversalApk = true
                }
            }
        } else {
            ndk { abiFilters.addAll(abiFilterList) }
        }
    }

    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }

    signingConfigs {
        val props = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use<java.io.InputStream, Unit> { props.load(it) }
        }

        create("release") {
            storeFile = file(System.getenv("SIGNING_KEY_FILE") ?: props.getProperty("signing.key.file") ?: "release.jks")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD") ?: props.getProperty("signing.store.password")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS") ?: props.getProperty("signing.key.alias")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD") ?: props.getProperty("signing.key.password")
        }
    }

    buildTypes {
        release {
            val config = signingConfigs.getByName("release")
            val isSigningConfigured = config.storeFile?.exists() == true && !config.storePassword.isNullOrBlank()
            if (isSigningConfigured) {
                signingConfig = config
            }
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    androidComponents {
        onVariants { variant ->

            // Assigns a different version code for each output APK
            // other than the universal APK.
            variant.outputs.forEach { output ->
                val name =
                    if (splitApks) {
                        output.filters.find { it.filterType == ABI }?.identifier
                    } else {
                        abiFilterList.firstOrNull()
                    }

                // Stores the value of abiCodes that is associated with the ABI for this variant.
                val baseAbiCode = abiCodes[name]
                // Because abiCodes.get() returns null for ABIs that are not mapped by ext.abiCodes,
                // the following code doesn't override the version code for universal APKs.
                // However, because you want universal APKs to have the lowest version code,
                // this outcome is desirable.
                if (baseAbiCode != null) {
                    // Assigns the new version code to output.versionCode, which changes the version code
                    // for only the output APK, not for the variant itself.
                    output.versionCode.set(10*(output.versionCode.get() ?: 0) + baseAbiCode)
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
        // Disable unused AGP features
        aidl = false
        shaders = false
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.dataStore)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.hilt.core)
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.qalculate)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
