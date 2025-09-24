Project development guidelines (RentStadium)

Last updated: 2025-09-24

Scope
- This document captures build/configuration, testing, and development specifics that are unique to this Kotlin Multiplatform (KMP) Compose project.
- Audience: experienced KMP/Compose developers.

Architecture and modules
- Multiplatform app using Compose Multiplatform. App entry is the composeApp module.
- Feature and core modules are split by responsibility and shared across platforms:
  - features: feature/auth, feature/base, feature/validation, feature/user/home, feature/user/search, feature/user/stadium_detail
  - shared utilities/UI: shared (common UI components, navigation), navigation (NavHost setup), domain/data/core (network, datastore)
  - DI: di module provides Koin modules; composeApp/commonMain includes projects.di and others.
- Navigation
  - Type-safe navigation via androidx.navigation with kotlinx.serialization. See shared/src/commonMain/.../Screen.kt.
  - Routes are sealed @Serializable classes. Composables register with composable<Screen.X> and navigate(Screen.X(...)).
- Dependency injection
  - Koin is used for DI; viewModels retrieved via koinViewModel in composables. Ensure modules are included in the commonMain through di module.
- Platform-specific components
  - Example: MapComponent has expect/actual implementations under feature/user/search/src/androidMain and iosMain.

Build and configuration
Prerequisites
- JDK 11 (Gradle and Android toolchain configured for Java 11).
- Android SDK installed; local.properties must have sdk.dir configured.
- Xcode for iOS build/run; Cocoapods is not used; iOS uses XCFramework produced by composeApp configuration.
- Gradle Wrapper: Gradle 8.x via ./gradlew.

Android
- Assemble/debug APK of the Compose app module:
  - ./gradlew :composeApp:assembleDebug
- Install/run from Android Studio or use standard ADB install tasks.
- Min/target SDK values are sourced from gradle/libs.versions.toml; Java compatibility is set to 11.

iOS
- The composeApp module configures an XCFramework (XCFramework xcf = XCFramework()). Each iOS target (iosX64, iosArm64, iosSimulatorArm64) adds itself to xcf.
- Typical Gradle tasks (names depend on KGP version; use ./gradlew tasks to confirm):
  - Assemble XCFramework (Debug): ./gradlew :composeApp:assembleDebugXCFramework
  - Assemble XCFramework (Release): ./gradlew :composeApp:assembleReleaseXCFramework
- Alternatively, open iosApp/iosApp.xcodeproj and run the iosApp scheme to launch the host iOS app that integrates the shared code.

Networking/config
- Base URL is defined in core/network/src/commonMain/.../NetworkModule.kt as base_url = https://stadium.rent-home.uz/api/.
- If you need environment switching, consider moving this to Gradle properties and wiring via expect/actual or buildConfig constants.

Logging and expect/actual note
- shared/utils/Logger uses expect/actual. Kotlin shows a Beta warning for expect/actual classes. Suppress via compiler flag if needed:
  - In Kotlin compiler options, add -Xexpect-actual-classes (per KT-61573) once the team agrees.

Testing
Where tests live
- The composeApp module declares commonTest dependencies (kotlin.test). Preferred place for cross-platform unit tests:
  - composeApp/src/commonTest/kotlin/... (kotlin.test API)
- Platform-specific tests:
  - Android JVM unit tests: composeApp/src/test (Gradle task: :composeApp:testDebugUnitTest or :composeApp:testReleaseUnitTest)
  - iOS simulator tests: tasks like :composeApp:iosSimulatorArm64Test exist for common tests executed on that target. Use ./gradlew tasks to discover exact names.

Running tests (validated)
- To run all tests across configured targets:
  - ./gradlew :composeApp:allTests
- To run only JVM unit tests for Android variant:
  - ./gradlew :composeApp:testDebugUnitTest
- To run iOS simulator tests (if needed):
  - ./gradlew :composeApp:iosSimulatorArm64Test

Adding a new test (example pattern)
- Create a file under composeApp/src/commonTest/kotlin, e.g. dev/soul/FooTest.kt:
  - package dev.soul
  - import kotlin.test.Test
  - import kotlin.test.assertEquals
  - class FooTest {
      @Test fun sanity() { assertEquals(4, 2 + 2) }
    }
- Then run: ./gradlew :composeApp:allTests
- Notes:
  - Use kotlin.test for cross-platform assertions.
  - If a test needs Android-only APIs, place it in android unit test sourceSet and use the Android unit test tasks mentioned above.

Creating and validating a sample test (what we verified)
- We validated the pipeline by adding a trivial commonTest and running:
  - ./gradlew :composeApp:allTests (BUILD SUCCESSFUL)
- Remember to remove throwaway tests after validating to keep the repo clean.

Development conventions and tips
- Compose
  - Material3 components; prefer stateless composables with state hoisted to viewModels.
  - Use BaseBox and shared components from the shared module where possible for consistency.
- ViewModels
  - Lifecycle-aware viewModels via androidx.lifecycle and Koin integration. For screen initialization requiring side-effects (e.g., sending an initial intent), prefer LaunchedEffect(Unit) in the Composable (as seen in NavGraph for VerifyCode/Register).
- Navigation
  - Keep all route parameters @Serializable. Use navController.navigate(Screen.Route(args)) rather than string routes.
- Gradle/version catalogs
  - Dependencies and versions are organized in gradle/libs.versions.toml; prefer adding/updating there.
- Maps/Search feature
  - feature/user/search contains MapSearchRoot, ViewModel, and platform MapComponent actuals (Android/iOS). When adding map features, edit expect/actual definitions accordingly.
- Debugging network/DI
  - If Koin resolution fails in a Composable, check that the module is exported via di module and included in composeApp commonMain dependencies (this project already includes projects.di in commonMain).

CI/CD considerations
- The project builds fine using Gradle wrapper with Java 11. If you see expect/actual class warnings in CI, add -Xexpect-actual-classes to the Kotlin compiler options gated by a CI flag to avoid warning-only failures.

Common pitfalls
- Navigation parameters must remain serializable across KMP; avoid platform-specific types in Screen.*.
- When adding new iOS actuals, ensure you implement all expect declarations to avoid linkage errors.
- Keep Android target JVM at 11 to match project settings.

Appendix: Useful Gradle commands
- List tasks: ./gradlew tasks
- Clean: ./gradlew clean
- Build all: ./gradlew build
- Run all tests: ./gradlew :composeApp:allTests
