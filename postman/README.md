# Ejecutar pruebas con Postman

1. Importa `Empleados.postman_collection.json` y un environment de `environments` que coincida con el perfil iniciado.
2. Inicia la aplicación con ese perfil y puerto. La URL de Postman debe apuntar a la misma máquina donde corre Spring Boot.
3. Selecciona el environment importado y ejecuta la colección en orden con **Run**. La petición de creación guarda el ID; las siguientes consultan, actualizan y eliminan ese empleado.
4. Revisa el panel de resultados: cada petición incluye aserciones automáticas para el código HTTP y el contenido de la respuesta.

La colección cubre `GET /configuracion`, las cinco rutas CRUD, el `404` al consultar un empleado eliminado y el `400` al enviar un empleado sin campos obligatorios.

## URLs por perfil

| Environment | `baseUrl` |
|---|---|
| DEV | `http://127.0.0.1:8080` |
| QA | `http://127.0.0.1:8082` |
| TEST | `http://127.0.0.1:8084` |
| PROD | `http://127.0.0.1:8086` |

La aplicación que quedó iniciada para esta sesión responde en `http://127.0.0.1:8080/configuracion` con el perfil DEV. Este equipo no tenía Docker activo; para mantener aislado el entorno, la ejecución local usa una instancia temporal de MariaDB en `127.0.0.1:3307` con base `empleados_dev`. Los archivos Compose del proyecto usan el puerto MySQL estándar `3306`.

Para probar otro perfil con la instancia temporal de esta sesión, abre otra terminal PowerShell en la raíz del proyecto y configura, por ejemplo, QA:

```powershell
$env:PROFILE_ACTIVE = 'qa'
$env:SERVER_PORT = '8082'
$env:DB_URL = 'jdbc:mysql://127.0.0.1:3307/empleados_qa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
$env:DB_USER = 'root'
$env:DB_PASSWORD = 'laboratorio'
.\mvnw.cmd spring-boot:run
```

Para TEST o PROD, cambia el perfil, puerto y nombre de base de datos según la tabla del README. PROD valida el esquema existente.
