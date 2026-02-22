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
- **Búsqueda de medicamentos** en la base de datos OpenFDA con 2 endpoints
- **Sistema multi-usuario** con datos aislados por cuenta
- **Persistencia local** con Room Database
- **Arquitectura MVVM** con Clean Architecture

## Tecnologías Utilizadas

### Core
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework UI declarativo (14 pantallas)
- **Material Design 3** - Sistema de diseño

### Arquitectura
- **MVVM** (Model-View-ViewModel)
- **Clean Architecture** (separación en capas: data, domain, ui)
- **Inyección de dependencias** con Hilt/Dagger

### Persistencia
- **Room** - Base de datos local SQLite (4 entidades con CRUD completo)
- **Coroutines** y **Flow** - Programación asíncrona reactiva
- **SharedPreferences** - Almacenamiento de sesión de usuario

### Networking
- **Retrofit2** - Cliente HTTP para consumo de API REST
- **Gson** - Serialización/deserialización JSON
- **OkHttp** - Interceptores y logging de red
- **OpenFDA API** - 2 endpoints: búsqueda de medicamentos + información detallada

### Autenticación
- **Auth0** - Servicio de autenticación OAuth2/OIDC
- **Persistencia de sesión** con SharedPreferences

### Navegación
- **Navigation Compose** - Navegación entre pantallas tipo-segura

## Requisitos Previos

- Android Studio Hedgehog o superior
- JDK 11 o superior
- SDK de Android 24 (Android 7.0) o superior
- Cuenta de Auth0 configurada

## Descargar e Instalar

### APK Release

El APK firmado y listo para instalar está disponible en:
```
Health/app/release/app-release.apk
```

También puedes descargarlo directamente desde GitHub:
[Descargar APK](https://github.com/GuillermoSierra/ProyectoPSPyPMPDM/tree/main/Health/app/release)

### Instalación en Dispositivo Android

1. Descarga el archivo `app-release.apk` desde el repositorio
2. En tu dispositivo Android, ve a: **Configuración > Seguridad**
3. Activa la opción **"Instalar aplicaciones desconocidas"** o **"Fuentes desconocidas"**
4. Abre el archivo APK descargado
5. Toca **"Instalar"** y espera a que se complete la instalación
6. Abre HealthCare desde el cajón de aplicaciones
7. Haz login con Auth0 (se abrirá el navegador)
8. Ya puedes usar la aplicación

### Ejecución desde Android Studio (Desarrollo)

1. Clona el repositorio:
```bash
git clone https://github.com/GuillermoSierra/ProyectoPSPyPMPDM.git
cd ProyectoPSPyPMPDM/Health
```

2. Abre el proyecto en Android Studio

3. Sincroniza Gradle (Build > Sync Project with Gradle Files)

4. Ejecuta la aplicación (Run > Run 'app' o Shift+F10)

## Configuración de Auth0

La aplicación está configurada con las siguientes credenciales de Auth0:

- **Domain**: `dev-bbezl7kkcc25rf77.us.auth0.com`
- **Client ID**: `4ehLNq9ezzcqisqrwGPW7dH5qaa1M3D8`
- **Callback URL**: `com.guillermo.healthcare://dev-bbezl7kkcc25rf77.us.auth0.com/android/com.guillermo.healthcare/callback`

### Configurar con tu propia cuenta de Auth0

Si deseas usar tu propia cuenta de Auth0:

1. Crear una cuenta en [Auth0](https://auth0.com)
2. Crear una nueva aplicación de tipo "Native"
3. Configurar en el Dashboard de Auth0:
    - **Allowed Callback URLs**: `com.guillermo.healthcare://TU_DOMAIN.auth0.com/android/com.guillermo.healthcare/callback`
    - **Allowed Logout URLs**: `com.guillermo.healthcare://TU_DOMAIN.auth0.com/android/com.guillermo.healthcare/callback`
4. Activar en Database Settings: "Requires Username" (opcional)
5. Editar el archivo `ViewModelAuth.kt`:
```kotlin
private val auth0 = Auth0(
    "TU_CLIENT_ID",
    "TU_DOMAIN.auth0.com"
)
```

## Información de Keystore (Para Desarrolladores)

Si necesitas generar un nuevo APK release o actualizar la aplicación:

**Archivo Keystore**: `healthcare_keystore.jks`  
**Ubicación**: Raíz del proyecto  
**Password del Keystore**: `healthcare2026`  
**Key Alias**: `key0`  
**Password del Alias**: `healthcare2026`  
**Validez**: 25 años

### Generar APK Release

1. Build > Generate Signed Bundle / APK
2. Selecciona "APK"
3. Key store path: selecciona `healthcare_keystore.jks`
4. Introduce las contraseñas
5. Build Variant: release
6. Finish

**IMPORTANTE**: Este keystore es solo para propósitos académicos. En un entorno de producción:
- Usa un keystore con contraseñas seguras
- NO lo compartas públicamente
- Guárdalo en un lugar seguro
- Considera usar Google Play App Signing

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
│   │   │   └── OpenFDAApi.kt          # Interfaz Retrofit con 2 endpoints
│   │   ├── dto/
│   │   │   └── MedicamentoDto.kt      # Data Transfer Objects de API
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
    ├── screens/                # Pantallas de la aplicación (14 en total)
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

## Base de Datos Room

### Las 4 Entidades

#### 1. Medicamento
```kotlin
@Entity(tableName = "medicamentos")
data class Medicamento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,        // Filtro multi-usuario
    val nombre: String,
    val dosis: String,
    val frecuencia: String,
    val fechaInicio: String,   // Formato: YYYY-MM-DD
    val fechaFin: String?,     // null = indefinido
    val notas: String?
)
```

#### 2. Cita
```kotlin
@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val nombreDoctor: String,
    val especialidad: String,
    val fecha: String,         // Formato: YYYY-MM-DD
    val hora: String,          // Formato: HH:mm
    val lugar: String,
    val notas: String?
)
```

#### 3. Sintoma
```kotlin
@Entity(tableName = "sintomas")
data class Sintoma(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val nombre: String,
    val intensidad: Int,       // Escala 1-10
    val fecha: String,         // Formato: YYYY-MM-DD
    val hora: String,          // Formato: HH:mm
    val descripcion: String?
)
```

#### 4. Doctor
```kotlin
@Entity(tableName = "doctores")
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val nombre: String,
    val especialidad: String,
    val telefono: String,
    val email: String,
    val direccion: String,
    val notas: String?
)
```

### Ubicación de la Base de Datos

La base de datos Room se almacena en:
```
/data/data/com.guillermo.healthcare/databases/healthcare_database
```

**IMPORTANTE**: Esta ubicación está protegida por Android y solo es accesible:
- Desde la propia aplicación
- Con Database Inspector de Android Studio (versión debug)
- Con Device File Explorer (solo en emulador o dispositivo con USB debugging)

### Ver la Base de Datos con Database Inspector

**Requisitos**:
- Ejecutar la app desde Android Studio (versión debug)
- Tener el dispositivo/emulador conectado

**Pasos**:
1. Android Studio → View → Tool Windows → App Inspection
2. Selecciona la pestaña "Database Inspector"
3. En el dropdown, selecciona el proceso de la app
4. Expande: com.guillermo.healthcare → databases → healthcare_database
5. Verás las 4 tablas: medicamentos, citas, sintomas, doctores
6. Doble click en cualquier tabla para ver todas las filas
7. Los cambios se reflejan EN TIEMPO REAL

**Troubleshooting**:
- Si aparece [DETACHED]: Para la app (Stop) y ejecútala de nuevo (Run)
- Si no aparece la base de datos: Crea al menos un dato (medicamento, cita, etc.)
- Si Database Inspector está vacío: Verifica que la app está en modo Debug, no Release

### Migración de Base de Datos

El proyecto incluye una migración de versión 1 a versión 2:

**Cambio**: Añadir campo `userId` a todas las tablas para implementar sistema multi-usuario

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE medicamentos ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE citas ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE sintomas ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE doctores ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
    }
}
```

## OpenFDA API - 2 Endpoints Implementados

### Endpoint 1: Búsqueda de Medicamentos

**URL**: `GET https://api.fda.gov/drug/ndc.json`

**Parámetros**:
- `search`: brand_name:NOMBRE* (búsqueda por nombre de marca)
- `limit`: 10 (número máximo de resultados)

**Respuesta**:
```json
{
  "results": [
    {
      "brand_name": "Ibuprofen",
      "generic_name": "IBUPROFEN",
      "labeler_name": "Major Pharmaceuticals Inc.",
      "route": ["ORAL"],
      "dosage_form": "TABLET, COATED"
    }
  ]
}
```

**Uso en la App**:
- Pantalla de búsqueda permite buscar medicamentos por nombre
- Resultados se muestran en lista con información básica
- Al seleccionar un resultado, se puede añadir a la lista personal con el nombre precargado

### Endpoint 2: Información Detallada del Medicamento

**URL**: `GET https://api.fda.gov/drug/label.json`

**Parámetros**:
- `search`: openfda.brand_name:NOMBRE
- `limit`: 5

**Respuesta**:
```json
{
  "results": [
    {
      "purpose": ["Pain reliever/fever reducer"],
      "warnings": ["Allergy alert: Ibuprofen may cause severe allergic reaction..."],
      "dosage_and_administration": ["Adults: Take 1-2 tablets every 4-6 hours..."]
    }
  ]
}
```

**Uso en la App**:
- Botón "Ver más información" en resultados de búsqueda
- Muestra diálogo con propósito, advertencias y dosificación
- Información detallada para tomar decisiones informadas

### Manejo de Errores de API

```kotlin
try {
    val respuesta = api.buscarMedicamentos(query, limit)
    if (respuesta.resultados != null) {
        ResultadoApi.Exito(respuesta.resultados)
    } else {
        ResultadoApi.Error("No se encontraron resultados")
    }
} catch (e: retrofit2.HttpException) {
    if (e.code() == 404) {
        ResultadoApi.Error("Medicamento no encontrado en FDA")
    } else {
        ResultadoApi.Error("Error del servidor: ${e.code()}")
    }
} catch (e: Exception) {
    ResultadoApi.Error("Error de conexión: ${e.message}")
}
```

## Flujo de Datos

### Lectura (Ejemplo: Medicamentos)
```
UI (PantallaListaMedicamentos)
    ↓ collectAsState()
ViewModel (ViewModelMedicamento)
    ↓ StateFlow<List<Medicamento>>
UseCase (ObtenerMedicamentosUseCase)
    ↓ Flow<List<ModeloMedicamento>>
Repository (RepositorioMedicamento)
    ↓ Flow<List<Medicamento>>
DAO (MedicamentoDao)
    ↓ @Query("SELECT * FROM medicamentos WHERE userId = :userId")
Room Database
    ↓ Emite cambios automáticamente cuando se modifica la tabla
```

**Reactividad**: Si insertas, actualizas o eliminas un medicamento,
el Flow emite automáticamente la lista actualizada y la UI se recompone sin necesidad de recargar manualmente.

### Escritura (Ejemplo: Crear Medicamento)
```
UI (PantallaFormularioMedicamento)
    ↓ onClick del botón "Guardar"
    ↓ Validación de campos
ViewModel (ViewModelMedicamento.insertarMedicamento)
    ↓ viewModelScope.launch (coroutine)
UseCase (InsertarMedicamentoUseCase)
    ↓ suspend fun
Repository (RepositorioMedicamento.insertarMedicamento)
    ↓ suspend fun
DAO (MedicamentoDao.insert)
    ↓ @Insert suspend fun
Room Database
    ↓ INSERT SQL ejecutado
    ↓ Flow detecta cambio automáticamente
    ↓ Emite nueva lista
UI se actualiza automáticamente
```

## Sistema Multi-Usuario

Cada entidad incluye un campo `userId` que almacena el identificador único de Auth0 (formato: `auth0|65a8f3c2d1e4b9a7...`).

### Flujo de Autenticación

1. Usuario hace login con Auth0 (se abre navegador)
2. Auth0 valida credenciales y devuelve objeto `Credentials`
3. `ViewModelAuth` extrae `result.user.getId()` → userId
4. Se guarda en SharedPreferences (clave: "auth_prefs")
    - userId
    - nombreUsuario
    - emailUsuario
5. `ViewModelAuth.estado` emite `EstadoAuth(autenticado = true, userId = "...")`
6. Todos los ViewModels filtran datos por este userId
7. Al crear nuevas entidades, se asigna automáticamente el userId del usuario autenticado

### Aislamiento de Datos

```kotlin
// Todas las queries filtran por userId
@Query("SELECT * FROM medicamentos WHERE userId = :userId")
fun getAllMedicamentos(userId: String): Flow<List<Medicamento>>

// Al insertar, se incluye el userId
val medicamento = Medicamento(
    id = 0,
    userId = estadoAuth.userId,  // Usuario actual
    nombre = "Ibuprofeno",
    // ...
)
```

**Resultado**: Cada usuario solo ve sus propios medicamentos, citas, síntomas y doctores. Los datos están completamente aislados.

### Persistencia de Sesión

**Al hacer login**:
```kotlin
val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
prefs.edit().apply {
    putString("userId", userId)
    putString("nombreUsuario", result.user.name)
    putString("emailUsuario", result.user.email)
}.apply()
```

**Al abrir la app**:
```kotlin
fun cargarSesionGuardada(context: Context) {
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    val userId = prefs.getString("userId", "") ?: ""
    if (userId.isNotBlank()) {
        _estado.value = EstadoAuth(
            autenticado = true,
            userId = userId,
            nombreUsuario = prefs.getString("nombreUsuario", null),
            emailUsuario = prefs.getString("emailUsuario", null)
        )
    }
}
```

**Al cerrar sesión**:
```kotlin
fun cerrarSesionLocal(context: Context) {
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    prefs.edit().clear().apply()  // Borra todo
    _estado.value = EstadoAuth(autenticado = false)
}
```

**Ubicación del archivo**:
```
/data/data/com.guillermo.healthcare/shared_prefs/auth_prefs.xml
```

**Contenido del archivo**:
```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="userId">auth0|65a8f3c2d1e4b9a7f3c2d1e4</string>
    <string name="nombreUsuario">Guillermo Sierra</string>
    <string name="emailUsuario">guillermo@example.com</string>
</map>
```

**IMPORTANTE sobre Seguridad**:
- NO se guardan tokens de acceso (accessToken, idToken) por seguridad
- Solo se guarda userId (identificador público)
- Los tokens quedan en memoria y se pierden al cerrar la app
- En producción, considera usar EncryptedSharedPreferences

## Validaciones

### Formularios

Todos los formularios implementan validación en tiempo real:

```kotlin
var nombre by remember { mutableStateOf("") }
var errorNombre by remember { mutableStateOf(false) }

OutlinedTextField(
    value = nombre,
    onValueChange = { 
        nombre = it
        errorNombre = false  // Quita error al escribir
    },
    label = { Text("Nombre del medicamento *") },
    isError = errorNombre,
    supportingText = if (errorNombre) {
        { Text("El nombre es obligatorio") }
    } else null
)

Button(onClick = {
    errorNombre = nombre.isBlank()
    if (!errorNombre) {
        // Guardar
    }
}) {
    Text("Crear")
}
```

**Campos obligatorios**:
- Medicamentos: nombre, dosis, frecuencia, fecha inicio
- Citas: nombre doctor, especialidad, fecha, hora, lugar
- Síntomas: nombre, intensidad, fecha, hora
- Doctores: nombre, especialidad, teléfono, email

### Fechas

**DatePicker con restricciones**:

```kotlin
// Fecha inicio: permite fechas pasadas (medicamentos ya iniciados)
val fechaInicioPickerState = rememberDatePickerState(
    initialSelectedDateMillis = System.currentTimeMillis(),
    selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()  // Solo pasado y hoy
        }
    }
)

// Fecha fin: solo desde hoy en adelante
val fechaFinPickerState = rememberDatePickerState(
    initialSelectedDateMillis = System.currentTimeMillis(),
    selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= System.currentTimeMillis()  // Solo futuro y hoy
        }
    }
)
```

**Checkbox fecha indefinida**:
```kotlin
var fechaFinIndefinida by remember { mutableStateOf(false) }

Row {
    Checkbox(
        checked = fechaFinIndefinida,
        onCheckedChange = { 
            fechaFinIndefinida = it
            if (it) fechaFin = ""  // Limpia fecha si es indefinida
        }
    )
    Text("Fecha fin indefinida")
}

// Campo de fecha solo visible si NO es indefinida
if (!fechaFinIndefinida) {
    OutlinedTextField(
        value = fechaFin,
        // ...
    )
}
```

### Eliminación

Todos los delete operations incluyen diálogo de confirmación:

```kotlin
if (mostrarDialogoEliminar) {
    AlertDialog(
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de que deseas eliminar '${medicamento.nombre}'?") },
        onDismissRequest = { mostrarDialogoEliminar = false },
        confirmButton = {
            TextButton(onClick = {
                viewModel.eliminarMedicamento(medicamento)
                navController.navigateUp()
            }) {
                Text("Eliminar", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = { mostrarDialogoEliminar = false }) {
                Text("Cancelar")
            }
        }
    )
}
```

## Dependencias Principales

```gradle
dependencies {
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Auth0
    implementation("com.auth0.android:auth0:2.10.2")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Core
    implementation("androidx.core:core-ktx:1.12.0")
}
```

## Características Técnicas Destacadas

### Arquitectura Limpia (Clean Architecture)

- **Separación de capas**: data, domain, ui
- **Independencia**: Cada capa solo conoce la inferior
- **Testabilidad**: Fácil de testear cada capa por separado
- **Mantenibilidad**: Cambios aislados sin afectar otras capas

### Inyección de Dependencias con Hilt

```kotlin
// ViewModels
@HiltViewModel
class ViewModelMedicamento @Inject constructor(
    private val obtenerMedicamentosUseCase: ObtenerMedicamentosUseCase,
    private val insertarMedicamentoUseCase: InsertarMedicamentoUseCase,
    // ...
) : ViewModel()

// Provisión de dependencias
@Module
@InstallIn(SingletonComponent::class)
object ModuloBaseDatos {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BaseDatosSalud {
        return Room.databaseBuilder(
            context,
            BaseDatosSalud::class.java,
            "healthcare_database"
        ).addMigrations(BaseDatosSalud.MIGRATION_1_2).build()
    }
}
```

### Programación Reactiva con Flow

```kotlin
// DAO emite Flow
@Query("SELECT * FROM medicamentos WHERE userId = :userId")
fun getAllMedicamentos(userId: String): Flow<List<Medicamento>>

// ViewModel recoge el Flow
fun cargarMedicamentos(userId: String) {
    viewModelScope.launch {
        obtenerMedicamentosUseCase(userId).collect { lista ->
            _medicamentos.value = lista  // Actualiza StateFlow
        }
    }
}

// UI observa el StateFlow
val medicamentos by viewModel.medicamentos.collectAsState()

// Beneficio: Cuando Room inserta/actualiza/elimina un medicamento,
// el Flow detecta el cambio automáticamente y emite la lista actualizada.
// La UI se recompone sola sin necesidad de recargar manualmente.
```

### Navegación Tipo-Segura

```kotlin
sealed class Pantalla(val ruta: String) {
    object Login : Pantalla("login")
    object Inicio : Pantalla("inicio")
    
    object FormularioMedicamento : Pantalla(
        "formulario_medicamento?medicamentoId={medicamentoId}&nombre={nombre}"
    ) {
        fun crearRuta(medicamentoId: Int? = null, nombre: String? = null): String {
            var ruta = if (medicamentoId != null) {
                "formulario_medicamento?medicamentoId=$medicamentoId"
            } else {
                "formulario_medicamento"
            }
            if (nombre != null) {
                ruta += if (medicamentoId != null) "&nombre=$nombre" else "?nombre=$nombre"
            }
            return ruta
        }
    }
}

// Uso:
navController.navigate(Pantalla.FormularioMedicamento.crearRuta(nombre = "Ibuprofeno"))
```

**Beneficios**:
- Errores de compilación si cambias rutas
- Autocompletado en el IDE
- Refactorización segura

### ViewModelAuth Compartido

```kotlin
@Composable
fun GrafoNavegacion(navController: NavHostController) {
    // UNA SOLA instancia de ViewModelAuth
    val viewModelAuth: ViewModelAuth = hiltViewModel(
        LocalContext.current as ComponentActivity
    )
    
    NavHost(navController, startDestination = Pantalla.Login.ruta) {
        composable(Pantalla.Login.ruta) {
            PantallaLogin(navController, viewModelAuth)  // Mismo ViewModel
        }
        composable(Pantalla.Inicio.ruta) {
            PantallaInicio(navController, viewModelAuth)  // Mismo ViewModel
        }
        // Todas las pantallas reciben la MISMA instancia
    }
}
```

**Beneficio**: Estado de autenticación consistente en toda la app. No se pierde userId al navegar entre pantallas.

### Componentes Reutilizables

```kotlin
@Composable
fun FilaDetalle(etiqueta: String, valor: String) {
    Column {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Uso en múltiples pantallas:
FilaDetalle("Dosis", medicamento.dosis)
FilaDetalle("Frecuencia", medicamento.frecuencia)
FilaDetalle("Fecha Inicio", medicamento.fechaInicio)
```

**Beneficio**: Consistencia visual y menos código duplicado.

## Problemas Conocidos y Limitaciones

### OpenFDA API

- Puede devolver 404 si el medicamento no existe en la base de datos de la FDA
- No todos los medicamentos tienen información de etiqueta completa
- Limitado a medicamentos aprobados por la FDA (principalmente USA)

### Auth0

- Requiere conexión a internet para autenticación inicial
- La sesión se mantiene localmente pero no sincroniza automáticamente con Auth0
- Al cambiar de dispositivo, el usuario debe hacer login de nuevo

### Base de Datos

- No hay sincronización en la nube (datos solo locales)
- Si se desinstala la app, se pierden todos los datos
- No hay sistema de backup automático

### Database Inspector

- Solo funciona en versión debug
- No funciona con APK release firmado
- Requiere dispositivo/emulador con USB debugging activado

## Mejoras Futuras Planificadas

- Notificaciones push para recordatorios de medicación
- Gráficos de evolución de síntomas con MPAndroidChart
- Exportación de datos a PDF
- Sincronización en la nube con Firebase
- Widget de homescreen con próximas citas
- Integración con Google Calendar
- Modo offline completo con caché de OpenFDA
- Autenticación biométrica (huella/face ID)
- Tests unitarios de ViewModels
- Tests de integración de Room
- Tests de UI con Compose Testing
- CI/CD con GitHub Actions

## Control de Versiones

**Estadísticas del repositorio**:
- Total de commits: 45+
- Branches: main
- Fecha entrega: 22/02/2026

## Autor

**Guillermo Sierra Castejón**  
Proyecto Final Asignaturas PSP y PMDM  
Curso 2025/2026  
IES Isidra de Guzmán

## Contacto

- **Email**: sierracastejong@gmail.com
- **GitHub**: [GuillermoSierra](https://github.com/GuillermoSierra)
- **Repositorio**: [ProyectoPSPyPMPDM](https://github.com/GuillermoSierra/ProyectoPSPyPMPDM)