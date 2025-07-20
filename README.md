# Henry Food

Aplicación móvil desarrollada en **Kotlin** utilizando **Jetpack Compose**, **MVVM**, y conectada a una API REST. Permite gestionar productos, usuarios y órdenes, con persistencia local y sincronización.

## Funcionalidades principales

- **Login y registro** con validaciones de email y contraseña.
- **Catálogo de productos**: listado, búsqueda y filtrado.
- **Carrito de compras**: añadir, editar cantidad, eliminar productos y cálculo de total.
- **Perfil de usuario**: edición de todos los campos y guardado de cambios.
- **Carga de imagen** desde galería o cámara, subida a Cloudinary.
- **Historial de órdenes**: muestra y actualiza las órdenes realizadas.

## Conectividad y servicios

- Consumo de datos desde **API REST** usando **Retrofit**.
- Configuración de `ApiService` con todos los endpoints requeridos.
- **Loaders** visuales durante requests de red.
- **Inyección de dependencias** con **Hilt** (ViewModels, repositorios, servicios).
- Solicitud de permisos de cámara y galería con diálogos claros.

## Persistencia y sincronización

- **Carrito y órdenes** guardados con **Room**, persisten al cerrar la app.
- Base de datos con versionado y migraciones actualizadas.
- **WorkManager** simula sincronización o migración de datos.

## Arquitectura y testing

- Patrón **MVVM** en todas las pantallas.
- Organización en paquetes/módulos: UI, ViewModel, data, domain.
- **ViewModels** con gestión de estado reactivo y asincrónico.
- Pruebas unitarias para ViewModels y repositorios usando mocks.

## Interfaz de usuario

- UI desarrollada completamente con **Jetpack Compose**.
- Soporte para **modo claro y oscuro**.
- Interfaz clara, ordenada y sin errores visuales graves.

## Calidad y entrega

- Sin logs de depuración ni valores hardcodeados.
- Proyecto compila y navega entre todas las pantallas sin errores.
- Repositorio actualizado y organizado con commits claros.

## Consideraciones sobre la API

- Se realizó un cambio en la API para separar órdenes por usuario (correo), evitando mezclar órdenes de distintos usuarios.
- Para pruebas, se puede utilizar la siguente url: https://peyademoappapi-main.onrender.com/
- Recomendación: realizar una consulta previa por Postman o web para "despertar" el servicio de Render (versión free puede demorar más de 50 segundos en la primera consulta tras inactividad).

## Instalación y ejecución

1. Clonar el repositorio:
   ```
   git clone https://github.com/AntoAldao/HenryApp.git
   ```
2. Abrir en **Android Studio**.
3. Configurar en `local.properties`
```
    CLOUDINARY_CLOUD_NAME=cloudinary_name
    CLOUDINARY_UPLOAD_PRESET=cloudinary_preset
    BASE_URL=https://peyademoappapi-main.onrender.com/
```
4. Compilar y ejecutar en un dispositivo/emulador Android.

## Tecnologías utilizadas

- Kotlin, Jetpack Compose, MVVM
- Retrofit, Hilt, Room, WorkManager
- Cloudinary (subida de imágenes)
- Android Studio

---

**Autor:** AntoAldao  
**Repositorio:** [HenryApp](https://github.com/AntoAldao/HenryApp)

¿Dudas o sugerencias? Abrir un issue en el repositorio.