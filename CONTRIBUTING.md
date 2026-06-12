# Contributing to WorkLogTracker

Bedankt dat je wilt bijdragen aan WorkLogTracker! Volg deze handleiding om je ontwikkelomgeving op te zetten.

## Installatiehandleiding

Dit project is een Kotlin Multiplatform project bestaande uit een backend, een Android frontend en een web applicatie.

### Vereisten

Zorg ervoor dat de volgende software is geïnstalleerd. Als je hulp nodig hebt bij de installatie, klik dan op de link voor de specifieke handleiding:
- **[Java Development Kit (JDK) 21](docs/setup/JDK_INSTALL.md)**
- **[Android Studio](docs/setup/ANDROID_STUDIO_INSTALL.md)** (voor de frontend ontwikkeling)
- **[Docker & Docker Compose](docs/setup/DOCKER_INSTALL.md)** (voor de database en backend services)
- **[Kotlin 1.9+](docs/setup/KOTLIN_INSTALL.md)**

### Project Clonen

Voordat je begint met de installatie, moet je het project clonen naar je lokale machine:

1.  Open een terminal.
2.  Clone de repository:
    ```bash
    git clone https://github.com/jschroevers8/WorkLogTracker.git
    ```
3.  Ga naar de projectmap:
    ```bash
    cd WorkLogTracker
    ```

### Project Structuur

- `backend`: Ktor server applicatie.
- `frontend`: Android applicatie (Jetpack Compose).
- `webapp`: Web applicatie (Compose HTML/JS).
- `shared`: Gedeelde code tussen backend, frontend en webapp.

### Stap 1: Backend Opzetten

De backend vereist een PostgreSQL database. De makkelijkste manier om dit op te zetten is via Docker.

1.  Ga naar de root directory van het project.
2.  Start de database (en optioneel de backend) via Docker Compose:
    ```bash
    docker-compose up -d
    ```
    Dit start een PostgreSQL instantie op poort 5432.

3.  Als je de backend lokaal wilt draaien zonder Docker:
    ```bash
    ./gradlew :backend:run
    ```
    De backend is dan bereikbaar op `http://localhost:8080`.

### Stap 2: Android Frontend Opzetten

1.  Open het project in **Android Studio**.
2.  Wacht tot Gradle de synchronisatie heeft voltooid.
3.  Zorg ervoor dat je een Android Emulator hebt geconfigureerd (API level 28 of hoger).
4.  Run de `frontend` module op je emulator of fysieke device.

### Stap 3: Web Applicatie Opzetten

De web applicatie wordt gecompileerd naar JavaScript.

1.  Start de web applicatie in development mode:
    ```bash
    ./gradlew :webapp:jsBrowserDevelopmentRun --continuous
    ```
2.  De webapp zal bereikbaar zijn op de URL die in de console wordt getoond (meestal `http://localhost:8081`).

---

## Ontwikkelrichtlijnen

### Code Stijl
- Volg de bestaande Kotlin code stijl in het project.
- Gebruik `ktlint` voor het controleren van de formatting:
  ```bash
  ./gradlew ktlintCheck
  ```

### Architectuur
- Het project volgt **Clean Architecture** en **Domain-Driven Design (DDD)** principes.
- Houd domeinlogica in de `domain` layer binnen de modules.
- Gebruik Use Cases voor orchestratie.

### Bijdragen
1.  Fork de repository.
2.  Maak een nieuwe branch aan voor je feature of bugfix.
3.  Voeg tests toe voor nieuwe functionaliteit.
4.  Open een Pull Request met een duidelijke beschrijving van je wijzigingen.
