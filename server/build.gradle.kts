import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask
import java.io.BufferedReader
import proguard.gradle.ProGuardTask

plugins {
    application
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.shadow)
}

dependencies {

    // Dependencies of Aniyomi, some are duplicate from root build.gradle.kts
    // keeping it here for reference
    implementation(libs.injekt.core)
    implementation(libs.jsoup)
    implementation(libs.rxjava)
    implementation(libs.bundles.okhttp)

    // AndroidCompat
    implementation(project(":AndroidCompat"))
    implementation(project(":AndroidCompat:Config"))

    // Testing
    testImplementation(kotlin("test-junit5"))
}

val MainClass = "suwayomi.tachidesk.MainKt"
application {
    mainClass.set(MainClass)
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}

// should be bumped with each stable release
val inspectorVersion = "v2.1.0"

// counts commit count on master
val inspectorRevision = runCatching {
    System.getenv("ProductRevision") ?: Runtime
        .getRuntime()
        .exec("git rev-list HEAD --count")
        .let { process ->
            process.waitFor()
            val output = process.inputStream.use {
                it.bufferedReader().use(BufferedReader::readText)
            }
            process.destroy()
            "r" + output.trim()
        }
}.getOrDefault("r0")

val String.wrapped get() = """"$this""""

buildConfig {
    className("BuildConfig")
    packageName("suwayomi.server")

    useKotlinOutput()

    buildConfigField("String", "NAME", rootProject.name.wrapped)
    buildConfigField("String", "VERSION", inspectorVersion.wrapped)
    buildConfigField("String", "REVISION", inspectorRevision.wrapped)
}

tasks {
    shadowJar {
        dependencies {
            exclude("com/ibm/icu/impl/data/icudt72b/*/*")
        }
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to MainClass,
                    "Implementation-Title" to rootProject.name,
                    "Implementation-Vendor" to "The Tachiyomi Open Source Project",
                    "Specification-Version" to inspectorVersion,
                    "Implementation-Version" to inspectorRevision
                )
            )
        }
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(inspectorVersion)
        archiveClassifier.set(inspectorRevision)
    }


    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.InternalCoroutinesApi",
                "-Xopt-in=kotlin.io.path.ExperimentalPathApi",
            )
        }
    }

    test {
        useJUnit()
    }

    withType<ShadowJar> {
        destinationDirectory.set(File("$rootDir/server/build"))
        dependsOn("formatKotlin", "lintKotlin")
    }

    named("run") {
        dependsOn("formatKotlin", "lintKotlin")
    }

    withType<LintTask> {
        source(files("src/kotlin"))
    }

    withType<FormatTask> {
        source(files("src/kotlin"))
    }

    withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

    register<ProGuardTask>("optimizeShadowJar") {
        group = "shadow"
        val shadowJar = getByName("shadowJar")
        dependsOn(shadowJar)
        val shadowJars = shadowJar.outputs.files
        injars(shadowJars)
        outjars(
            shadowJars.map { file ->
                File(file.parentFile, "min/" + file.name)
            }
        )
        val javaHome = System.getProperty("java.home")
        libraryjars("$javaHome/jmods")
        configuration("proguard-rules.pro")
    }
}
