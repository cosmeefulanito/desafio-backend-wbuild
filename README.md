# Desafio backend

Este proyecto es un cliente de clima que consume datos de la API de OpenWeatherMap y expone un API GraphQL utilizando Spring Boot, Kotlin con Graphql

## Requisitos de instalacion

* Java 17
* Maven 3.8.x
* Kotlin 1.8
* git

### Configurar proyecto
Clonar el repositorio en tu maquina local utilizando git:
```git
git clone https://github.com/miusuario/desafio-backend-wbuild.git
```

Configurar el archivo `application.properties`
```git
openweathermap.api.key=MI_API_KEY
openweathermap.api.url=http://api.openweathermap.org/data/2.5/weather 
```
Para obtener la API key key debes crearte una cuenta en https://openweathermap.org/ . Una vez registrado podrás encontrar tu API key en tu perfil. Si lo prefieres puedes crear una nueva key.

### Compilar el proyecto
Compilar el proyecto para descargar todas las dependencias necesarias:
````
./mvnw clean install
````

### Ejecutar la aplicacion
````
./mvnw spring-boot:run
````