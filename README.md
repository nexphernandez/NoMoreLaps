# NoMoreLaps

![NoMoreLaps Logo](images/Logo.png)

NoMoreLaps es una plataforma de movilidad urbana inteligente orientada a la optimización del uso de plazas de aparcamiento privadas, basada en un ecosistema de aplicaciones que comparten una API REST centralizada.
Todo el sistema convive dentro de un único repositorio GitHub, facilitando el desarrollo, mantenimiento y despliegue del proyecto.

La plataforma está diseñada para que empresas propietarias o gestoras de parkings integren la API en sus propios sistemas, permitiendo ofrecer información de disponibilidad y precios en tiempo real a los usuarios finales.

## Arquitectura general

La plataforma está formada por:

* Aplicación móvil para usuarios finales, orientada a la búsqueda, reserva y gestión de plazas de aparcamiento.

* Aplicación de gestión para empresas, enfocada en la administración, monitorización y monetización de aparcamientos.

* API REST centralizada, que actúa como núcleo del sistema y punto de integración para terceros.

## API REST centralizada

La API constituye el eje principal del proyecto y proporciona:

* Autenticación y autorización mediante JWT

* Gestión de usuarios, empresas, aparcamientos, reservas y sanciones

* Consulta de disponibilidad de plazas en tiempo casi real

* Gestión de precios dinámicos en función de:

  * Disponibilidad del parking

  * Franja horaria

  * Políticas definidas por la empresa

* Integración con calendarios personales de los usuarios para anticipar necesidades de aparcamiento

* Comunicación segura y documentada mediante Swagger / OpenAPI

* Soporte para múltiples clientes desde un único backend

La API está pensada para ser consumida tanto por las aplicaciones propias de NoMoreLaps como por sistemas externos de empresas colaboradoras.

## Aplicación de Usuario

La aplicación móvil permite a los conductores:

* Localizar aparcamientos cercanos mediante GPS

* Consultar disponibilidad de plazas en tiempo real

* Visualizar precios dinámicos según hora y ocupación

* Reservar plazas de forma anticipada

* Sincronizar la aplicación con su calendario personal para recibir sugerencias automáticas de aparcamiento

* Gestionar su perfil y reservas activas

* Recibir avisos y posibles sanciones en caso de incumplimiento del horario de reserva (por ejemplo, no ocupar la plaza tras un margen de cortesía)

## Aplicación de Empresa

La aplicación de gestión permite a las empresas:

* Registrar y administrar uno o varios aparcamientos

* Definir horarios, disponibilidad y precios base

* Establecer rangos de precios y porcentajes máximos de descuento

* Visualizar reservas activas e históricas

* Consultar métricas de ocupación e ingresos

* Gestionar penalizaciones por incumplimiento de reservas

* Rentabilizar espacios infrautilizados sin inversión adicional

* Integrarse directamente con la API de NoMoreLaps desde sus propios sistemas

## Diferenciación del proyecto

NoMoreLaps se diferencia de otras soluciones de aparcamiento por:

* Su modelo B2B2C, donde las empresas integran la API directamente

* La aplicación de precios dinámicos controlados por las propias empresas

* El uso del calendario del usuario para anticipar la demanda de aparcamiento

* Un sistema de reservas con control de cumplimiento y sanciones

* Arquitectura escalable y desacoplada basada en una API central

## Impacto

* Reduce recorridos innecesarios y emisiones contaminantes

* Optimiza el uso de infraestructuras de aparcamiento existentes

* Mejora la experiencia del conductor en entornos urbanos

* Incrementa la eficiencia operativa y los ingresos de las empresas
