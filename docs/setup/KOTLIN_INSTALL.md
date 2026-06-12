# Kotlin Installatiehandleiding

Dit project is geschreven in **Kotlin**. In de meeste gevallen hoef je Kotlin niet handmatig te installeren, omdat de IDE (Android Studio of IntelliJ IDEA) en Gradle dit automatisch afhandelen.

## Gebruik binnen de IDE
1. Zorg ervoor dat de **Kotlin plugin** is geïnstalleerd in je IDE (meestal standaard aanwezig).
2. De versie van de Kotlin compiler wordt beheerd door Gradle in het `build.gradle.kts` bestand van het project.

## Handmatige installatie (Optioneel)
Als je de Kotlin compiler (`kotlinc`) op de commandline wilt gebruiken buiten Gradle om:

1. Gebruik [SDKMAN!](https://sdkman.io/):
   ```bash
   sdk install kotlin
   ```
2. Of gebruik Homebrew (Mac):
   ```bash
   brew install kotlin
   ```

## Versie controle
Controleer of de Kotlin plugin in je IDE up-to-date is om compatibiliteitsproblemen te voorkomen met Kotlin 1.9+.
