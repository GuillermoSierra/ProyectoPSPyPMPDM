# HealthCare - Gestor de Salud Personal

Aplicación Android nativa desarrollada en **Kotlin** con **Jetpack Compose** para la gestión personal de salud.
Proyecto académico para los módulos de **PMDM** (Programación Multimedia y Dispositivos Móviles) y **PSP** (Programación de Servicios y Procesos).

---

## Descripción

HealthCare es una aplicación móvil que permite al usuario gestionar su información médica personal de forma segura y organizada.
La app incluye autenticación mediante Auth0, almacenamiento local con Room Database y consulta de medicamentos a través de la API pública de OpenFDA.

---

## Características principales

- **Autenticación segura** con Auth0 (login/logout)
- **Gestión de medicamentos** - CRUD completo con dosis, frecuencia y fechas
- **Gestión de citas médicas** - Registro y seguimiento de citas
- **Registro de síntomas** - Control de síntomas con intensidad (1-10)
- **Directorio de doctores** - Contactos médicos personales
- **Búsqueda de medicamentos** - Consulta en tiempo real a la API de OpenFDA
- **Dashboard** - Resumen visual con contadores de todos los datos
- **Selectores visuales** - DatePicker y TimePicker para fechas y horas
- **Validaciones** - Formularios con validación de campos obligatorios y formatos

---

## Tecnologías utilizadas

| Tecnología | Uso |
|-----------|-----|
| **Kotlin** | Lenguaje de programación |
| **Jetpack Compose** | UI declarativa |
| **Room Database** | Persistencia de datos local |
| **Hilt** | Inyección de dependencias |
| **Retrofit** | Consumo de API REST |
| **Auth0** | Autenticación de usuarios |
| **Navigation Compose** | Navegación entre pantallas |
| **Coroutines + Flow** | Programación asíncrona reactiva |
| **MVVM** | Patrón de arquitectura |
| **Clean Architecture** | Organización del código |

---

## Arquitectura

El proyecto sigue los principios de **Clean Architecture** y el patrón **MVVM**:

```
app/
├── data/
│   ├── local/
│   │   ├── entity/       # Entidades Room (Medicamento, Cita, Sintoma, Doctor)
│   │   ├── dao/          # Data Access Objects
│   │   └── database/     # Configuración de la base de datos
│   ├── remote/           # Cliente Retrofit y DTOs de OpenFDA
│   │   ├── api/          # Interfaz OpenFDAApi
│   │   └── dto/          # Data Transfer Objects
│   └── repository/       # Repositorios de datos
├── domain/
│   ├── model/            # Modelos de dominio
│   └── usecase/          # Casos de uso para cada entidad
└── ui/
    ├── screens/          # Pantallas organizadas por funcionalidad
    │   ├── inicio/
    │   ├── medicamentos/
    │   ├── citas/
    │   ├── sintomas/
    │   ├── doctores/
    │   ├── busqueda/
    │   └── login/
    ├── components/       # Componentes reutilizables
    ├── navigation/       # Grafo de navegación
    └── theme/            # Tema y estilos
```

---

## Pantallas

| Pantalla | Descripción |
|---------|-------------|
| **Login** | Autenticación con Auth0 |
| **Home/Dashboard** | Resumen con contadores y accesos rápidos |
| **Lista Medicamentos** | Listado con opción de eliminar |
| **Detalle Medicamento** | Vista completa con editar/eliminar |
| **Formulario Medicamento** | Crear/editar con DatePicker |
| **Lista Citas** | Listado de citas médicas |
| **Detalle Cita** | Vista completa de la cita |
| **Formulario Cita** | Crear/editar con DatePicker y TimePicker |
| **Lista Síntomas** | Listado con intensidad |
| **Detalle Síntoma** | Vista completa del síntoma |
| **Formulario Síntoma** | Crear/editar con DatePicker y TimePicker |
| **Lista Doctores** | Directorio de doctores |
| **Detalle Doctor** | Vista completa del doctor |
| **Formulario Doctor** | Crear/editar con validación de teléfono y email |
| **Búsqueda OpenFDA** | Búsqueda de medicamentos en la API |

---

## API REST - OpenFDA

La aplicación consume la API pública que se encuentra en el enunciado de la FDA americana para buscar información de medicamentos.

- **Base URL:** `https://api.fda.gov/`
- **Endpoint búsqueda:** `GET /drug/ndc.json`
- **Endpoint etiqueta:** `GET /drug/label.json`
- **Búsqueda por prefijo:** Soporta wildcard (*) para búsquedas parciales
- **Manejo de errores:** Control de error 404 y errores de conexión

---

## Autenticación Auth0

- **Dominio:** `dev-bbezl7kkcc25rf77.us.auth0.com`
- **Tipo de aplicación:** Native
- **Flujo:** WebAuthProvider con PKCE
- **Funcionalidades:** Login, logout con confirmación, protección de rutas

---

## Configuración del proyecto

### Requisitos previos
- Android Studio Hedgehog o superior
- Android SDK 24 o superior (minSdk 24)
- Cuenta en Auth0

### Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/GuillermoSierra/HealthCare.git
```

2. Abre el proyecto en **Android Studio**

3. Configura tus credenciales de Auth0 en `res/values/auth0.xml`:
```xml
<string name="com_auth0_domain">TU_DOMINIO.auth0.com</string>
<string name="com_auth0_client_id">TU_CLIENT_ID</string>
```

4. Sincroniza las dependencias con **Sync Now**

5. Ejecuta la app en un emulador o dispositivo físico

### Generar APK

```bash
./gradlew assembleDebug
```

O desde Android Studio: **Build → Build APK(s)**

### Instalar APK via ADB

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## Dependencias principales

```
// Jetpack Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// Hilt - Inyección de dependencias
implementation("com.google.dagger:hilt-android:2.48")
implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

// Retrofit - API REST
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Auth0
implementation("com.auth0.android:auth0:2.10.2")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.6")
```

---

## Autor

**Guillermo Sierra**
- GitHub: [@GuillermoSierra](https://github.com/GuillermoSierra)

---

## Resultados de aprendizaje utilizados

### PMDM (Programación Multimedia y Dispositivos Móviles)
- **RA3** - Programación de interfaces de usuario con Jetpack Compose
- **RA4** - Aplicación del patrón MVVM con ViewModels y StateFlow
- **RA5** - Persistencia de datos con Room Database

### PSP (Programación de Servicios y Procesos)
- **RA4** - Consumo de servicios REST con Retrofit y API OpenFDA
- **RA5** - Autenticación segura con Auth0