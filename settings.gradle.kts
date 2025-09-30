rootProject.name = "RentStadium"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google() /*{
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }*/
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google() /*{
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }*/
        mavenCentral()
    }
}
include(":data")
include(":core:datastore")
include(":di")
include(":domain")

include(":feature:auth")
include(":feature:base")
include(":feature:validation")
include(":feature:user:home")
include(":feature:user:search")
include(":feature:user:stadium_detail")

include(":navigation")
include(":core:network")
include(":composeApp")
include(":shared")
