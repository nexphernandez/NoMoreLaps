# Memoria del Proyecto: NoMoreLaps

## Índice
1. Identificación del Reto y Análisis del Sector
2. Planteamiento de la Solución y Viabilidad
3. Estrategia de Marketing y Posicionamiento
4. Plan de Sostenibilidad y Responsabilidad Social
5. Planificación y Gestión del Proyecto
6. Ejecución Técnica (Pasar a la Acción)
7. Conclusiones y Contribución a los ODS
8. Anexo I: Análisis IRO (Impactos, Riesgos y Oportunidades)

---

## 1. Identificación del Reto y Análisis del Sector
### 1.1. Definición del Reto
El reto planteado consiste en la digitalización y optimización de la gestión de plazas de aparcamiento en entornos urbanos saturados, eliminando las ineficiencias del modelo tradicional manual y reduciendo el impacto ambiental del tráfico de agitación.

### 1.2. Análisis del Sector y DAFO
Se identifica una carencia tecnológica en las medianas y pequeñas empresas de parking. NoMoreLaps democratiza esta tecnología mediante un ecosistema SaaS.

| Fortalezas | Debilidades |
| :--- | :--- |
| Modelo de coste cero para el usuario. | Marca nueva sin reconocimiento. |
| Funcionamiento offline único. | Equipo de desarrollo pequeño. |

| Oportunidades | Amenazas |
| :--- | :--- |
| Crecimiento de Smart Cities. | Competidores como Telpark o Google. |
| Acuerdos con Ayuntamientos. | Cambios en normativas de tráfico. |

### 1.3. Roles del Equipo
*   **Gestión y Backend:** Arquitectura de microservicios y lógica de negocio.
*   **Frontend y Mobile:** Experiencia de usuario y persistencia local (SQLite).
*   **Integración y QA:** Conexión con Odoo ERP y validación de calidad.

## 2. Planteamiento de la Solución y Viabilidad
### 2.1. Solución Propuesta
Un ecosistema intermodular compuesto por una API Spring Boot, un Dashboard Angular para empresas y una App React Native para usuarios, con integración en Odoo ERP para la gestión comercial.

### 2.2. Viabilidad Económica y Presupuesto
*   **Costes Fijos:** 2.650€/año (Servidores, dominios, mantenimiento).
*   **Presupuesto Desarrollo:** 9.000€ (estimación de horas de ingeniería).
*   **Umbral de Rentabilidad:** Alcanzado con 5.000 usuarios activos mensuales (MAU).

## 3. Estrategia de Marketing y Posicionamiento
### 3.1. Segmentación (Target)
*   **B2C:** Conductores urbanos que buscan ahorro de tiempo y reducción de estrés.
*   **B2B:** Gestores de parkings que necesitan digitalizar su oferta sin inversión en hardware.

### 3.2. Marketing Mix (7Ps)
*   **Producto:** Ecosistema inteligente de reserva con capacidades offline.
*   **Precio:** Modelo "Ad-Supported Freemium" (gratis para el usuario).
*   **Promoción:** SEO/ASO, marketing local en parkings y campañas segmentadas.
*   **Procesos:** Reserva rápida (<30s) y sincronización automática.

### 3.3. Monetización: Odoo Ads
Uso del ERP Odoo para la venta de espacios publicitarios segmentados contextualmente por la ubicación del parking reservado.

## 4. Plan de Sostenibilidad y Responsabilidad Social
### 4.1. Sostenibilidad Ambiental
*   **Green Coding:** Optimización de algoritmos para reducir consumo de CPU.
*   **Eficiencia Energética:** Uso de infraestructura Cloud eco-eficiente y Modo Oscuro nativo.

### 4.2. Sostenibilidad Social y Ética
*   **RGPD:** Cifrado de datos sensible y transparencia en el uso de geolocalización.
*   **Accesibilidad:** Cumplimiento de pautas WCAG y norma ISO 9241.

## 5. Planificación y Gestión del Proyecto
Se ha seguido una metodología ágil (Scrum/Sprints):
1.  **Sprint 1:** Arquitectura base y persistencia.
2.  **Sprint 2:** Integración con Odoo y servicios SOAP/REST.
3.  **Sprint 3:** Desarrollo App móvil y modo offline.
4.  **Sprint 4:** Dashboard Web y mapas interactivos.

## 6. Ejecución Técnica: Pasar a la Acción
*   **Capa de Datos:** PostgreSQL con arquitectura hexagonal.
*   **Movilidad:** Caché SQLite (WAL Mode) para garantizar operatividad en subterráneos.
*   **Gestión:** Panel administrativo con geocodificación inversa vía Leaflet.

## 7. Conclusiones y Contribución a los ODS
| ODS | Justificación |
| :--- | :--- |
| **ODS 11** | Mejora la movilidad urbana y reduce la congestión. |
| **ODS 13** | Reduce emisiones de CO2 al optimizar las rutas de parking. |
| **ODS 9** | Fomenta la innovación en infraestructuras tradicionales. |

## 8. Anexo I: Análisis IRO
Materialidad (Probabilidad x Impacto). Umbral > 12 = Material.

| Tipo | Descripción | Prob. | Imp. | Total | Material |
| :--- | :--- | :---: | :---: | :---: | :---: |
| **Impacto (+)** | Reducción huella de carbono urbana. | 5 | 4 | **20** | **SÍ** |
| **Oportunidad (+)** | Alianzas con Smart Cities. | 3 | 5 | **15** | **SÍ** |
| **Riesgo (-)** | Dependencia infraestructura Cloud. | 4 | 3 | **12** | **SÍ** |
| **Impacto (+)** | Mejora accesibilidad (plazas PMR). | 4 | 3 | **12** | **SÍ** |
