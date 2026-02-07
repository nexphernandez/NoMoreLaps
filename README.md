# NoMoreLaps

![NoMoreLaps Logo](Images/Logo.png)

NoMoreLaps es una plataforma de movilidad urbana compuesta por un ecosistema de aplicaciones que comparten una misma API backend y conviven dentro de un único repositorio GitHub, facilitando el desarrollo, mantenimiento y despliegue del sistema.

La plataforma está formada por:

* Aplicación móvil para usuarios finales, orientada a la búsqueda y reserva de plazas de aparcamiento.

* Aplicación de gestión para empresas, enfocada en la administración y monetización de aparcamientos privados.

## API REST centralizada

La API actúa como núcleo del sistema, proporcionando:

* Autenticación y autorización mediante JWT
* Gestión de usuarios, empresas, aparcamientos y reservas
* Comunicación segura y documentada (Swagger/OpenAPI)
* Soporte para múltiples clientes desde un único backend

## Aplicación de Usuario

Permite a los conductores:

* Localizar aparcamientos cercanos mediante GPS
* Consultar disponibilidad en tiempo real
* Reservar plazas de forma anticipada
* Gestionar su perfil y reservas activas

## Aplicación de Empresa

Permite a las empresas:

* Publicar y gestionar sus plazas de aparcamiento
* Definir horarios, disponibilidad y precios
* Visualizar reservas activas e históricas
* Obtener métricas de ocupación e ingresos
* Rentabilizar espacios infrautilizados sin inversión adicional

## Impacto

* Reduce recorridos innecesarios y emisiones contaminantes
* Optimiza el uso de infraestructuras existentes
* Mejora la eficiencia operativa de empresas y usuarios
