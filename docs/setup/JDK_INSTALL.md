# JDK 21 Installatiehandleiding

Dit project vereist **Java Development Kit (JDK) 21**. Volg de onderstaande stappen om dit te installeren.

## Windows
1. Download de Windows x64 Installer van [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) of gebruik [Adoptium (Temurin)](https://adoptium.net/temurin/releases/?version=21).
2. Voer het .msi of .exe bestand uit.
3. Voeg Java toe aan je `PATH` omgevingsvariabele als de installer dit niet automatisch doet.
4. Controleer de installatie in een terminal:
   ```bash
   java -version
   ```

## macOS
1. Gebruik [Homebrew](https://brew.sh/):
   ```bash
   brew install openjdk@21
   ```
2. Volg de instructies van brew om de symlink aan te maken.
3. Of download het .pkg bestand van [Adoptium](https://adoptium.net/temurin/releases/?version=21).

## Linux (Ubuntu/Debian)
1. Update je package list:
   ```bash
   sudo apt update
   ```
2. Installeer OpenJDK 21:
   ```bash
   sudo apt install openjdk-21-jdk
   ```
3. Controleer de versie:
   ```bash
   java -version
   ```
