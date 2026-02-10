# Anteproyecto – NoMoreLaps

![NoMoreLaps Logo](../Images/Logo.png)

**Autores:** Nicolás Expósito Hernández y Alejandro Díaz Luis  
**Fecha:** Febrero 2026

---

## Índice de Contenidos

1. [Introducción](#introducción)
2. [Planteamiento del Problema](#planteamiento-del-problema)
3. [Justificación](#justificación)
4. [Objetivos](#objetivos)
5. [Marco Teórico/Tecnológico](#marco-teóricotecnológico)
6. [Metodología](#metodología)
7. [Alcance y Limitaciones](#alcance-y-limitaciones)
8. [Integración del Calendario](#integración-del-calendario)
9. [Sistema de Precios y Sanciones](#sistema-de-precios-y-sanciones)
10. [Recursos](#recursos)

---

## Introducción

**NoMoreLaps** es una aplicación móvil y plataforma web diseñada para mejorar la movilidad urbana facilitando la búsqueda de aparcamiento en tiempo real. 

### Características Principales

La aplicación permite a los usuarios:

- Localizar plazas libres en tiempo real
- Consultar precios y horarios disponibles
- Realizar reservas de manera rápida
- Recibir descuentos automáticos según disponibilidad y hora

Se conecta con los sistemas de gestión de las empresas de parking mediante una **API estándar**, lo que permite centralizar la información de manera confiable y actualizada.

### Diferenciadores

NoMoreLaps se diferencia de otras soluciones existentes al integrar:

- **Lectura del calendario del usuario** para sugerir estacionamientos cerca de sus eventos
- **Cálculo de precios dinámicos** y descuentos según disponibilidad
- **Aplicación de sanciones automáticas** si no se respeta el horario de reserva

---

## Planteamiento del Problema

En muchas ciudades, encontrar aparcamiento es una tarea complicada que genera:

  - Estrés en los conductores
  - Pérdida de tiempo considerable
  - Congestión vehicular en zonas urbanas

### Problemas de Soluciones Actuales

Las aplicaciones existentes ofrecen información básica de disponibilidad, pero **NO**:

  - Integran los horarios personales del usuario
  - Permiten descuentos automáticos según demanda
  - Imponen sanciones si no se respeta la reserva
  - Optimizan la búsqueda según eventos del calendario

**Conclusión:** Existe un hueco en el mercado que NoMoreLaps busca cubrir, ofreciendo un sistema completo y en tiempo real que beneficie tanto a usuarios como a empresas de parking.

---

## Justificación

La aplicación NoMoreLaps permitirá:

  - **Mejorar la eficiencia** de aparcamiento en zonas urbanas
  - **Ajustar precios dinámicamente** según demanda (beneficio para empresas)
  - **Integrar el calendario del usuario** para optimizar reservas y evitar conflictos
  - **Reducir tiempo de búsqueda** significativamente para los usuarios
  - **Integrar información en tiempo real** desde la API del proveedor
  - **Evitar infracciones** mediante control de horarios y sanciones automáticas

---

## Objetivos

### Objetivo General

Desarrollar una aplicación móvil y web que permita **gestionar, localizar y reservar aparcamientos en tiempo real**, integrando precios dinámicos, sanciones y planificación basada en el calendario del usuario.

### Objetivos Específicos

1. Implementar la **API** que conecte los sistemas de las empresas con la app
2. Desarrollar el **módulo de reservas** con control de horarios y sanciones automáticas
3. **Integrar el calendario del usuario** para sugerir plazas cercanas a eventos programados
4. Permitir el **registro de precios** por parte de las empresas y aplicar descuentos automáticos según disponibilidad
5. Crear la **interfaz de usuario** (front-end) para consultar disponibilidad, realizar reservas y visualizar información del parking

---

## Marco Teórico/Tecnológico

### 5.1 Lenguajes y Frameworks

| Componente | Tecnología |
|------------|-----------|
| **Front-end Móvil** | React Native + Expo |
| **Front-end Web** | React |
| **Back-end/API** | Java + Spring Boot |
| **Base de Datos** | SQLite |
| **Mapas** | Google Maps API |
| **Calendario** | Google Calendar, iCal |
| **Autenticación** | JWT (JSON Web Tokens) |
| **Control de Versiones** | GitHub |
| **Gestión de Tareas** | GitHub Kanban (Issues) |
| **Herramientas** | VS Code, Docker |

### 5.2 Estructura de la Base de Datos

#### Tablas Principales

| Tablas | Descripción |
|-------|------------|
| **Usuarios** | Información de usuarios, empresas y administradores |
| **Parkings** | Información de parkings, ubicación, plazas, horarios y precio base |
| **Reservas** | Datos de reservas con precios calculados y estado |
| **Sanciones** | Registro de sanciones por incumplimiento de horario |
| **CalendarioUsuario** | Eventos del calendario del usuario para integración |
| **PreciosDinamicos** | Precios calculados según disponibilidad y hora |

### 5.3 Estructura de la API (Endpoints Principales)

#### 1. Registro de Aparcamiento

```json
POST /api/parkings
{
  "empresaId": 123,
  "nombre": "Parking Central",
  "ubicacion": "Calle Mayor 10",
  "plazasTotales": 50,
  "horarios": "08:00-22:00",
  "preciosBase": 2.5
}
```

#### 2. Consulta de Disponibilidad

```json
GET /api/parkings/123/disponibilidad
{
  "fecha": "2026-02-10",
  "hora": "10:00"
}
```

#### 3. Reserva de Plaza

```json
POST /api/reservas
{
  "usuarioId": 45,
  "parkingId": 123,
  "fecha": "2026-02-10",
  "horaEntrada": "10:00",
  "horaSalida": "12:00"
}
```

#### 4. Aplicación de Sanciones (tras 10 min de retraso)

```json
POST /api/reservas/123/sancion
{
  "usuarioId": 45,
  "motivo": "No respetó el horario de reserva",
  "monto": 5.0
}
```

#### 5. Consulta de Calendario del Usuario

```json
GET /api/usuarios/45/calendario
{
  "fechaInicio": "2026-02-10",
  "fechaFin": "2026-02-11"
}
```

---

## Metodología

Se utilizará **Kanban en GitHub** para gestionar los issues. Cada tarea se moverá por columnas en el siguiente flujo:

```
Backlog → In Progress → In Review → Done
```

Esto permite un **flujo continuo de trabajo** y flexibilidad para priorizar según avance el proyecto.

### Fases de Desarrollo

#### **Fase 1: Análisis y Diseño**
  - Definición de casos de uso
  - Diagramas de clases y arquitectura
  - Diseño UI/UX de la aplicación

#### **Fase 2: Desarrollo Back-end**
  - Creación de API REST
  - Capas de negocio
  - Persistencia en SQLite
  - Controladores y servicios
  - Tests unitarios

#### **Fase 3: Desarrollo Front-end**
  - Interfaces de usuario
  - Conexión a API
  - Integración de Google Maps
  - Integración de calendario

#### **Fase 4: Pruebas**
  - Tests unitarios
  - Pruebas de integración
  - Pruebas de usuario (UAT)

#### **Fase 5: Entrega Final**
  - Documentación completa
  - Video demostrativo
  - Despliegue en servidor de pruebas

---

## Alcance y Limitaciones

### 7.1 Alcance

**Funcionalidades incluidas:**

  - Registro de parkings y precios por parte de empresas
  - Consulta de disponibilidad en tiempo real
  - Integración con calendario del usuario
  - Aplicación de sanciones por retraso en horarios
  - Descuentos automáticos según disponibilidad y hora
  - Interfaz web y móvil
  - Sistema de autenticación con JWT

### 7.2 Limitaciones

**Funcionalidades NO incluidas:**

  - Reconocimiento automático de plazas libres mediante sensores físicos (dependerá de input manual de empresas)
  - La app dependerá de que las empresas integren correctamente la API
  - La gestión de pagos se realizará fuera de la plataforma (solo cálculo de montos)
  - No incluye sistema de notificaciones push en esta versión inicial

---

## Integración del Calendario

La aplicación solicitará **permisos de acceso al calendario** del usuario (Google Calendar, Outlook, iCal, etc.).

### Funcionalidades

  - **Lectura de eventos próximos** en el calendario del usuario
  - **Sugerencias automáticas** de reservas de aparcamiento cerca de eventos programados
  - **Optimización de horarios** y ubicaciones basada en la agenda del usuario
  - **Alertas preventivas** antes de eventos importantes para recordar la reserva

### Beneficios

  - Reduce el tiempo de búsqueda manual de parking
  - Evita retrasos por no encontrar estacionamiento
  - Mejora la planificación del usuario
  - Integración transparente con herramientas ya usadas

---

## Sistema de Precios y Sanciones

### Precios Dinámicos

  - **Base de cálculo:** Los precios varían según:
    - Disponibilidad de plazas en tiempo real
    - Hora del día (peak hours vs off-peak)
    - Día de la semana
    - Configuración de la empresa

  - **Operación:**
    - La empresa indica los precios **base y máximos**
    - El sistema aplica **descuentos automáticos** si hay plazas libres
    - Se recalculan en tiempo real según demanda

### Sanciones por Incumplimiento

  - **Tolerancia:** 10 minutos de gracia después de la hora de salida programada
  - **Activación:** Si el usuario supera la tolerancia, se aplica automáticamente
  - **Monto:** Determinado por la empresa (configurables en el sistema)
  - **Registro:** Todas las sanciones quedan registradas en el perfil del usuario
  - **Avisos:** La app notifica al usuario de sanciones aplicadas

### Impacto en la Plataforma

| Beneficio para | Detalle |
|---|---|
| **Usuarios** | Precios justos según disponibilidad, incentivos por uso fuera de peak hours |
| **Empresas** | Optimización de ingresos, control de ocupación, reducción de infracciones |
| **Ciudades** | Reducción de congestión, mejora de movilidad urbana |

## Recursos

### Recursos Humanos

  - **2 Desarrolladores:** Nicolás Expósito Hernández y Alejandro Díaz Luis
  - **Testers:** Pruebas internas y externas
  - **Asesor/Coordinador:** Supervisor del proyecto

### Recursos Materiales

  - Ordenadores de desarrollo (2)
  - Smartphones para testeo (iOS y Android)
  - Servidores para pruebas (en nube o local)

### Recursos Técnicos

| Categoría | Herramientas |
|-----------|-------------|
| **Back-end** | Java, Spring Boot, SQLite, Maven |
| **Front-end** | React, React Native, Node.js, npm |
| **Desarrollo** | VS Code, GitHub, Git |
| **Testing** | JUnit, Jest, Postman |
| **Deployment** | Docker, servidor de pruebas |
| **Integraciones** | Google Maps API, Google Calendar API |

---

## Conclusiones

**NoMoreLaps** es un proyecto viable que resuelve un problema real en la movilidad urbana mediante:

  - Innovación tecnológica (precios dinámicos, integración de calendario)
  - Valor agregado (sanciones automáticas, descuentos inteligentes)
  - Multi-plataforma (web y móvil)
  - Modelo beneficioso para todas las partes (usuarios, empresas, ciudades)

El proyecto cuenta con los recursos, tecnología y equipo necesario para su desarrollo e implementación exitosa.

