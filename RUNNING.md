# Compilar y probar este fork

Este repositorio personal integra la rama `current-stable` de [AndBible/and-bible](https://github.com/AndBible/and-bible). El proyecto mantiene su licencia y atribuciones originales. Las instrucciones de desarrollo del original están en [AGENTS.md](AGENTS.md) y [CLAUDE.md](CLAUDE.md).

## Android Studio en Windows

1. Abre la raíz como proyecto Gradle y selecciona JDK 17 para Gradle. Instala los componentes Android indicados en `app/build.gradle.kts` mediante SDK Manager.
2. Inicializa el motor JSword: `git submodule update --init --recursive`. No sustituyas su commit fijado por la última rama de JSword.
3. Usa Node 24 con npm 11 para `app/bibleview-js`. Agrega su directorio a PATH antes de iniciar Gradle o Android Studio.
4. Selecciona la variante `standardGithubDebug` para una APK de prueba sin claves de publicación.

```powershell
# JAVA_HOME debe apuntar al JDK 17; ANDROID_HOME, a tu SDK
.\gradlew.bat assembleStandardGithubDebug
.\gradlew.bat testStandardGoogleplayDebugUnitTest
```

La APK queda en `app/build/outputs/apk/standardGithub/debug/`. Instálala en un emulador o dispositivo de prueba compatible usando Android Studio o `adb install -r <apk>`.

## Frontend de la aplicación

```powershell
cd app/bibleview-js
npm ci --ignore-scripts
npm run test:ci
npm run lint:ci
npm run type-check:ci
npm run build-debug
```

Las pruebas JVM usan el modo nativo de SQLite para evitar las conexiones simuladas inválidas del modo antiguo en Windows. La suite completa también requiere módulos de prueba como ESV2011: el CI original los descarga mediante secretos y un archivo cifrado, según `.github/workflows/run-tests.yml`. Esos módulos no están incluidos en Git y no se reemplazaron por datos inventados. Sin ellos, algunas pruebas de contenido quedan pendientes aunque la APK compile.

La primera compilación descarga Gradle, dependencias y herramientas, y puede tardar bastante. No requiere descifrar el archivo de firma de producción para generar la variante Debug. Las funcionalidades con datos bíblicos necesitan descargar módulos desde la propia aplicación; compilar no comprueba todos los proveedores ni funciones de red.

## Sincronizar de nuevo

El remoto `origin` es el fork personal. `upstream` apunta al original y su URL de push está deshabilitada para evitar publicar allí por accidente. Integra `upstream/current-stable` en una rama de trabajo y conserva el historial, las licencias y las mejoras locales. No uses un reset para actualizar el fork.

## Módulos de prueba y comprobaciones de plataforma

Puedes proporcionar módulos SWORD genuinos sin modificar el código ni el directorio personal:

```powershell
.\gradlew.bat testStandardGoogleplayDebugUnitTest -PtestModulesDir="D:/ruta/a/modulos"
```

La carpeta debe contener `mods.d/` y los datos de los módulos. Se verificaron módulos públicos KJV, FinRK, FinSTLK2017, FinPR, ISV y StrongsGreek descargados de CrossWire. El conjunto completo sigue necesitando ESV2011. Según la [FAQ del original](https://andbible.org/docs/faq/), el proveedor retiró esa edición de las descargas SWORD; un respaldo existente permite recuperarla. Los textos de prueba se mantienen fuera de Git y no se sustituyen por otras traducciones con un identificador falso.

Los ZIP usan separadores independientes del sistema operativo. La comprobación de URI FileProvider se omite únicamente en la JVM de Windows, cuyo sistema de rutas no representa el de Android. La misma funcionalidad se comprueba en el dispositivo mediante `FileProviderSharingTest`, que escribe, comparte y vuelve a leer archivos de prueba en backup y export. La suite completa mantiene visibles los fallos por datos ausentes.
