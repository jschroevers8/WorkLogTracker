# Docker & Docker Compose Installatiehandleiding

Docker wordt gebruikt om de PostgreSQL database en de backend services eenvoudig te kunnen draaien.

## Windows & macOS
1. Download en installeer [Docker Desktop](https://www.docker.com/products/docker-desktop/).
2. Volg de installatie-instructies.
3. Zorg ervoor dat Docker Desktop is opgestart.
4. Open een terminal en controleer de installatie:
   ```bash
   docker --version
   docker-compose --version
   ```

## Linux (Ubuntu)
1. Update je packages: `sudo apt update`
2. Installeer Docker:
   ```bash
   sudo apt install docker.io
   ```
3. Installeer Docker Compose:
   ```bash
   sudo apt install docker-compose
   ```
4. Voeg je gebruiker toe aan de docker groep (optioneel, om `sudo` te vermijden):
   ```bash
   sudo usermod -aG docker $USER
   ```
   *Let op: Je moet opnieuw inloggen om dit effectief te maken.*
