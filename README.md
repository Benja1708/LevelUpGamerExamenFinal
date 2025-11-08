# 🎮 LevelUpGamer

**LevelUpGamer** es una aplicación de Android diseñada como un **catálogo de productos para periféricos de gaming**.  
Permite a los usuarios **navegar por una lista de productos**, **ver sus detalles** e **interactuar con ellos mediante códigos QR**.  

El proyecto está construido utilizando **las últimas tecnologías** y **las mejores prácticas del desarrollo moderno de Android**.

---

## ✨ Características Principales

- **Catálogo de Productos:** Muestra una lista de productos de gaming (teclados, ratones, monitores, etc.) obtenidos desde una base de datos local.  
- **Base de Datos Autocargable:** La base de datos se puebla automáticamente con una lista inicial de productos la primera vez que se ejecuta la aplicación.  
- **Detalles del Producto:** Cada producto tiene una pantalla dedicada con su información completa.  
- **Generación de Códigos QR:** Cada producto genera un código QR único basado en su ID.  
- **Escáner QR Inteligente:**  
  - Al escanear el QR de un producto, la app navega directamente a su pantalla de detalles.  
  - Puede extenderse para leer credenciales de usuario u otros tipos de datos.  
- **Inicio de Sesión de Usuario:** Incluye una pantalla de inicio de sesión simple.  

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Kotlin  
- **UI Toolkit:** Jetpack Compose (interfaz declarativa y moderna)  
- **Diseño:** Material 3 (componentes y principios de diseño de Google)  
- **Arquitectura:** MVVM (Model–View–ViewModel)  
- **Base de Datos:** Room (persistencia local)  
- **Asincronía:** Coroutines y Flow  
- **Navegación:** Navigation Compose  
- **Códigos QR:**  
  - [ZXing (JourneyApps)](https://github.com/journeyapps/zxing-android-embedded) para escaneo  
  - [qrcode-kotlin-compose](https://github.com/G00fY2/compose-qrcode) para generación  
- **Gestión de Dependencias:** Gradle con Kotlin DSL  

---

## 🚀 Cómo Empezar

Sigue estos pasos para tener el proyecto funcionando en tu máquina local.

### 🧩 Prerrequisitos

- Android Studio **Iguana | 2023.2.1** o superior  
- **JDK 17** o superior  

---

### ⚙️ Instalación

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/LevelUpGamer.git
