# AutoMigration_RoomDB


1. Before start Auto Migartion Just put this in Gradle

  defaultConfig {
      ....

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

       ** //add this in the build.gradle.kts(app) file
        javaCompileOptions {
            annotationProcessorOptions { arguments["room.schemaLocation"] = "$projectDir/schemas" }
        }**
    }


2. After you add on AppDatabase class like

   @Database(
    entities = [User::class],
    version = 2,
    exportSchema = true,
   ** autoMigrations = [AutoMigration(from = 1, to = 2)],**

    )
abstract class AppDatabase : RoomDatabase() {

    
