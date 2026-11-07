# Empleados con Spring Profiles

API REST para administrar empleados y demostrar configuraciones independientes mediante `dev`, `qa`, `test` y `prod`. La aplicación utiliza Spring Boot 2.7.18 para mantener compatibilidad con Java 8, como en el entorno académico indicado.

## Requisitos

- JDK 8 o superior compatible con Spring Boot 2.7
- Maven 3.6+
- Docker Desktop (opcional, para levantar MySQL local)

## Base de datos local

Levanta MySQL con las cuatro bases de datos aisladas:

```bash
docker compose up -d
```

Esto crea `empleados_dev`, `empleados_qa`, `empleados_test` y `empleados_prod` en el contenedor local. Para uso fuera del laboratorio, cambia las credenciales de ejemplo y almacénalas en un gestor de secretos.

## Ejecutar por perfil

Los valores por defecto ya apuntan al puerto y base de datos correctos para cada perfil. En terminal PowerShell:

```powershell
$env:PROFILE_ACTIVE = 'dev'
$env:DB_USER = 'root'
$env:DB_PASSWORD = 'laboratorio'
./mvnw.cmd spring-boot:run
```

Para cambiar de ambiente, sustituye `PROFILE_ACTIVE` por `qa`, `test` o `prod`. En IDE configura las variables de entorno de la misma forma. También puedes usar `SPRING_PROFILES_ACTIVE` (propiedad nativa de Spring Boot). `PROFILE_ACTIVE` prevalece como selector por defecto del proyecto. `SERVER_PORT`, `DB_URL`, `DB_USER` y `DB_PASSWORD` permiten sobreescribir individualmente los valores.

| Perfil | Puerto | Base de datos | `empresa.ambiente` |
|---|---:|---|---|
| dev | 8080 | empleados_dev | DESARROLLO |
| qa | 8082 | empleados_qa | CALIDAD |
| test | 8084 | empleados_test | PRUEBAS |
| prod | 8086 | empleados_prod | PRODUCCION |

Las bases de datos deben existir antes de iniciar la aplicación. Hibernate usa `update` en dev/qa/test y `validate` en prod. No se incluyen contraseñas reales.

## API

- `POST /empleados` crea un empleado (201; cuerpo JSON con `nombres`, `apellidos`, `email`, `sueldo`).
- `GET /empleados` lista empleados.
- `GET /empleados/{id}` consulta uno (404 si no existe).
- `PUT /empleados/{id}` reemplaza los datos editables (404 si no existe).
- `DELETE /empleados/{id}` elimina (204; 404 si no existe).
- `GET /configuracion` informa ambiente, perfil activo, puerto y base de datos seleccionada.

Ejemplo:

```json
{"nombres":"Ana","apellidos":"Pérez","email":"ana@example.com","sueldo":2500.00}
```

Respuesta de `GET /configuracion` en dev:

```json
{"ambiente":"DESARROLLO","perfil":"dev","puerto":"8080","baseDatos":"empleados_dev"}
```

## Pruebas

```bash
./mvnw.cmd test
```

Las pruebas de controlador validan las rutas CRUD y la respuesta de configuración sin requerir una conexión MySQL. Para comprobar persistencia real, usa Postman con Docker Compose levantado y ejecuta las peticiones sobre cada puerto.

## Observaciones

1. El perfil activo se selecciona al iniciar la aplicación; cambiar variables durante la ejecución no cambia dinámicamente el perfil de un proceso ya iniciado.
2. Cada perfil apunta a una base de datos distinta para evitar que pruebas o cambios de desarrollo afecten producción.
3. La base de datos y las credenciales deben estar disponibles antes del arranque; si el servidor MySQL no responde, Spring no completa la inicialización.
4. Producción valida el esquema y no lo modifica; por ello, una tabla ausente o incompatible impide el inicio. El script `database/init.sql` crea también la tabla inicial requerida por el perfil prod.
5. Los puertos 8080, 8082, 8084 y 8086 deben estar libres; una colisión de puerto produce un error de arranque.

## Conclusiones

1. Spring Profiles permite mantener una sola aplicación y seleccionar valores apropiados para cada ambiente.
2. Externalizar URL, usuario, contraseña y puerto facilita el despliegue sin cambiar el código fuente.
3. La separación de bases de datos reduce el riesgo de mezclar información entre desarrollo, calidad, pruebas y producción.
4. `@Value` permite inyectar propiedades puntuales y `Environment` permite consultar el perfil y sus propiedades activas.
5. Probar tanto las respuestas REST como el arranque con bases de datos reales ayuda a detectar errores de configuración antes del despliegue.




## Pruebas Postman

Importa la colección postman/Empleados.postman_collection.json y el environment que corresponde al perfil desde postman/environments. Ejecuta la colección en orden para que el ID creado se use en lectura, actualización y eliminación. La guía detallada está en postman/README.md.

