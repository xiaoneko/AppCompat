plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "app.compile"
    compileSdk = 35
    buildToolsVersion = "35.0.0"
    ndkVersion = "27.1.12297006"
    
    androidResources {
         noCompress.add(".so")
       // noCompress.addAll(listOf(".so", "adb", "fastboot", "dmtracedump", "magiskboot"))
      //  generateLocaleConfig = true
    }
    
    sourceSets {
        getByName("main") {
            jniLibs {
                srcDirs("libs")
            }
        }
    }
    
    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    defaultConfig {
        applicationId = "Android.XiaomiHyperOS.CuteKitty"
        minSdk = 24
        targetSdk = 35
        versionCode = 70
        versionName = "v0.8.838"
        
        buildConfigField("int", "TARGET_SDK_VERSION", targetSdk.toString())
        buildConfigField("int", "MIN_SDK_VERSION", minSdk.toString())
        buildConfigField("int", "COMPILE_SDK_VERSION", compileSdk.toString())
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
        
        externalNativeBuild {
            cmake {
                abiFilters("arm64-v8a", "armeabi-v7a")
                
            }
        }
    }
    
    externalNativeBuild {
        cmake {
            // CMakeLists.txt 
            path("src/main/cpp/CMakeLists.txt")
            
        }
        
    }
    
    signingConfigs {
        create("url") {
        // keystore file，.bks & .jks
            storeFile = file("keystore/xiao.keystore")
            storePassword = findProperty("KEYSTORE_PASSWORD") as String
            keyAlias = findProperty("KEY_ALIAS") as String
            keyPassword = findProperty("KEY_PASSWORD") as String
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "configs-keep.pro")
            signingConfig = signingConfigs.getByName("url")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("url")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        aidl = true
        buildConfig = true
    }
    
    kotlinOptions {
        jvmTarget = "21"
    }
    
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    
    lint {
        htmlReport = true
       // disable("ProtectedPermissions")
    }
    
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    
}

tasks
    .withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>()
    .configureEach {
        compilerOptions
            .jvmTarget
            .set(
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
            )
    }

dependencies {
    val lifecycle_version = "2.8.7"
    val navigation_version = "2.8.9"
    val shizuku_version = "13.1.5"
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-service:$lifecycle_version")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("com.squareup.okio:okio:3.10.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.fragment:fragment-ktx:1.8.6")
    implementation("com.aayushatharva.brotli4j:brotli4j:1.17.0")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")
    implementation("dev.rikka.shizuku:api:$shizuku_version")
    implementation("dev.rikka.shizuku:provider:$shizuku_version")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation(fileTree("configs") { include("*.jar") })
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}