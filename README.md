# Reel Scout - Exploración de Películas 🍿
Aplicación móvil que permite la exploración y gestión de películas. Ofrece funcionalidades como ver películas populares, buscar películas, ver detalles de cada una, marcar películas como favoritas y guardar listado de películas favoritas.

La aplicación utiliza la API de The Movie DB para obtener datos de películas, implementa persistencia local con Room para almacenar los favoritos. La arquitectura del proyecto está basada en Clean Architecture, con MVVM para la gestión del ciclo de vida y Hilt para la inyección de dependencias.

## Características 📋
- Explorar películas populares  
- Buscar películas por nombre  
- Ver detalles completos de cada película  
- Marcar y desmarcar películas como favoritas  
- Ver lista de favoritos offline  

## Requerimientos Técnicos 🛠️
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

## Estructura del Proyecto 🗂️
- data: Contiene las fuentes de datos, incluyendo las implementaciones de la API y Room.
- domain: La capa de dominio que incluye los casos de uso y los modelos de negocio.
- presentation: Contiene la UI de la aplicación, ViewModels, y eventos de UI.
- di: Configuración de Hilt para la inyección de dependencias.
- utils: Funciones y clases utilitarias, como manejadores de red o conectividad.

## Tecnologías Utilizadas 🔧
- Android: Kotlin, Jetpack Compose, Hilt, Room, Retrofit, Coroutines
- API: The Movie DB
- Arquitectura: Clean Architecture, MVVM
- Testing: Unit tests con JUnit, MockK

## Cómo hacer fork del proyecto
Si deseas contribuir a este proyecto o usarlo como base para tus desarrollos, sigue estos pasos para hacer un fork:

1. Realizar el Fork
   - Haz click en el botón Fork en la esquina superior derecha de la página.
   - Selecciona la cuenta u organización para crear una copia del repositorio en tu perfil.

2. Clonar el repositorio Forkeado
   - Abre una terminal o tu editor de código favorito.
   - Clona el repositorio en tu máquina local con el siguiente comando:

```
git clone https://github.com/tu-usuari@/nombre-del-repositorio.git
```

  Asegúrate de remplazar tu-usuari@ y nombre-del-repositorio con los datos de tu repositorio forkeado.
  
   - Navega al directorio del proyecto:

```
cd nombre-del-repositorio
```

3. Abrir el repositorio Forkeado
   Ahora puedes abrir el repositorio en tu editor de código elegido.

## Configuración de la API Key 🔑
Para que la aplicación funcione correctamente, es necesario configurar una API Key de The Movie DB. Sigue estos pasos:

1. Obtener una API Key
   - Crea una cuenta en The Movie DB si no tienes una.
   - Ve a la sección API.
   - Solicita una API Key.
   - Copia tu API Key.

2. Configurar la API Key en el proyecto
   - Abre el archivo key.properties en el directorio raíz del proyecto (si no existe, créalo).
   - Agrega la API Key al archivo, de esta forma:
     
```
API_KEY="tu_api_key"
```

   - Asegúrate de incluir el archivo key.properties en .gitignore para evitar exponer tu clave.

3. Modificar el build.gradle
Configura el archivo build.gradle de tu módulo de aplicación para leer la API Key desde key.properties:

 ```
 android {
  defaultConfig {
      val properties = Properties().apply{
          load(rootProject.file("key.properties").render())
      }
      val key: String = properties.getProperties("API_KEY") ?: ""
      buildConfigField("String", "TMDB_API_KEY", "\"$key\"")
  }
}
```

4. Usar la API Key en el código
Ahora se puede acceder a la API Key desde el archivo BuildConfig en tu código:

```
val API_KEY = BuildConfig.TMDB_API_KEY
```

5. Probar la configuración
Ejecuta la aplicación y asegúrate que las solicitudes a The Movie DB funcionan como se espera. Si está todo configurado de manera correcta, la aplicación debería mostrar las películas.


## Desarrollada con 💡
![Kotlin](https://img.shields.io/badge/Kotlin-grey?style=for-the-badge&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-grey?style=for-the-badge&logo=android)
