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
include(":features:detail")
include(":features:editor")
include(":features:list")
include(":foundation:arch")
include(":foundation:theme")
