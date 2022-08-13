pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MyNotes"
include(":app")
include(":core:notes")
include(":features:list")
include(":features:editor")
include(":foundation:arch")
include(":foundation:theme")
