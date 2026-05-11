# 🛠️ Informe Técnico: Despliegue para Presentación (Demo Live)

## 1. Introducción
Este documento detalla el procedimiento para poner en marcha el ecosistema **NoMoreLaps** durante la presentación final, garantizando la conectividad entre la App móvil, el Panel Web y el ERP Odoo mediante el uso de túneles seguros.

## 2. Preparación del Entorno (Docker)
Antes de iniciar la demo, el sistema debe estar corriendo localmente:
1.  **Limpieza de datos (Opcional pero recomendada):** `docker compose down -v` (para asegurar una DB limpia con usuarios de prueba).
2.  **Arranque de servicios:** `docker compose up --build -d`.
    *   Verificar que los 4 contenedores (`app-api`, `company-front`, `odoo`, `db`) están en estado "Running".

## 3. Configuración de Túneles (Cloudflared)
Para que el móvil y el tribunal puedan acceder a los servicios desde cualquier red, se utilizan túneles de Cloudflare. Se deben abrir 3 terminales y ejecutar:

*   **Túnel API:** `npx cloudflared tunnel --url http://localhost:8080`
*   **Túnel Web Panel:** `npx cloudflared tunnel --url http://localhost:4200`
*   **Túnel Odoo:** `npx cloudflared tunnel --url http://localhost:8069`

> **IMPORTANTE:** Copiar las URLs generadas (ej: `https://xxxx.trycloudflare.com`).

## 4. Sincronización de URLs
Una vez obtenidos los túneles, hay que actualizar las rutas en el código:

1.  **Mobile App:** Editar `user-front/app/services/api.ts` y actualizar `API_URL` con la URL del túnel de la API.
2.  **Web Panel:** Editar `company-front/src/environments/environment.ts` y actualizar `apiUrl` con la URL del túnel de la API.
3.  **ERP Odoo:** Asegurarse de que en `api-back/src/main/resources/application.properties`, la URL de Odoo apunte a `http://nomorelaps-odoo:8069` (conexión interna Docker).

## 5. Reconstrucción Final
Tras cambiar las URLs del Panel Web, es imperativo reconstruir el contenedor para que los cambios se apliquen al bundle de Angular:
```powershell
docker compose up --build -d company-front
```

## 6. Ejecución de la Demo
1.  **App Móvil:** Ejecutar `npx expo start` y escanear el QR con Expo Go.
2.  **Panel Web:** Acceder desde el navegador usando la URL del túnel correspondiente.
3.  **Odoo:** Acceder a la URL del túnel de Odoo para gestionar los anuncios en vivo.

---
*Este procedimiento garantiza una demostración fluida y profesional sin depender de configuraciones de red locales o apertura de puertos en el router.*
