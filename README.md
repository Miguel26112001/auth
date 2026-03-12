# 🔐 Authentication Service Experiment

Un servicio de autenticación experimental construido con **Spring Boot 4.0.1** que proporciona un sistema robusto de gestión de usuarios, autenticación y autorización.

## 📋 Descripción del Proyecto

Este proyecto es una aplicación de **Spring Boot** que implementa un servicio de autenticación completo con características avanzadas como:

- ✅ Registro e inicio de sesión de usuarios
- ✅ Verificación de usuarios por correo electrónico
- ✅ Gestión de roles y permisos
- ✅ Hasheo seguro de contraseñas
- ✅ Generación de tokens de autenticación
- ✅ API REST completamente documentada con Swagger
- ✅ Persistencia en base de datos PostgreSQL
- ✅ Envío de correos electrónicos de verificación
- ✅ Auditoría de datos (JPA Auditing)
- ✅ Validación de entrada
- ✅ Seguridad con Spring Security

## 🏗️ Arquitectura

El proyecto está organizado siguiendo principios de **arquitectura limpia** y **Domain-Driven Design (DDD)**:

```
src/main/java/com/example/authentication/
├── iam/                                    # Bounded Context principal
│   ├── application/
│   │   └── internal/
│   │       ├── commandservices/           # Servicios de dominio (escritura)
│   │       └── queryservices/             # Servicios de dominio (lectura)
│   ├── domain/
│   │   ├── exceptions/                    # Excepciones de negocio
│   │   ├── model/
│   │   │   ├── aggregates/                # Agregados del dominio
│   │   │   ├── entities/                  # Entidades del dominio
│   │   │   ├── valueobjects/              # Objetos de valor
│   │   │   ├── commands/                  # Comandos CQRS
│   │   │   └── queries/                   # Queries CQRS
│   │   └── services/                      # Servicios de dominio
│   ├── infrastructure/
│   │   ├── authorization/                 # Configuración de seguridad
│   │   ├── email/                         # Servicio de envío de emails
│   │   ├── hashing/                       # Algoritmos de hasheo
│   │   ├── persistence/                   # Repositorios JPA
│   │   └── tokens/                        # Generación de tokens
│   └── interfaces/
│       └── rest/
│           ├── controllers/                # REST Controllers
│           ├── exceptions/                 # Mapeos de excepciones
│           ├── resources/                  # DTOs para respuestas
│           └── transform/                  # Mappers DTO ↔ Dominio
└── shared/                                # Código compartido
    ├── domain/
    │   └── model/                         # Modelos compartidos
    ├── infrastructure/
    │   ├── documentation/                 # Configuración de Swagger
    │   └── persistence/                   # Configuración JPA
    └── interfaces/
        └── rest/                          # Controladores compartidos
```

## 🛠️ Tecnologías

### Backend
- **Spring Boot 4.0.1** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **Spring Mail** - Envío de correos electrónicos
- **Spring Validation** - Validación de datos
- **Spring Actuator** - Monitoreo y health checks
- **Thymeleaf** - Motor de plantillas HTML

### Base de Datos
- **PostgreSQL** - Base de datos relacional

### Documentación
- **Springdoc OpenAPI (Swagger)** - Documentación interactiva de la API

### Utilidades
- **Lombok** - Reducción de código boilerplate
- **Java 21** - Versión de Java

## 🚀 Funcionalidades Principales

### Autenticación
- **Registro de usuarios** - Endpoint `POST /api/v1/authentication/sign-up`
- **Login** - Endpoint `POST /api/v1/authentication/login`
- **Verificación por email** - Endpoint `GET /api/v1/authentication/verify`

### Gestión de Roles
- **Obtener todos los roles** - Endpoint `GET /api/v1/roles`
- **Obtener rol por nombre** - Endpoint `GET /api/v1/roles/{name}`
- **Acceso restringido a administradores** - Anotación `@PreAuthorize("hasRole('ADMIN')")`

### Seguridad
- Hasheo seguro de contraseñas
- Validación de tokens de verificación
- Control de acceso basado en roles (RBAC)
- Spring Security integrado

### Email
- Envío de correos de verificación
- Plantillas HTML customizadas
- Soporte para múltiples proveedores (Gmail, Brevo, etc.)

## 📦 Dependencias Principales

```xml
<!-- Spring Boot Starters -->
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-validation
spring-boot-starter-webmvc
spring-boot-starter-mail
spring-boot-starter-thymeleaf
spring-boot-starter-actuator

<!-- Base de Datos -->
postgresql

<!-- Utilidades -->
lombok

<!-- Documentación API -->
springdoc-openapi (Swagger)
```

## ⚙️ Configuración

### Propiedades de Aplicación

El proyecto usa perfiles de Spring para diferenciar configuraciones:

- **development** (`application-dev.properties`) - Para desarrollo local
- **production** (`application-prod.properties`) - Para producción
- **default** (`application.properties`) - Configuración base

### Variables de Entorno Requeridas

```properties
# Base de Datos
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/auth_db
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=contraseña

# Correo Electrónico
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=tu-email@gmail.com
MAIL_PASSWORD=tu-contraseña-app
MAIL_FROM=noreply@authentication.com

# Servidor
SERVER_PORT=8090
SPRING_PROFILES_ACTIVE=dev
```

## 🏃 Cómo Ejecutar

### Requisitos Previos
- Java 21 o superior
- PostgreSQL 12 o superior
- Maven 3.8 o superior

### Pasos para Ejecutar

1. **Clonar/Descargar el proyecto**
   ```bash
   cd F:\IdeaProjects\auth
   ```

2. **Configurar las variables de entorno**
   - Editar `application-dev.properties` o crear un archivo `.env`
   - Configurar la base de datos PostgreSQL
   - Configurar las credenciales de correo electrónico

3. **Ejecutar con Maven**
   ```bash
   mvn clean spring-boot:run
   ```

4. **Acceder a la aplicación**
   - API: http://localhost:8090/api
   - Documentación Swagger: http://localhost:8090/swagger-ui.html
   - Health Check: http://localhost:8090/actuator/health

## 📚 Endpoints Principales

### Autenticación

```
POST /api/v1/authentication/sign-up
- Registra un nuevo usuario
- Body: { username, email, password, roleId }

POST /api/v1/authentication/login
- Inicia sesión
- Body: { username, password }
- Response: { token, user }

GET /api/v1/authentication/verify?token=<token>
- Verifica un usuario usando token
- Response: HTML (success/error)
```

### Roles

```
GET /api/v1/roles
- Obtiene todos los roles (requiere ADMIN)
- Response: [ { id, name, description } ]

GET /api/v1/roles/{name}
- Obtiene un rol por nombre (requiere ADMIN)
- Response: { id, name, description }
```

## 📖 Documentación API

Una vez que la aplicación esté en ejecución, la documentación interactiva de la API está disponible en:

```
http://localhost:8090/swagger-ui.html
```

O también en el formato JSON:

```
http://localhost:8090/v3/api-docs
```

## 🧪 Testing

Ejecutar tests:

```bash
mvn test
```

La carpeta `src/test/` contiene tests para:
- Controllers REST
- Servicios de aplicación
- Excepciones y validaciones

## 📋 Control de Calidad

El proyecto incluye verificaciones de calidad de código:

```bash
# Ejecutar análisis de estilo de código
mvn checkstyle:check

# Generar reporte
mvn checkstyle:checkstyle
```

## 🔒 Seguridad

- ✅ Contraseñas hasheadas con algoritmos seguros
- ✅ Spring Security para autenticación y autorización
- ✅ Tokens de verificación seguros
- ✅ Validación de entrada en todos los endpoints
- ✅ CORS configurado
- ✅ Protección contra CSRF (cuando aplique)

## 📞 Soporte y Mantenimiento

- **Descripción**: Experiment project to improve authentication
- **Versión**: 0.0.1-SNAPSHOT
- **Estado**: En desarrollo

## 📝 Notas de Desarrollo

- El proyecto utiliza **JPA Auditing** para registrar fechas de creación/modificación
- Las convenciones de nombres de tablas siguen el patrón **snake_case** pluralizado
- La zona horaria se configura a **UTC**
- Los logs de Spring Mail están en nivel **DEBUG** para desarrollo

## 🎯 Próximos Pasos

- [ ] Implementar refresh tokens
- [ ] Agregar autenticación OAuth2
- [ ] Implementar 2FA
- [ ] Agregar rate limiting
- [ ] Implementar caching
- [ ] Agregar más tests de integración
- [ ] Configurar CI/CD

---

**Última actualización**: Marzo 2026

