version = "1.0.1"

dependencies {
    api(project(":file-storage-api"))
    implementation(libs.spring.web)
    implementation(libs.problems.web)
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.bundles.springdoc)
    testImplementation(libs.spring.starter.test)
}
