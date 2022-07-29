rootProject.name = "java-baseinfra"

File("${rootProject.projectDir}/libs").listFiles()?.forEach { proj ->
    proj.listFiles()?.filter { it.isDirectory }?.forEach {
        include(":${proj.name}-${it.name}")
        project(":${proj.name}-${it.name}").projectDir = it
    }
}
