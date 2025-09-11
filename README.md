# FIDE2Wikipedia

Un programa sencillo para convertir [los XML oficiales de la federación internacional de ajedrez (FIDE)](https://ratings.fide.com/download_lists.phtml) con las puntuaciones
de las distintas categorías en el formato de tabla utilizado en
[la página con los rankings de la Wikipedia en Español](https://es.wikipedia.org/wiki/Ranking_FIDE).

## Cómo ejecutar

Para ejecutar el programa, asegúrate de tener Java 21 o superior instalado.

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/javiermf/fide2wikipedia.git
    cd fide2wikipedia
    ```

2.  **Ejecutar con Makefile:**
    Puedes usar el `Makefile` para construir y ejecutar la aplicación.

    *   **Construir el proyecto:**
        ```bash
        make build
        ```
    *   **Ejecutar el programa:**
        ```bash
        make run
        ```
        Esto descargará los archivos XML de la FIDE, los procesará y generará un archivo `output-YYYY-MM-DD.txt` (donde `YYYY-MM-DD` es la fecha actual) en el directorio raíz del proyecto con la tabla de Wikipedia.