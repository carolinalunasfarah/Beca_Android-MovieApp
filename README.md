# Reel Scout - Exploración de Películas 🎞
Aplicación móvil que permite la exploración y gestión de películas. Ofrece funcionalidades como ver películas populares, buscar películas, ver detalles de cada una, marcar películas como favoritas y guardar listado de películas favoritas.

La aplicación utiliza la API de The Movie DB para obtener datos de películas, implementa persistencia local con Room para almacenar los favoritos. La arquitectura del proyecto está basada en Clean Architecture, con MVVM para la gestión del ciclo de vida y Hilt para la inyección de dependencias.

## Características 🧰
- Explorar películas populares  
- Buscar películas por nombre  
- Ver detalles completos de cada película  
- Marcar y desmarcar películas como favoritas  
- Ver lista de favoritos offline  

## Requerimientos Técnicos ⚙
1. API y Networking
The Movie DB API para obtener datos de películas.
Retrofit con Coroutines para manejar la comunicación con la API.
Manejo de errores de red para proporcionar retroalimentación al usuario en caso de problemas de conexión.

2. Persistencia Local
Room para almacenar las películas marcadas como favoritas.

3. UI/UX
Material Design 3 para una interfaz moderna y consistente.
Jetpack Compose para una interfaz de usuario declarativa y fluida.

4. Arquitectura
Clean Architecture para estructurar el código de manera modular y escalable.
MVVM (Modelo-Vista-ViewModel) para separar la lógica de negocio de la interfaz de usuario.
Dependency Injection con Hilt para gestionar las dependencias de manera eficiente.
Repository Pattern para manejar la interacción entre los datos remotos y locales.

6. Testing
Unit Tests para probar las funciones y componentes clave de la aplicación.

## Estructura del Proyecto
- data: Contiene las fuentes de datos, incluyendo las implementaciones de la API y Room.
- domain: La capa de dominio que incluye los casos de uso y los modelos de negocio.
- presentation: Contiene la UI de la aplicación, ViewModels, y eventos de UI.
- di: Configuración de Hilt para la inyección de dependencias.
- utils: Funciones y clases utilitarias, como manejadores de red o conectividad.

## Tecnologías Utilizadas
- Android: Kotlin, Jetpack Compose, Hilt, Room, Retrofit, Coroutines
- API: The Movie DB
- Arquitectura: Clean Architecture, MVVM
- Testing: Unit tests con JUnit, MockK

## Desarrollada con
![Kotlin](https://img.shields.io/badge/Kotlin-grey?style=for-the-badge&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-grey?style=for-the-badge&logo=android)
