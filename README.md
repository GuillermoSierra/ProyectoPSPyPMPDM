# HealthCare - Gestor de Salud Personal

Aplicación Android nativa desarrollada en Kotlin con Jetpack Compose.
Esta permite gestionar información médica personal incluyendo medicamentos, citas médicas, síntomas y contactos de doctores.
Proyecto final del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM).

## Características Principales

- **Autenticación segura** con Auth0
- **Gestión de medicamentos** con control de dosis, frecuencia y fechas
- **Calendario de citas médicas** con recordatorios
- **Registro de síntomas** con nivel de intensidad y seguimiento temporal
- **Directorio de doctores** con información de contacto
- **Búsqueda de medicamentos** en la base de datos OpenFDA
- **Sistema multi-usuario** con datos aislados por cuenta
- **Persistencia local** con Room Database
- **Arquitectura MVVM** con Clean Architecture

## Tecnologías Utilizadas

### Core
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework UI declarativo
- **Material Design 3** - Sistema de diseño

### Arquitectura
- **MVVM** (Model-View-ViewModel)
- **Clean Architecture** (separación en capas: data, domain, ui)
- **Inyección de dependencias** con Hilt/Dagger

### Persistencia
- **Room** - Base de datos local SQLite
- **Coroutines** y **Flow** - Programación asíncrona reactiva
- **SharedPreferences** - Almacenamiento de sesión de usuario

### Networking
- **Retrofit2** - Cliente HTTP para consumo de API REST
- **Gson** - Serialización/deserialización JSON
- **OkHttp** - Interceptores y logging de red

### Autenticación
- **Auth0** - Servicio de autenticación OAuth2/OIDC

### Navegación
- **Navigation Compose** - Navegación entre pantallas

## Requisitos Previos

- Android Studio Hedgehog o superior
- JDK 11 o superior
- SDK de Android 24 (Android 7.0) o superior
- Cuenta de Auth0 configurada

## Configuración de Auth0

1. Crear una cuenta en [Auth0](https://auth0.com)
2. Crear una nueva aplicación de tipo "Native"
3. Configurar los siguientes valores en el Dashboard de Auth0:
    - **Allowed Callback URLs**: `com.guillermo.healthcare://dev-bbezl7kkcc25rf77.us.auth0.com/android/com.guillermo.healthcare/callback`
    - **Allowed Logout URLs**: `com.guillermo.healthcare://dev-bbezl7kkcc25rf77.us.auth0.com/android/com.guillermo.healthcare/callback`
4. Activar en Database Settings: "Requires Username" (opcional, para personalización de nombre de usuario)

## Instalación

### Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/healthcare.git
cd healthcare
```

### Configurar Credenciales de Auth0

Editar el archivo `ViewModelAuth.kt` ubicado en:
```
app/src/main/java/com/guillermo/healthcare/ui/screens/login/ViewModelAuth.kt
```

Reemplazar las credenciales de Auth0 con las de tu cuenta:
```kotlin
private val auth0 = Auth0(
    "TU_CLIENT_ID",
    "TU_DOMAIN.auth0.com"
)
```

### Compilar y Ejecutar

1. Abrir el proyecto en Android Studio
2. Sincronizar el proyecto con Gradle
3. Ejecutar en un emulador o dispositivo físico

## Estructura del Proyecto

```
app/src/main/java/com/guillermo/healthcare/
│
├── HealthCareApp.kt           # Clase Application con configuración de Hilt
├── MainActivity.kt             # Activity principal, punto de entrada de la app
│
├── data/                       # Capa de datos
│   ├── local/                  # Persistencia local
│   │   ├── dao/                # Data Access Objects (interfaces de acceso a BD)
│   │   │   ├── CitaDao.kt
│   │   │   ├── DoctorDao.kt
│   │   │   ├── MedicamentoDao.kt
│   │   │   └── SintomaDao.kt
│   │   ├── database/           # Configuración de Room Database
│   │   │   ├── BaseDatosSalud.kt      # Definición de la BD y migración v1→v2
│   │   │   └── ModuloBaseDatos.kt     # Módulo de Hilt para inyección
│   │   └── entity/             # Entidades (tablas) de Room
│   │       ├── Cita.kt
│   │       ├── Doctor.kt
│   │       ├── Medicamento.kt
│   │       └── Sintoma.kt
│   │
│   ├── remote/                 # API externa (OpenFDA)
│   │   ├── api/
│   │   │   └── OpenFDAApi.kt          # Interfaz Retrofit con endpoints
│   │   ├── dto/
│   │   │   └── MedicamentoDto.kt      # Data Transfer Object de API
│   │   └── ClienteRetrofit.kt         # Configuración de Retrofit
│   │
│   └── repository/             # Repositorios (abstracción de fuentes de datos)
│       ├── RepositorioCita.kt
│       ├── RepositorioDoctor.kt
│       ├── RepositorioMedicamento.kt
│       ├── RepositorioOpenFDA.kt
│       └── RepositorioSintoma.kt
│
├── domain/                     # Capa de dominio (lógica de negocio)
│   ├── model/                  # Modelos de dominio
│   │   ├── Mappers.kt                 # Conversores Entity ↔ Model
│   │   ├── ModeloCita.kt
│   │   ├── ModeloDoctor.kt
│   │   ├── ModeloMedicamento.kt
│   │   └── ModeloSintoma.kt
│   │
│   └── usecase/                # Casos de uso (operaciones de negocio)
│       ├── CasosUsoCita.kt
│       ├── CasosUsoDoctor.kt
│       ├── CasosUsoMedicamento.kt
│       └── CasosUsoSintoma.kt
│
└── ui/                         # Capa de presentación
    ├── components/             # Componentes reutilizables
    │   └── ComponentesComunes.kt      # FilaDetalle, campos compartidos
    │
    ├── navigation/             # Sistema de navegación
    │   ├── GrafoNavegacion.kt         # Definición del grafo de navegación
    │   └── Pantalla.kt                # Rutas de navegación (sealed class)
    │
    ├── screens/                # Pantallas de la aplicación
    │   ├── busqueda/           # Búsqueda de medicamentos en OpenFDA
    │   │   ├── PantallaBusqueda.kt
    │   │   └── ViewModelBusqueda.kt
    │   │
    │   ├── citas/              # Gestión de citas médicas
    │   │   ├── PantallaDetalleCita.kt
    │   │   ├── PantallaFormularioCita.kt
    │   │   ├── PantallaListaCitas.kt
    │   │   └── ViewModelCita.kt
    │   │
    │   ├── doctores/           # Gestión de doctores
    │   │   ├── PantallaDetalleDoctor.kt
    │   │   ├── PantallaFormularioDoctor.kt
    │   │   ├── PantallaListaDoctores.kt
    │   │   └── ViewModelDoctor.kt
    │   │
    │   ├── inicio/             # Pantalla principal (dashboard)
    │   │   └── PantallaInicio.kt
    │   │
    │   ├── login/              # Autenticación
    │   │   ├── PantallaLogin.kt
    │   │   └── ViewModelAuth.kt
    │   │
    │   ├── medicamentos/       # Gestión de medicamentos
    │   │   ├── PantallaDetalleMedicamento.kt
    │   │   ├── PantallaFormularioMedicamento.kt
    │   │   ├── PantallaListaMedicamentos.kt
    │   │   └── ViewModelMedicamento.kt
    │   │
    │   ├── perfil/             # Perfil de usuario
    │   │   └── PantallaPerfil.kt
    │   │
    │   └── sintomas/           # Gestión de síntomas
    │       ├── PantallaDetalleSintoma.kt
    │       ├── PantallaFormularioSintoma.kt
    │       ├── PantallaListaSintomas.kt
    │       └── ViewModelSintoma.kt
    │
    └── theme/                  # Tema de la aplicación
        ├── Color.kt                   # Paleta de colores
        ├── Theme.kt                   # Configuración del tema Material3
        └── Type.kt                    # Tipografía
```

## Descripción de Componentes

### Capa de Datos (data/)

#### local/dao/
Interfaces que definen las operaciones CRUD para cada entidad. Room genera automáticamente la implementación.

**MedicamentoDao.kt**
- `getAllMedicamentos(userId: String)`: Obtiene medicamentos filtrados por usuario
- `getMedicamentoById(id: Int)`: Obtiene un medicamento específico
- `insert(medicamento: Medicamento)`: Inserta nuevo medicamento
- `update(medicamento: Medicamento)`: Actualiza medicamento existente
- `delete(medicamento: Medicamento)`: Elimina medicamento

Funcionalidad similar en CitaDao, DoctorDao y SintomaDao.

#### local/database/
**BaseDatosSalud.kt**: Definición de la base de datos Room. Incluye:
- Versión actual: 2
- Migración 1→2: Añade campo `userId` a todas las tablas
- Entities: Medicamento, Cita, Sintoma, Doctor

**ModuloBaseDatos.kt**: Módulo de Hilt que proporciona la instancia singleton de la base de datos.

#### local/entity/
Clases de datos que representan las tablas de la base de datos.

**Medicamento.kt**
```kotlin
@Entity(tableName = "medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val nombre: String,
    val dosis: String,
    val frecuencia: String,
    val fechaInicio: String,
    val fechaFin: String?,
    val notas: String?
)
```

Estructura similar en Cita, Sintoma y Doctor con sus campos específicos.

#### remote/
**OpenFDAApi.kt**: Interfaz Retrofit que define el endpoint de búsqueda de medicamentos.

**MedicamentoDto.kt**: Objeto de transferencia de datos que mapea la respuesta JSON de OpenFDA.

**ClienteRetrofit.kt**: Configuración de Retrofit con:
- BaseUrl: "https://api.fda.gov/"
- Conversor Gson para JSON
- Logging interceptor para debug

#### repository/
Implementan el patrón Repository, abstrayendo las fuentes de datos (Room local o API remota).

**RepositorioMedicamento.kt**
- Proporciona Flow reactivo de medicamentos
- Operaciones CRUD sobre el DAO
- Filtra por userId del usuario autenticado

**RepositorioOpenFDA.kt**
- Realiza búsquedas en la API de la FDA
- Manejo de errores de red y respuestas 404
- Mapeo de DTOs a modelos de dominio

### Capa de Dominio (domain/)

#### model/
Modelos de dominio que representan las entidades desde la perspectiva del negocio.

**Mappers.kt**: Funciones de extensión para convertir entre Entities (Room) y Models (dominio).

```kotlin
fun Medicamento.toModel() = ModeloMedicamento(...)
fun ModeloMedicamento.toEntity() = Medicamento(...)
```

#### usecase/
Casos de uso que encapsulan la lógica de negocio.

**CasosUsoMedicamento.kt**
```kotlin
class ObtenerMedicamentosUseCase @Inject constructor(
    private val repositorio: RepositorioMedicamento
) {
    operator fun invoke(userId: String): Flow<List<ModeloMedicamento>>
}
```

Incluye casos de uso para: obtener, insertar, actualizar, eliminar y obtener por ID.

### Capa de UI (ui/)

#### components/
**ComponentesComunes.kt**: Componentes reutilizables.

**FilaDetalle**: Componente que muestra un par etiqueta-valor formateado para pantallas de detalle.

#### navigation/
**Pantalla.kt**: Sealed class que define todas las rutas de la aplicación con tipado seguro.

```kotlin
sealed class Pantalla(val ruta: String) {
    object Login : Pantalla("login")
    object Inicio : Pantalla("inicio")
    object FormularioMedicamento : Pantalla("formulario_medicamento?medicamentoId={medicamentoId}&nombre={nombre}") {
        fun crearRuta(medicamentoId: Int? = null, nombre: String? = null): String
    }
    // ...
}
```

**GrafoNavegacion.kt**: Define el NavHost con todas las rutas y composables asociados. Gestiona la inyección del ViewModelAuth compartido.

#### screens/

##### login/
**PantallaLogin.kt**: Pantalla de autenticación con Auth0.
- Botón "Iniciar Sesión con Auth0"
- Abre navegador para login/registro
- Manejo de estados: cargando, error, autenticado

**ViewModelAuth.kt**: Gestiona el estado de autenticación.
- `iniciarSesion(context)`: Inicia flujo de Auth0 con prompt=login
- `cargarSesionGuardada(context)`: Restaura sesión desde SharedPreferences
- `cerrarSesionLocal(context)`: Limpia sesión local
- Estado: `EstadoAuth(userId, nombreUsuario, emailUsuario, autenticado, cargando, error)`

##### inicio/
**PantallaInicio.kt**: Dashboard principal con:
- Resumen de datos (contadores de medicamentos, citas, síntomas, doctores)
- Menú desplegable con perfil y cerrar sesión
- Accesos rápidos a todas las funcionalidades

##### medicamentos/
**PantallaListaMedicamentos.kt**: Lista de medicamentos del usuario.
- LazyColumn con tarjetas clickeables
- Navegación a detalle al hacer click
- FAB para crear nuevo medicamento

**PantallaFormularioMedicamento.kt**: Formulario de creación/edición.
- Campos: nombre, dosis, frecuencia, fecha inicio, fecha fin, notas
- DatePickers para fechas
- Fecha inicio: permite fechas pasadas
- Fecha fin: solo fechas desde hoy
- Checkbox "Fecha fin indefinida" (oculta selector de fecha)
- Validación de campos obligatorios
- Puede recibir nombre precargado desde búsqueda de OpenFDA

**PantallaDetalleMedicamento.kt**: Vista detallada de un medicamento.
- Muestra todos los campos
- Fecha fin muestra "Indefinida" si es null
- Botones: Editar, Eliminar (con confirmación)

**ViewModelMedicamento.kt**: Gestiona estado de medicamentos.
- `medicamentos: StateFlow<List<Medicamento>>`: Lista reactiva
- `medicamentoSeleccionado: StateFlow<Medicamento?>`: Para detalle/edición
- `cargarMedicamentos(userId)`, `insertarMedicamento()`, etc.

##### citas/
Estructura similar a medicamentos.

**PantallaFormularioCita.kt**: Campos específicos.
- Nombre del doctor, especialidad, fecha, hora, lugar, notas
- DatePicker y TimePicker

##### sintomas/
Estructura similar a medicamentos.

**PantallaFormularioSintoma.kt**: Campos específicos.
- Nombre del síntoma, intensidad (1-10 con Slider), fecha, hora, descripción

##### doctores/
Estructura similar a medicamentos.

**PantallaFormularioDoctor.kt**: Campos específicos.
- Nombre, especialidad, teléfono, email, dirección, notas

##### busqueda/
**PantallaBusqueda.kt**: Búsqueda de medicamentos en OpenFDA.
- Campo de búsqueda con botón
- Lista de resultados clickeables
- Al hacer click: diálogo de confirmación
- Si acepta: navega a formulario con nombre precargado

**ViewModelBusqueda.kt**: Gestiona búsqueda en API.
- `estado: EstadoBusqueda(consulta, resultados, cargando, error)`
- `buscarMedicamentos()`: Llama a RepositorioOpenFDA

##### perfil/
**PantallaPerfil.kt**: Muestra información del usuario autenticado.
- Avatar con inicial del nombre
- Nombre y email desde Auth0
- Indicador de autenticación

#### theme/
**Color.kt**: Paleta de colores de Material Design 3.

**Theme.kt**: Configuración del tema con modo claro/oscuro.

**Type.kt**: Tipografía de la aplicación.

## Flujo de Datos

### Lectura (Ejemplo: Medicamentos)
```
UI (PantallaListaMedicamentos)
    ↓ collectAsState
ViewModel (ViewModelMedicamento)
    ↓ StateFlow
UseCase (ObtenerMedicamentosUseCase)
    ↓ Flow
Repository (RepositorioMedicamento)
    ↓ Flow
DAO (MedicamentoDao)
    ↓ @Query
Room Database
```

### Escritura (Ejemplo: Crear Medicamento)
```
UI (PantallaFormularioMedicamento)
    ↓ onClick
ViewModel (ViewModelMedicamento.insertarMedicamento)
    ↓ viewModelScope.launch
UseCase (InsertarMedicamentoUseCase)
    ↓ suspend
Repository (RepositorioMedicamento.insertarMedicamento)
    ↓ suspend
DAO (MedicamentoDao.insert)
    ↓ @Insert
Room Database
    ↓ Flow actualizado automáticamente
UI se actualiza reactivamente
```

## Sistema Multi-Usuario

Cada entidad tiene un campo `userId` que almacena el identificador único de Auth0 (`auth0|...`).

**Flujo de Autenticación:**
1. Usuario hace login con Auth0
2. `ViewModelAuth` recibe `result.user.getId()`
3. Se guarda en SharedPreferences como `userId`
4. `ViewModelAuth.estado` emite `EstadoAuth` con userId
5. Todos los ViewModels filtran datos por este userId
6. Al crear entidades, se asigna automáticamente el userId del usuario autenticado

**Aislamiento de Datos:**
```kotlin
@Query("SELECT * FROM medicamentos WHERE userId = :userId")
fun getAllMedicamentos(userId: String): Flow<List<Medicamento>>
```

## Persistencia de Sesión

Al hacer login:
- Auth0 devuelve `Credentials` con información del usuario
- Se extrae `userId`, `nombreUsuario` y `emailUsuario`
- Se guarda en SharedPreferences con clave "auth_prefs"

Al abrir la app:
- `MainActivity` llama a `viewModelAuth.cargarSesionGuardada(context)`
- Si hay userId guardado, restaura el estado de autenticación
- Navega automáticamente a Inicio si hay sesión válida

Al cerrar sesión:
- Se limpia SharedPreferences
- Se resetea el estado de ViewModelAuth
- Se navega al Login

## Validaciones

### Formularios
- Campos obligatorios: nombre, dosis, frecuencia, fecha inicio (medicamentos)
- Indicadores visuales de error con `isError` y `supportingText`
- Prevención de envío si hay errores

### Fechas
- Fecha inicio de medicamento: permite fechas pasadas
- Fecha fin de medicamento: solo desde hoy en adelante (validado con DatePicker.minDate)
- Opción de fecha fin indefinida (checkbox que oculta el selector)

### Eliminación
- Diálogos de confirmación antes de eliminar cualquier entidad
- Muestra el nombre del elemento a eliminar

## Manejo de Errores

### API OpenFDA
```kotlin
try {
    val respuesta = api.buscarMedicamento(query, limit)
    if (respuesta.isSuccessful && respuesta.body() != null) {
        // Procesar resultados
    } else if (respuesta.code() == 404) {
        // No se encontraron resultados
    } else {
        // Error genérico
    }
} catch (e: Exception) {
    // Error de red
}
```

### Room Database
Las operaciones de Room son transaccionales y lanzan excepciones en caso de error. Se capturan en los ViewModels.

## Dependencias Principales

```gradle
// Jetpack Compose
implementation("androidx.compose.ui:ui:1.6.0")
implementation("androidx.compose.material3:material3:1.2.0")
implementation("androidx.navigation:navigation-compose:2.7.6")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

// Room
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Auth0
implementation("com.auth0.android:auth0:2.10.2")

// Hilt
implementation("com.google.dagger:hilt-android:2.48")
ksp("com.google.dagger:hilt-compiler:2.48")
implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

## Características Técnicas Destacadas

### Arquitectura Limpia
Separación clara entre capas de datos, dominio y presentación. Facilita testing y mantenimiento.

### Inyección de Dependencias
Hilt proporciona instancias únicas de DAOs, Repositorios y UseCases. ViewModels se crean con `@HiltViewModel`.

### Programación Reactiva
Uso de Flow y StateFlow para propagación automática de cambios desde la base de datos hasta la UI.

### Navegación Tipo Seguro
Sealed class `Pantalla` con funciones que generan rutas correctamente, evitando errores de strings.

### ViewModelAuth Compartido
Una única instancia de ViewModelAuth se crea en MainActivity y se propaga a todas las pantallas vía GrafoNavegacion, asegurando consistencia del estado de autenticación.

### Composables Reutilizables
`FilaDetalle` se usa en todas las pantallas de detalle, manteniendo consistencia visual.

### DatePickers Nativos
Uso de DatePickerDialog de Android para selección de fechas con restricciones específicas.

## Mejoras Futuras

- Notificaciones para recordatorios de medicación
- Gráficos de evolución de síntomas
- Exportación de datos a PDF
- Sincronización en la nube
- Widget de homescreen con próximas citas
- Integración con Google Calendar
- Modo offline completo para búsqueda (caché de OpenFDA)
- Biometría para acceso rápido

## Problemas Conocidos

- La búsqueda de OpenFDA puede devolver 404 si el medicamento no existe en la base de datos de la FDA
- Auth0 requiere conexión a internet para autenticación inicial
- La sesión se mantiene localmente pero no sincroniza con Auth0 al cerrar/abrir app

## Licencia

Este proyecto es parte de un trabajo académico del ciclo de Desarrollo de Aplicaciones Multiplataforma.

## Autor

Guillermo Sierra Castejón
Proyecto Final Asignaturas PSP y PMDM - Curso 2025/2026

## Contacto

Para dudas o sugerencias:
- Email: sierracastejong@gmail.com
- GitHub: GuillermoSierra