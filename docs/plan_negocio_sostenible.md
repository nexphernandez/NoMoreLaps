# Plan de Marketing y Sostenibilidad: NoMoreLaps

## 1. Estrategia de Marketing y Posicionamiento

### 1.1. Análisis DAFO
Para entender la posición de NoMoreLaps en el mercado, realizamos un análisis de sus factores internos y externos:

| Fortalezas | Debilidades |
| :--- | :--- |
| Modelo de coste cero para el usuario final. | Dependencia de la adopción por parte de las empresas de parking. |
| Funcionamiento offline único en el mercado. | Equipo de desarrollo pequeño. |
| Integración nativa con Odoo ERP para gestión publicitaria. | Marca nueva sin reconocimiento inicial. |

| Oportunidades | Amenazas |
| :--- | :--- |
| Crecimiento de las Smart Cities y zonas de bajas emisiones. | Competidores consolidados (Telpark, Google Maps). |
| Posibilidad de acuerdos con Ayuntamientos. | Cambios en las normativas de tráfico urbanas. |
| Aumento del uso de vehículos compartidos que necesitan parking. | Inestabilidad en los ingresos por publicidad. |

### 1.2. Segmentación de Audiencia 
NoMoreLaps es una plataforma bilateral, por lo que tenemos dos públicos objetivo:

*   **B2C: Conductores Urbanos:** Hombres y mujeres de 20 a 60 años que utilizan el vehículo privado para desplazamientos laborales o de ocio. Buscan ahorrar tiempo, evitar el estrés y reducir el gasto en combustible.
*   **B2B: Propietarios y Gestores de Parking:** Dueños de parkings privados, hoteles con plazas sobrantes o administraciones públicas. Necesitan maximizar la ocupación y automatizar la gestión sin inversión en hardware costoso.

### 1.3. Marketing Mix 
*   **Producto:** Ecosistema digital integral (App, Web, ERP) para la gestión inteligente de plazas de aparcamiento con capacidades offline.
*   **Precio:** Estrategia "Ad-Supported Freemium". El servicio es gratuito para el usuario final, monetizando a través de espacios publicitarios dentro de la aplicación.
*   **Plaza:** Distribución global a través de tiendas de aplicaciones (Google Play, App Store) y servicios web para empresas.
*   **Promoción:** Marketing de guerrilla en zonas de alto tráfico, acuerdos con ayuntamientos, campañas de SEM y colaboraciones estratégicas.
*   **Personas:** Soporte técnico especializado para empresas y atención al usuario vía App.
*   **Procesos:** Flujo optimizado de reserva en menos de 30 segundos y sincronización instantánea Odoo-App.
*   **Prueba Física:** Interfaz premium con modo oscuro, notificaciones push personalizadas y señalética física en los parkings adheridos.

### 1.4. Estrategia de Monetización: Odoo Ads
El motor económico reside en la integración con el módulo de Publicidad de Odoo:
1.  **Venta de Espacios:** Las empresas locales compran espacios (Banners o Popups) en el ERP.
2.  **Segmentación Contextual:** Los anuncios se muestran según la ubicación del usuario (ej: anuncio de un restaurante cercano al parking reservado).
3.  **Revenue Stream:** Ingresos por impresión (CPM) y por clic (CPC).

### 1.5. Estrategia ASO/SEO y KPIs
*   **ASO:** Optimización de palabras clave ("aparcar fácil", "reserva parking") y capturas de pantalla para tiendas de apps.
*   **KPIs de Negocio:** CAC (Coste de Adquisición), LTV (Lifetime Value), Churn Rate (Tasa de abandono) y Uptime de anuncios en Odoo.

---

## 2. Estrategia de Sostenibilidad y Viabilidad

### 2.1. Sostenibilidad Económica (Viabilidad)
*   **Costes Fijos:** Servidores (600€/año), Dominios (50€/año), Mantenimiento técnico (2000€/año).
*   **Costes Variables:** Escalado de infraestructura Cloud según tráfico y soporte técnico bajo demanda.
*   **Fuentes de Ingresos:** Principalmente publicidad gestionada desde Odoo Ads.
*   **Umbral de Rentabilidad:** Se alcanza con un volumen de 5.000 usuarios activos mensuales (MAU).

### 2.2. Sostenibilidad Ambiental 
*   **Green Coding:** Código optimizado en Java y TypeScript para reducir el consumo de CPU y batería. Uso estratégico del modo WAL en SQLite para minimizar el impacto en el almacenamiento.
*   **Infraestructura Eco-eficiente:** Despliegue en centros de datos con metas de energía 100% renovable.
*   **Impacto Ambiental:** El sistema contribuye a la reducción de la huella de carbono al minimizar el tráfico de agitación en las ciudades.

### 2.3. Sostenibilidad Social y Ética
*   **RGPD:** Cifrado de contraseñas con BCrypt, anonimización de datos y transparencia en el tratamiento de la ubicación.
*   **Accesibilidad:** Diseño alineado con la norma ISO 9241 y pautas WCAG para asegurar el acceso a personas con diversidad funcional.

## 3. Alineación con los ODS (Objetivos de Desarrollo Sostenible)
| ODS | Justificación |
| :--- | :--- |
| **ODS 11: Ciudades Sostenibles** | Optimiza la movilidad y reduce la congestión urbana. |
| **ODS 13: Acción por el Clima** | Disminuye las emisiones de CO2 al reducir el tiempo de búsqueda de parking. |
| **ODS 9: Industria e Innovación** | Digitaliza infraestructuras urbanas tradicionales mediante tecnología IoT y Software. |

## 4. Análisis IRO (Impactos, Riesgos y Oportunidades)
Análisis de materialidad (Probabilidad x Impacto). Umbral > 12 = Material.

| Tipo | Descripción | Prob. | Imp. | Total | Material |
| :--- | :--- | :---: | :---: | :---: | :---: |
| **Impacto (+)** | Reducción de huella de carbono urbana. | 5 | 4 | **20** | **SÍ** |
| **Riesgo (-)** | Dependencia de infraestructura Cloud externa. | 4 | 3 | **12** | **SÍ** |
| **Oportunidad (+)** | Alianzas con Gobiernos Locales (Smart Cities). | 3 | 5 | **15** | **SÍ** |
| **Impacto (+)** | Mejora de la accesibilidad (gestión plazas PMR). | 4 | 3 | **12** | **SÍ** |
| **Riesgo (-)** | Brecha de seguridad en datos de ubicación. | 2 | 5 | **10** | NO |
