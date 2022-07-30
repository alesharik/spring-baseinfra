import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.time.Duration
import java.net.URI
import java.net.http.HttpResponse
import java.util.Properties

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.kotlin.jvm)
}

group = "com.alesharik.spring"
version = "1.0-SNAPSHOT"

val localProperties = Properties()
if (rootProject.file("local.properties").exists()) {
    localProperties.load(rootProject.file("local.properties").inputStream())
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")

    configurations {
        compileOnly {
            extendsFrom(annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        val libs = rootProject.libs
        api(platform(libs.spring.boot.dependencies))
        compileOnly(libs.lombok)
        annotationProcessor(platform(libs.spring.boot.dependencies))
        annotationProcessor(libs.lombok)
        annotationProcessor(libs.spring.configuration.processor)
        testImplementation(libs.assertj)
        testImplementation(libs.mockito)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }

    tasks.withType<GenerateMavenPom>().all {
        doLast {
            val file = File("$buildDir/publications/gpr/pom-default.xml")
            var text = file.readText().replace("<dependencies/>", "<dependencies> </dependencies>")
            val regex =
                "(?s)(<dependencyManagement>.+?<dependencies>)(.+?)(</dependencies>.+?</dependencyManagement>)".toRegex()
            val matcher = regex.find(text)
            if (matcher != null) {
                text = regex.replaceFirst(text, "")
                val firstDeps = matcher.groups[2]!!.value
                text = regex.replaceFirst(text, "$1$2$firstDeps$3")
            }
            file.writeText(text)
        }
    }

    val token = localProperties["gpr.token"] as String? ?: System.getenv("GITHUB_TOKEN")

    task("checkForExistingArtifact") {
        group = "publishing"
        doLast {
            val url =
                "https://maven.pkg.github.com/alesharik/spring-baseinfra/com/alesharik/spring/${project.name}/${project.version}/${project.name}-${project.version}.pom"
            val client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build()
            val request = HttpRequest.newBuilder()
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(url))
                .header("Authorization", "Bearer $token")
                .build()
            val response = client.send(request, HttpResponse.BodyHandlers.discarding()).statusCode()
            if (response == 401) {
                throw RuntimeException("Unauthorized gh maven user. Please provide valid token")
            }
            if (response == 200) {
                gradle.taskGraph.allTasks.find {
                    it.project == project && it.name == "publishGprPublicationToGitHubPackagesRepository"
                }?.enabled = false
            }
        }
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/alesharik/spring-baseinfra")
                credentials(HttpHeaderCredentials::class) {
                    name = "Authorization"
                    value = "Bearer $token"
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
        publications {
            create<MavenPublication>("gpr") {
                groupId = "com.alesharik.spring"
                from(components["java"])
            }
        }
    }

    tasks.withType<PublishToMavenRepository>().all {
        dependsOn("checkForExistingArtifact")
    }
}
