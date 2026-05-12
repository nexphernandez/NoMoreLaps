# Informe Técnico: Plataforma NoMoreLaps

## 1. Requisitos del Sistema
Para la correcta instalación y ejecución del ecosistema NoMoreLaps, se requiere el siguiente entorno:

### Hardware Recomendado
*   **Procesador:** Quad-core 2.5 GHz o superior.
*   **Memoria RAM:** 8 GB mínimo (debido a la ejecución simultánea de contenedores Docker y Odoo).
*   **Almacenamiento:** 5 GB de espacio libre.

### Software Necesario
*   **Docker Desktop:** v4.0 o superior.
*   **Node.js:** v18.x (LTS) o superior.
*   **Java JDK:** v17 (Amazon Corretto o Eclipse Temurin).
*   **Expo Go:** Instalado en dispositivo móvil para pruebas de la App.
*   **Git:** Para el control de versiones.

## 2. Arquitectura del Sistema
NoMoreLaps ha sido diseñado bajo un enfoque de **Sistemas Distribuidos** y **Microservicios Contenerizados**, asegurando escalabilidad y desacoplamiento.

### A. Backend: Arquitectura Hexagonal (Ports & Adapters)
El núcleo del sistema (`api-back`) sigue los principios de **Clean Architecture**:
*   **Domain Layer:** Contiene las entidades puras de negocio (User, Parking, Reservation, Sanction) sin dependencias de frameworks.
*   **Business Layer (Use Cases):** Define las interfaces y la lógica de orquestación (ej. cálculo de precios dinámicos).
*   **Infrastructure Layer:** Implementa los adaptadores externos:
    *   **Persistence:** Spring Data JPA con Hibernate sobre PostgreSQL.
    *   **Security:** Implementación de JWT (JSON Web Tokens) y filtros de seguridad personalizados.
    *   **ERP Integration:** Adaptador XML-RPC para comunicación bidireccional con Odoo.

### B. App Móvil: Estrategia Offline-First
La aplicación móvil (`user-front`) está diseñada para funcionar en entornos de baja conectividad (parkings subterráneos):
*   **Local Database:** Uso de `expo-sqlite` con modo **WAL (Write-Ahead Logging)** para máxima velocidad de escritura.
*   **Caché de Datos:** Sincronización proactiva de parkings y estado de plazas.
*   **Cola de Sincronización:** Las reservas realizadas sin internet se guardan en una tabla de `pending_reservations` y se sincronizan automáticamente cuando el dispositivo detecta conexión mediante un listener de red.

### C. Web Dashboard: Angular Enterprise Patterns
El panel de empresa (`company-front`) utiliza patrones modernos de desarrollo web:
*   **Angular Signals:** Gestión de estado reactiva de grano fino para una interfaz fluida.
*   **Leaflet Integration:** Sistema de geocodificación inversa mediante la API de Nominatim para la creación asistida de parkings.
*   **Interceptors:** Gestión centralizada de tokens de autenticación y manejo de errores 401/403.

## 3. Seguridad y Autenticación
*   **Hashing:** Las contraseñas se cifran mediante **BCrypt** con un factor de coste de 10.
*   **Stateless Auth:** El sistema no guarda sesiones en memoria, utilizando tokens JWT firmados para validar cada petición.
*   **CORS & API Keys:** Configuración estricta de orígenes permitidos y filtrado por API Key para integraciones de terceros.

## 4. Despliegue para Presentación (Live Demo)
Para la defensa en clase, se utiliza una infraestructura de túneles para saltar firewalls y NATs:

### Paso 1: Infraestructura Docker
Levantar la malla de servicios:
```powershell
docker compose up --build -d
```
Esto garantiza que la red interna de Docker (`nomorelaps-network`) conecte la API con la DB y Odoo de forma aislada.

### Paso 2: Exposición mediante Cloudflared
Se deben levantar tres túneles para conectar el mundo exterior con los servicios locales:
1.  **API (8080):** Proporciona el punto de entrada para la App y la Web.
2.  **Web (4200):** Permite al tribunal entrar al panel desde sus propios dispositivos.
3.  **Odoo (8069):** Permite la gestión en vivo de los anuncios.

### Paso 3: Configuración de Entorno Dinámico
*   **En Angular:** Actualizar `environment.ts` con la URL del túnel de la API.
*   **En Móvil:** Actualizar `services/api.ts` con la URL del túnel de la API.
*   **Rebuild:** Es crítico ejecutar `docker compose up --build -d company-front` para que el código compilado de Angular "conozca" la nueva URL del túnel.

## 5. Ejecución de la Demo
1.  **App Móvil:** Ejecutar `npx expo start` y escanear el QR con Expo Go.
2.  **Panel Web:** Acceder desde el navegador usando la URL del túnel correspondiente.
3.  **Odoo:** Acceder a la URL del túnel de Odoo para gestionar los anuncios en vivo.

## 6. Calidad y Rendimiento (RA4)
Para garantizar la robustez del ecosistema, se han implementado los siguientes mecanismos de control:

### Pruebas Unitarias e Integración
*   **Backend:** Cobertura de tests unitarios mediante **JUnit 5** y **Mockito** para la lógica de servicios y mappers. Se verifica la integridad de las transacciones y el manejo de excepciones.
*   **Validación de Datos:** Uso de anotaciones de validación en la API para asegurar que los datos (latitud, longitud, precios) cumplen con los rangos lógicos.

### Indicadores de Rendimiento (KPIs)
*   **Eficiencia de Sincronización:** El sistema de colas en la App móvil permite una recuperación de datos en menos de 1 segundo tras detectar el cambio de estado de red (offline a online).
*   **Escalabilidad:** El uso de Docker permite el escalado horizontal de la API en caso de aumento de demanda de usuarios.
