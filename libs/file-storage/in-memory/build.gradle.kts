version = "1.1.3"

dependencies {
    api(project(":file-storage-api"))
    implementation(libs.spring.web)
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.autoconfigure)
    testImplementation(libs.spring.starter.test)
}
