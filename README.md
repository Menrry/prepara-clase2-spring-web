# -----------------------------------------------------------
# Documentar un proyecto Spring Boot con Java 21 utilizando 
# la última versión estable de: 
##    org.springdoc:springdoc-openapi-starter-webmvc-ui 
##         Es un proceso relativamente sencillo. 
# -----------------------------------------------------------
# Aquí tienes los pasos a seguir:

# Paso 1: Añadir la Dependencia de Springdoc OpenAPI

## Maven (pom.xml):

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version> 
</dependency>

## Después de añadir la dependencia, asegúrate de que tu sistema de construcción descargue 
## las nuevas dependencias, como por ejemplo, ejecutando "mvn clean install" para Maven.

# Paso 2: Ejecutar tu Aplicación Spring Boot

Una vez que la dependencia de Springdoc OpenAPI esté en tu classpath, ejecuta tu aplicación Spring Boot normalmente.

# Paso 3: Acceder a la Documentación Generada

Springdoc OpenAPI genera automáticamente la documentación de tu API en formato OpenAPI 3.0. Puedes acceder a la 
interfaz de usuario de Swagger UI (que permite explorar y probar tu API) a través de la siguiente URL, asumiendo 
que tu aplicación se ejecuta en el puerto 8080:

# http://localhost:8080/swagger-ui/index.html

## Si has configurado un server.servlet.context-path diferente en tu application.properties o application.yml, 
## deberás incluirlo en la URL. Por ejemplo, si tu context path es /api, la URL sería:

# http://localhost:8080/api/swagger-ui/index.html

## También puedes acceder a la definición de la API en formato JSON o YAML a través de las siguientes URLs:
JSON: http://localhost:8080/v3/api-docs (o /api/v3/api-docs si tienes un context path)
YAML: http://localhost:8080/v3/api-docs.yaml (o /api/v3/api-docs.yaml si tienes un context path)

# Paso 4: Personalizar la Documentación (Opcional pero Recomendado)

Aunque Springdoc OpenAPI genera documentación automáticamente, es importante personalizarla para que sea 
más informativa y útil para los consumidores de tu API. Puedes usar varias anotaciones proporcionadas por 
Springdoc OpenAPI para esto:

## A nivel de Clase del Controlador:

@Tag(name = "Nombre del Recurso", description = "Descripción del Recurso"): Agrupa las operaciones relacionadas 
bajo un nombre y proporciona una descripción para el recurso. A nivel de Método del Controlador (Endpoints):

@Operation(summary = "Resumen de la Operación", description = "Descripción detallada de la operación"): 
Proporciona un resumen conciso y una descripción más extensa de lo que hace el endpoint.

@Parameters(...) y @Parameter(...): Documentan los parámetros de la solicitud 
(path variables, query parameters, headers, cookies).
name: Nombre del parámetro.
in: Ubicación del parámetro (path, query, header, cookie).
description: Descripción del parámetro.
required: Indica si el parámetro es obligatorio.
schema: Define el tipo de dato del parámetro.

@RequestBody(...): Documenta el cuerpo de la solicitud para métodos POST, PUT, etc.
description: Descripción del cuerpo de la solicitud.
required: Indica si el cuerpo de la solicitud es obligatorio.
content: Define el tipo de contenido (e.g., application/json) y el schema del objeto.

@ApiResponse(...) y @ApiResponses(...): Documentan las posibles respuestas del endpoint.
responseCode: Código de estado HTTP de la respuesta (e.g., "200", "400", "500").
description: Descripción de la respuesta para el código de estado dado.
content: Define el tipo de contenido de la respuesta y su schema.
A nivel de Clase del Modelo (DTOs, Entidades):

@Schema(description = "Descripción del Modelo", example = "Ejemplo de valor"): Proporciona 
una descripción y un ejemplo para el modelo.

@Schema(name = "nombreDelAtributo", description = "Descripción del Atributo", 
example = "valor de ejemplo", required = true/false): Documenta atributos individuales dentro de un modelo.

# Ejemplo de un Controlador Documentado:
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Obtiene los detalles de un usuario específico basado en su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@Parameter(description = "ID del usuario a obtener", required = true) @PathVariable Long id) {
        // Lógica para obtener el usuario
        Usuario usuario = new Usuario(id, "ejemplo", "secreto");
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Usuario> crearUsuario(@RequestBody(description = "Datos del usuario a crear", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))) @RequestBody Usuario nuevoUsuario) {
        // Lógica para crear el usuario
        nuevoUsuario.setId(10L); // Ejemplo de asignación de ID
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
}

class Usuario {
    @Schema(description = "ID del usuario", example = "1")
    private Long id;
    @Schema(description = "Nombre de usuario", example = "ejemplo")
    private String nombre;
    @Schema(description = "Contraseña del usuario", example = "secreto", required = true)
    private String contraseña;

    // Constructor, getters y setters
    public Usuario(Long id, String nombre, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}

# Paso 5: Configuración Adicional (Opcional)

## Puedes personalizar aún más la documentación de tu API configurando propiedades en tu archivo:
## application.properties o application.yml. Algunas configuraciones comunes incluyen:
# -----------------------------------------------------------
# YAML
# -----------------------------------------------------------
## springdoc:
##  api-docs:
##    path: /v3/api-docs # Cambiar la ruta de la definición OpenAPI
##  swagger-ui:
##    path: /swagger-ui.html # Cambiar la ruta de la interfaz de Swagger UI
 ##   config:
  ##    syntaxHighlight:
   ##     activated: true
          theme: monokai
  ## info:
##    title: Mi API de Usuarios
 ##   version: v1
  ##  description: API para gestionar usuarios
 ##   contact:
  ##    name: Tu Nombre
   ##   email: tu.email@example.com
        url: http://tu-sitio-web.com
  ##  license:
  ##    name: Apache 2.0
      url: https://www.apache.org/licenses/LICENSE-2.0
##  servers:
 ##   - url: http://localhost:8080
  ##    description: Servidor local de desarrollo
 ##   - url: https://api.ejemplo.com
##      description: Servidor de producción

# -----------------------------------------------------------

# Consulta la documentación oficial de Springdoc OpenAPI para 
# obtener una lista completa de las propiedades de configuración 
# disponibles.

Siguiendo estos pasos, podrás documentar eficazmente tu 
proyecto Spring Boot con Java 21 utilizando Springdoc OpenAPI,
proporcionando una documentación clara y útil para los consumidores 
de tu API. Recuerda revisar la interfaz de 
Swagger UI para verificar que la documentación se genera correctamente y realizar los ajustes necesarios con las anotaciones.
