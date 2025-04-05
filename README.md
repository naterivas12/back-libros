# Web Libro - API REST con Spring Boot y MongoDB

API REST para gestión de libros y autores desarrollada con Spring Boot WebFlux y MongoDB.

## Requisitos Previos

- Java 17 o superior
- Maven
- MongoDB Atlas (la conexión ya está configurada)

## Instalación

1. Clonar el repositorio:
```bash
git clone git@github.com:naterivas12/back-libros.git
cd weblibro
```

2. Ejecutar la aplicación:
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints Disponibles

### Autores

```
GET    /api/authors      - Obtener todos los autores
GET    /api/authors/{id} - Obtener un autor específico
POST   /api/authors      - Crear un nuevo autor
PUT    /api/authors/{id} - Actualizar un autor
DELETE /api/authors/{id} - Eliminar un autor
```

Ejemplo de creación de autor:
```json
POST http://localhost:8080/api/authors
{
    "name": "Gabriel García Márquez",
    "gender": "Masculino"
}
```

### Libros

```
GET    /api/books      - Obtener todos los libros
GET    /api/books/{id} - Obtener un libro específico
POST   /api/books      - Crear un nuevo libro
PUT    /api/books/{id} - Actualizar un libro
DELETE /api/books/{id} - Eliminar un libro
```

Ejemplo de creación de libro:
```json
POST http://localhost:8080/api/books
{
    "title": "Cien años de soledad",
    "description": "Una saga familiar que narra la historia de los Buendía en Macondo",
    "year": 1967,
    "authorId": "ID_DEL_AUTOR",
    "published": true
}
```

### Seed de Datos

Para inicializar la base de datos con datos de ejemplo (5 autores y 10 libros):

```
POST http://localhost:8080/api/seed
```

## Estructura de Datos

### Autor (Author)
- ID (generado automáticamente)
- Nombre (name)
- Género (gender)

### Libro (Book)
- ID (generado automáticamente)
- Título (title)
- Descripción (description)
- Año (year)
- ID del autor (authorId)
- Publicado (published)
- Fecha de registro (registrationDate)

## Tecnologías Utilizadas

- Spring Boot 3.x
- Spring WebFlux
- MongoDB
- Project Reactor
- Maven