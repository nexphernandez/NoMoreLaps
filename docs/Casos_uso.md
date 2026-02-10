## Diagrama de casos de uso

<img src="../Images/Diagramas/CasosDeUso.drawio.png">

## Especificación de Actores y Operaciones

### Actores

##### Usuario No Registrado
Actor	Usuario No Registrado
Descripción	Persona que utiliza la aplicación para consultar aparcamientos sin tener una cuenta.
Características	Acceso limitado
Relaciones	Usuario Registrado
Referencias	Registrarse, buscar aparcamientos
Notas	No puede reservar
Autor	Nicolás Expósito Hernández
Fecha	19/03/2025

Usuario Registrado
Actor	Usuario Registrado
Descripción	Usuario con cuenta que puede reservar plazas de aparcamiento.
Características	Autenticado mediante JWT
Relaciones	Usuario No Registrado
Referencias	Reservar plaza, gestionar reservas, sanciones
Notas	Puede sincronizar calendario
Autor	Nicolás Expósito Hernández
Fecha	19/03/2025


Empresa
Actor	Empresa
Descripción	Empresa propietaria de aparcamientos que gestiona plazas y precios.
Características	Acceso a panel de gestión
Relaciones	Usuario Registrado
Referencias	Gestionar aparcamientos, precios y reservas
Notas	Puede usar la API sin la app
Autor	Nicolás Expósito Hernández
Fecha	19/03/2025


Administrador del Sistema
Actor	Administrador
Descripción	Responsable del mantenimiento y control de la plataforma.
Características	Acceso total
Relaciones	Empresa, Usuario
Referencias	Supervisar sistema
Notas	Uso interno
Autor	Nicolás Expósito Hernández
Fecha	19/03/2025