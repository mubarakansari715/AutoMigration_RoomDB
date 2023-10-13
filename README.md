# AutoMigration_RoomDB


1. Before starting Auto Migration Just put this in Gradle

  defaultConfig {
      ....

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

       ** //add this in the build.gradle.kts(app) file
        javaCompileOptions {
            annotationProcessorOptions { arguments["room.schemaLocation"] = "$projectDir/schemas" }
        }**
    }


2. After you add an AppDatabase class like

   @Database(
    entities = [User::class],
    version = 2,
    exportSchema = true,
   ** autoMigrations = [AutoMigration(from = 1, to = 2)],**

    )
abstract class AppDatabase : RoomDatabase() {

Note: After that, you can see the JSON of all query files by version. like 1.json, 2.json
    
