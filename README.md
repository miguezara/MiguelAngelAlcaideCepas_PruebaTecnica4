# Sistema de Gestión de Reservas de Vuelos y Hoteles

Este proyecto es un sistema de gestión de reservas de vuelos y hoteles desarrollado en Java con Spring Boot. Permite a los usuarios realizar reservas de vuelos y habitaciones de hotel, así como gestionar clientes, vuelos, hoteles y reservas.

## Características

- Registro y gestión de clientes.
- Registro y gestión de vuelos.
- Registro y gestión de hoteles y habitaciones.
- Reserva de vuelos.
- Reserva de habitaciones de hotel.

## Tecnologías Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (para desarrollo)
- PostgreSQL (para producción)
- Lombok
- Jackson
- Maven
- Security

## Estructura del Proyecto

El proyecto sigue una estructura de paquetes estándar para una aplicación Spring Boot:

- `controller`: Controladores REST para gestionar las solicitudes HTTP.
- `model`: Entidades JPA que representan las tablas de la base de datos.
- `repository`: Repositorios que manejan la interacción con la base de datos.
- `service`: Servicios que implementan la lógica de negocio.
- `DTO`: Objetos de transferencia de datos para las solicitudes y respuestas REST.

## Instalación y Uso

1. Clona este repositorio en tu máquina local.
2. Asegúrate de tener instalado Java y Maven.
3. Configura la base de datos en `application.properties`.
4. Ejecuta la aplicación con el comando `mvn spring-boot:run`.
5. Accede a `http://localhost:8080` en tu navegador para utilizar la aplicación.

## Endpoints REST

- `GET /clients`: Obtiene todos los clientes.
- `GET /clients/{dni}`: Obtiene un cliente por su DNI.
- `POST /clients`: Crea un nuevo cliente.
- `PUT /clients/{dni}`: Actualiza un cliente existente.
- `DELETE /clients/{dni}`: Elimina un cliente por su DNI.

(Continuaría con los endpoints REST para vuelos, hoteles y reservas)

## Contribución

Las contribuciones son bienvenidas. Si deseas contribuir a este proyecto, sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/feature-name`).
3. Realiza tus cambios y haz commit de ellos (`git commit -am 'Add new feature'`).
4. Haz push de la rama (`git push origin feature/feature-name`).
5. Crea un nuevo Pull Request.

## Licencia

Este proyecto está bajo la licencia [MIT](LICENSE).
