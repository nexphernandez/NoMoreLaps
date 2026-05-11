# 📱 Manual de Usuario: Ecosistema NoMoreLaps

## 1. Aplicación Móvil (Usuario Final)
La aplicación móvil permite a los conductores encontrar, reservar y gestionar plazas de aparcamiento de forma eficiente.

### 🔑 Registro e Inicio de Sesión
*   **Acceso:** Abre la App y ve a la sección "Profile".
*   **Login:** Introduce tu email (`test@test.com`) y contraseña (`123456`).
*   **Modo Invitado:** Puedes explorar el mapa y ver parkings sin iniciar sesión, pero necesitarás una cuenta para reservar.

### 🗺️ Búsqueda de Parkings
*   **Mapa Interactivo:** Visualiza los parkings cercanos con pines de color azul. Haz clic en un pin para ver detalles rápidos.
*   **Vista de Lista:** Cambia a la vista de lista para ver todos los parkings ordenados por cercanía.
*   **Ficha de Detalle:** Consulta la dirección, precio por hora y plazas disponibles en tiempo real.

### 📅 Proceso de Reserva
1.  Selecciona un parking.
2.  Elige una plaza disponible (color verde).
3.  Confirma el tiempo estimado de estancia.
4.  **Sincronización:** Recibirás una notificación confirmando tu plaza.

---

## 2. Panel Web (Empresa/Administrador)
El panel de control permite a los dueños de parkings gestionar su red de ubicaciones.

### 🏢 Gestión de Parkings
*   **Dashboard:** Visualiza un resumen de ocupación y reservas activas.
*   **Añadir Parking:** Usa el nuevo mapa interactivo para seleccionar la ubicación exacta. Al hacer clic, se rellenarán automáticamente las coordenadas y la dirección.
*   **Configuración de Sanciones:** Define el importe de la multa y el intervalo de tiempo para usuarios que excedan su reserva.

### 🔔 Notificaciones y Perfil
*   Recibe avisos en tiempo real sobre nuevas reservas o sanciones emitidas.
*   Gestiona los datos maestros de tu empresa desde el perfil.

---

## 3. Gestión de Publicidad (ERP Odoo)
Las campañas publicitarias que aparecen en la App se gestionan desde Odoo.

1.  Accede a la instancia de Odoo (`http://localhost:8069`).
2.  Ve al módulo **"NoMoreLaps Ads"**.
3.  Crea una nueva **Campaign**:
    *   **Name:** Título de la oferta (ej: "¡50% Descuento Findes!").
    *   **Target URL:** Enlace a la web de la promo.
    *   **Ad Type:** Selecciona entre BANNER o POPUP.
    *   **Estado:** Cambia a "Activo" para que aparezca en la App móvil.

---
*Manual de usuario para la plataforma NoMoreLaps - Versión 1.0*
