version = "1.1.0"

dependencies {
    api(project(":country-provider"))
    implementation(libs.spring.web)
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.autoconfigure)
}
