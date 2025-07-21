# Libro de Gráficos por Computadora: OpenGL ES 2.0

¡Bienvenido, futuro experto en gráficos por computadora!

Este libro es tu guía de iniciación al fascinante mundo de OpenGL ES 2.0. A diferencia de otros textos, este se basa enteramente en el código que ya tienes, desglosando cada concepto para que puedas ver la teoría aplicada directamente en la práctica. Nuestro viaje nos llevará desde dibujar una simple línea hasta crear escenas 3D con luces, sombras y texturas.

**Filosofía de OpenGL ES 2.0: El Pipeline Programable**

La versión 2.0 de OpenGL ES marcó una revolución: introdujo el **pipeline de gráficos programable**. A diferencia de las versiones anteriores (el "pipeline de función fija") donde solo podías configurar estados (activar luces, elegir colores), ahora tienes el control casi total del proceso de renderizado a través de pequeños programas llamados **shaders**.

Hay dos tipos de shaders que son obligatorios:

1.  **Vertex Shader:** Se ejecuta una vez por cada vértice (punto) de tus objetos. Su trabajo principal es calcular la posición final de ese vértice en la pantalla. Aquí es donde aplicamos transformaciones 3D (mover, rotar, escalar) y preparamos datos para el siguiente paso.
2.  **Fragment Shader (o Pixel Shader):** Se ejecuta una vez por cada píxel que podría ser visible en la pantalla. Su trabajo es decidir el color final de ese píxel. Aquí es donde ocurre la magia de la iluminación, las texturas y otros efectos visuales.

---

## Capítulo 1: Fundamentos y Primitivas Gráficas

Antes de correr, debemos aprender a caminar. En gráficos, eso significa dibujar las formas más básicas, conocidas como **primitivas**.

### 1.1. El Entorno de OpenGL en Android

En todos tus proyectos, la configuración inicial es similar y se compone de tres clases clave:

*   `MainActivity.java`: Es el punto de entrada de la aplicación. Su única responsabilidad es crear y configurar una `MyGLSurfaceView`.
*   `MyGLSurfaceView.java`: Es un componente de la interfaz de usuario de Android (`GLSurfaceView`) que maneja el ciclo de vida de OpenGL. Aquí se crea una instancia de nuestro `MyGLRenderer`.
*   `MyGLRenderer.java`: ¡El corazón de la operación! Esta clase implementa la interfaz `GLSurfaceView.Renderer` y define tres métodos cruciales:
    *   `onSurfaceCreated()`: Se llama una sola vez cuando se crea la superficie. Es el lugar perfecto para inicializar objetos, compilar shaders y configurar estados de OpenGL que no cambiarán, como el color de fondo con `glClearColor()`.
    *   `onSurfaceChanged()`: Se llama cuando la superficie cambia de tamaño (por ejemplo, al rotar el dispositivo). Aquí configuramos la "cámara" o el viewport con `glViewport()` y definimos la proyección.
    *   `onDrawFrame()`: ¡La función principal del bucle de renderizado! Se llama en cada fotograma. Aquí es donde le decimos a OpenGL qué dibujar y cómo dibujarlo.

### 1.2. Definiendo Formas: Vértices

Todo en OpenGL se dibuja a partir de vértices. Un vértice no es solo una posición (X, Y, Z), también puede contener otros atributos como color, coordenadas de textura o normales (vectores de dirección).

En tu código (`primitivas/Triangle.java`, `primitivas/Square.java`), las coordenadas de los vértices se definen en un array de `float` y luego se convierten en un `FloatBuffer`.

```java
// primitivas/Triangle.java

// Coordenadas de los vértices del triángulo en el orden: X, Y, Z
static float triangleCoords[] = {
    0.0f,  0.5f, 0.0f, // Vértice superior
   -0.5f, -0.5f, 0.0f, // Vértice inferior izquierdo
    0.5f, -0.5f, 0.0f  // Vértice inferior derecho
};

// ...

// En el constructor, se inicializa el buffer
ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4); // 4 bytes por float
bb.order(ByteOrder.nativeOrder());
vertexBuffer = bb.asFloatBuffer();
vertexBuffer.put(triangleCoords);
vertexBuffer.position(0);
```

**¿Por qué `FloatBuffer`?** OpenGL es una API nativa (escrita en C). Java y el código nativo no comparten memoria directamente. `FloatBuffer` crea un bloque de memoria nativa donde la máquina virtual de Java no puede mover los datos, garantizando que OpenGL pueda acceder a ellos de forma segura y eficiente.

### 1.3. Los Shaders Más Sencillos

Para dibujar nuestro triángulo, necesitamos un Vertex Shader y un Fragment Shader.

**Vertex Shader (`primitivas/MyGLRenderer.java`)**

```glsl
// Código del Vertex Shader
uniform mat4 uMVPMatrix; // Matriz de transformación (Modelo-Vista-Proyección)
attribute vec4 vPosition;  // Atributo de entrada: la posición del vértice

void main() {
  // Calcula la posición final del vértice en la pantalla
  gl_Position = uMVPMatrix * vPosition;
}
```

*   `attribute vec4 vPosition`: Declara una variable de entrada (un atributo) que recibirá los datos de posición de nuestro `vertexBuffer`.
*   `uniform mat4 uMVPMatrix`: Declara una "uniform", que es una variable global que podemos establecer desde nuestro código Java y que es la misma para todos los vértices. En este caso, es la matriz que transforma nuestro objeto 3D a las coordenadas 2D de la pantalla.
*   `gl_Position`: Es una variable especial de salida. El valor que le asignemos aquí será la posición final del vértice.

**Fragment Shader (`primitivas/MyGLRenderer.java`)**

```glsl
// Código del Fragment Shader
precision mediump float; // Define la precisión por defecto para los floats
uniform vec4 vColor;     // La variable de color que pasamos desde Java

void main() {
  // Asigna el color final al píxel
  gl_FragColor = vColor;
}
```

*   `uniform vec4 vColor`: Otra variable global, esta vez para el color. `vec4` representa un vector de 4 componentes (Rojo, Verde, Azul, Alfa).
*   `gl_FragColor`: Variable especial de salida que define el color del píxel actual.

### 1.4. El Proceso de Dibujado (`draw` method)

El método `draw()` en tus clases de primitivas (`Triangle.java`, `Square.java`, etc.) orquesta el renderizado:

```java
// primitivas/Triangle.java -> draw()
public void draw(float[] mvpMatrix) {
    // 1. Añadir el programa de shaders a OpenGL
    GLES20.glUseProgram(mProgram);

    // 2. Obtener manejadores (handles) para los atributos y uniforms del shader
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    // 3. Habilitar el atributo de posición del vértice
    GLES20.glEnableVertexAttribArray(mPositionHandle);

    // 4. Preparar los datos de coordenadas del triángulo
    GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                 GLES20.GL_FLOAT, false,
                                 vertexStride, vertexBuffer);

    // 5. Establecer el color para el dibujado
    GLES20.glUniform4fv(mColorHandle, 1, color, 0);

    // 6. Pasar la matriz de transformación al shader
    GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

    // 7. Dibujar el triángulo
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    // 8. Deshabilitar el array de vértices
    GLES20.glDisableVertexAttribArray(mPositionHandle);
}
```

Este es el ritual estándar de dibujado en OpenGL ES 2.0. Cada línea tiene un propósito claro para comunicar a la GPU cómo usar nuestros datos y shaders.

---

## Capítulo 2: El Mundo 3D - Transformaciones y Cámara

Un objeto 3D no vive en el vacío. Necesita ser posicionado, orientado y visto desde una perspectiva. Esto se logra con la **matriz Modelo-Vista-Proyección (MVP)**.

`uMVPMatrix = mProjectionMatrix * mViewMatrix * mModelMatrix`

Esta multiplicación se aplica a cada vértice en el Vertex Shader. El orden es crucial (se lee de derecha a izquierda):

1.  **Matriz de Modelo (`mModelMatrix`):** Transforma el objeto desde su espacio local (centrado en el origen, como fue definido) al espacio del mundo. Aquí es donde aplicas rotaciones (`Matrix.rotateM`), traslaciones (`Matrix.translateM`) y escalado (`Matrix.scaleM`) al objeto.
2.  **Matriz de Vista (`mViewMatrix`):** Posiciona la "cámara". Define desde qué punto del espacio estamos mirando, hacia dónde y cuál es la orientación de nuestra cabeza (arriba/abajo). Esto se configura con `Matrix.setLookAtM()`.
3.  **Matriz de Proyección (`mProjectionMatrix`):** Define el campo de visión de la cámara, creando la ilusión de perspectiva. Los objetos más lejanos se ven más pequeños. Esto se configura con `Matrix.frustumM()` (para proyección en perspectiva) o `Matrix.orthoM()` (para proyección 2D sin perspectiva).

En tu código de `manejocamara/MyGLRenderer.java`, se ve claramente:

```java
// manejocamara/MyGLRenderer.java -> onSurfaceChanged()
GLES20.glViewport(0, 0, width, height);
float ratio = (float) width / height;
Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7); // Configura la proyección

// manejocamara/MyGLRenderer.java -> onDrawFrame()
// Configura la cámara (matriz de vista)
Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

// Combina vista y proyección
Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

// ... luego, al dibujar cada objeto, se aplica la matriz de modelo antes de la MVP final.
```

---

## Capítulo 3: Iluminando la Escena

Un mundo 3D sin luces es plano y aburrido. La iluminación es una de las técnicas más importantes para dar profundidad y realismo. En tus proyectos, exploras varios modelos de iluminación.

**Concepto Clave: Las Normales**

Para que la iluminación funcione, cada vértice debe tener un **vector normal**. Una normal es un vector de longitud 1 (unitario) que es perpendicular a la superficie del polígono en ese vértice. Indica hacia dónde "mira" la superficie. La GPU usará este vector para calcular cómo la luz rebota en la superficie.

### 3.1. Luz Difusa (Proyecto `diffuselight`)

La luz difusa es la luz que incide en una superficie y se dispersa en todas las direcciones por igual. Es la razón por la que vemos las superficies que no están directamente de cara a la luz. El modelo más común es la **Reflexión Lambertiana**.

**La Matemática (en el Shader):**

La intensidad de la luz difusa depende del ángulo entre el vector normal de la superficie (`N`) y el vector que va desde la superficie hacia la fuente de luz (`L`).

`Intensidad_Difusa = max(dot(N, L), 0.0)`

*   `dot(N, L)`: El producto punto de dos vectores unitarios nos da el coseno del ángulo entre ellos.
    *   Si son paralelos (la luz incide de frente), el resultado es 1.0 (máxima intensidad).
    *   Si son perpendiculares, el resultado es 0.0 (sin intensidad).
    *   Si apuntan en direcciones opuestas (la luz está detrás de la superficie), el resultado es negativo.
*   `max(..., 0.0)`: Usamos `max` para asegurarnos de que la intensidad no sea negativa.

**Vertex Shader (`diffuselight/MyGLRenderer.java`):**

```glsl
// ... (uniforms para matrices, etc.)
attribute vec4 a_Position;
attribute vec3 a_Normal; // ¡Atributo para las normales!

varying vec3 v_Normal; // "varying": pasa el dato al Fragment Shader

void main() {
    // ... (cálculo de gl_Position)

    // Rotamos la normal junto con el modelo para que apunte en la dirección correcta
    // y la pasamos al Fragment Shader.
    v_Normal = normalize(u_MVMatrix * vec4(a_Normal, 0.0)).xyz;
}
```

**Fragment Shader (`diffuselight/MyGLRenderer.java`):**

```glsl
precision mediump float;

uniform vec3 u_LightPos;   // Posición de la luz en el espacio de la vista
uniform vec4 u_Color;      // Color base del objeto

varying vec3 v_Normal;     // Normal recibida del Vertex Shader
varying vec3 v_Position;   // Posición recibida del Vertex Shader

void main() {
    // Calculamos el vector de luz y lo normalizamos
    vec3 lightVector = normalize(u_LightPos - v_Position);

    // Calculamos el factor difuso usando el producto punto
    float diffuseFactor = max(dot(v_Normal, lightVector), 0.0);

    // El color final es el color del objeto modulado por el factor de luz
    gl_FragColor = u_Color * diffuseFactor;
}
```

### 3.2. Luz Especular (Proyecto `especular`)

La luz especular simula los reflejos brillantes y directos que vemos en superficies pulidas (como metal o plástico). El modelo implementado es **Blinn-Phong**, que es una optimización del modelo de Phong clásico.

**La Matemática (en el Shader):**

Blinn-Phong introduce el **vector intermedio (Halfway vector, `H`)**. Este vector se encuentra a medio camino entre el vector de luz (`L`) y el vector de vista (`V`, que va desde la superficie al ojo/cámara).

`H = normalize(L + V)`

La intensidad especular se calcula entonces como:

`Intensidad_Especular = pow(max(dot(N, H), 0.0), Shininess)`

*   `dot(N, H)`: Comparamos qué tan alineada está la normal de la superficie con este vector intermedio. El brillo será máximo cuando la normal apunte exactamente en la misma dirección que `H`.
*   `pow(..., Shininess)`: Elevamos el resultado a una potencia llamada `Shininess` (brillo especular). Un valor alto de `Shininess` crea un punto de luz especular pequeño y concentrado (como en el metal pulido). Un valor bajo crea un brillo más amplio y difuso (como en el plástico).

**Fragment Shader (`especular/MyGLRenderer.java`):**

```glsl
// ... (uniforms y varyings)
void main() {
    // --- Componente Difusa (igual que antes) ---
    vec3 lightVector = normalize(u_LightPos - v_Position);
    float diffuseFactor = max(dot(v_Normal, lightVector), 0.0);
    vec3 diffuseColor = u_Color.rgb * diffuseFactor;

    // --- Componente Especular ---
    vec3 viewVector = normalize(-v_Position); // Vector hacia la cámara (origen en espacio de vista)
    vec3 halfwayVector = normalize(lightVector + viewVector);
    float specularFactor = pow(max(dot(v_Normal, halfwayVector), 0.0), 32.0); // Shininess = 32.0
    vec3 specularColor = vec3(0.8, 0.8, 0.8) * specularFactor; // Color del brillo especular (blanco)

    // --- Color Final ---
    // Se suma la luz ambiente (un color base para que no haya negro absoluto),
    // la luz difusa y la luz especular.
    gl_FragColor = vec4(u_Color.rgb * 0.2 + diffuseColor + specularColor, u_Color.a);
}
```

### 3.3. Foco de Luz (Spotlight) (Proyecto `spotlight`)

Un foco de luz (spotlight) es una fuente de luz que está limitada a un cono. Es como una linterna. Para implementarlo, necesitamos tres parámetros adicionales:

1.  **Dirección del Foco (`spot_direction`)**: Un vector que indica hacia dónde apunta el cono de luz.
2.  **Ángulo de Corte (`spot_cutoff`)**: El ángulo que define la apertura del cono.
3.  **Exponente del Foco (`spot_exponent`)**: Controla cuán concentrada está la luz en el centro del cono.

**La Matemática (en el Shader):**

El factor de atenuación del foco se calcula así:

`Spot_Factor = pow(max(dot(normalize(-LightToSurface), spot_direction), 0.0), spot_exponent)`

*   `dot(normalize(-LightToSurface), spot_direction)`: Comparamos la dirección del foco con la dirección del rayo de luz que va hacia la superficie. Si están alineados, el resultado es 1.
*   Solo los píxeles donde este producto punto sea mayor que `cos(spot_cutoff)` serán iluminados.
*   `pow(..., spot_exponent)`: El exponente hace que el borde del cono de luz sea más suave y difuminado.

**Fragment Shader (`spotlight/MyGLRenderer.java`):**

```glsl
// ... (uniforms para posición de la luz, color, etc.)
uniform vec3 u_SpotDirection; // Dirección del foco
uniform float u_SpotCutoff;   // Coseno del ángulo de corte
uniform float u_SpotExponent; // Exponente de atenuación

void main() {
    vec3 lightVector = u_LightPos - v_Position;
    float lightDist = length(lightVector);
    lightVector = normalize(lightVector);

    // Calculamos el factor de atenuación del foco
    float spotEffect = dot(normalize(u_SpotDirection), -lightVector);

    // Si el píxel está dentro del cono de luz...
    if (spotEffect > u_SpotCutoff) {
        spotEffect = pow(spotEffect, u_SpotExponent);

        // ... calculamos la iluminación (difusa + especular)
        float diffuse = max(dot(v_Normal, lightVector), 0.0);
        // ... (cálculo especular omitido por brevedad)

        // El color final se modula por el efecto del foco
        gl_FragColor = u_Color * (diffuse * spotEffect /* + specular * spotEffect */);
    } else {
        // Si está fuera del cono, el píxel no se ilumina (o solo tiene luz ambiente)
        gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
    }
}
```

---

## Capítulo 4: Vistiendo los Objetos - Texturas

Las texturas nos permiten aplicar imágenes a la superficie de nuestros modelos 3D, añadiendo un nivel de detalle y realismo que sería imposible de lograr solo con polígonos.

### 4.1. Coordenadas de Textura (UV)

Para mapear una imagen 2D sobre un objeto 3D, cada vértice necesita un par de **coordenadas de textura**, comúnmente llamadas (U, V).

*   `U` es la coordenada horizontal de la imagen (de 0.0 a 1.0, de izquierda a derecha).
*   `V` es la coordenada vertical de la imagen (de 0.0 a 1.0, de abajo hacia arriba en OpenGL).

Estas coordenadas se definen junto con la posición de los vértices y se pasan al Vertex Shader como un atributo más.

```java
// texturas3d/CuadradoTexturizado.java
// Vértices y Coordenadas de Textura intercalados: X, Y, Z, U, V
static float vertices[] = {
    -1.0f,  1.0f, 0.0f,   0.0f, 1.0f, // Vértice sup izq
    -1.0f, -1.0f, 0.0f,   0.0f, 0.0f, // Vértice inf izq
     1.0f, -1.0f, 0.0f,   1.0f, 0.0f, // Vértice inf der
     1.0f,  1.0f, 0.0f,   1.0f, 1.0f  // Vértice sup der
};
```

### 4.2. Carga y Activación de Texturas

El proceso de cargar una imagen y prepararla para OpenGL se encapsula en tu clase `TextureUtils.java`. Los pasos clave son:

1.  **Cargar la Imagen**: Se decodifica un recurso de Android (p. ej., de la carpeta `drawable`) en un objeto `Bitmap`.
2.  **Generar un ID de Textura**: `glGenTextures()` crea un nuevo objeto de textura en la GPU y nos devuelve su ID.
3.  **Vincular la Textura**: `glBindTexture(GLES20.GL_TEXTURE_2D, ...)` le dice a OpenGL que todas las operaciones de textura siguientes se aplicarán a este objeto.
4.  **Configurar Parámetros**: Se definen los filtros de minificación y magnificación (`GL_TEXTURE_MIN_FILTER`, `GL_TEXTURE_MAG_FILTER`), que le dicen a OpenGL cómo manejar la imagen si se ve más pequeña o más grande en pantalla que su tamaño original.
5.  **Cargar los Datos**: `GLUtils.texImage2D()` finalmente copia los píxeles del `Bitmap` a la memoria de la GPU.
6.  **Liberar Memoria**: Se recicla el `Bitmap` de Java, ya que los datos ahora están seguros en la GPU.

### 4.3. Shaders para Texturizado

**Vertex Shader (`texturas3d/MyGLRenderer.java`):**

El Vertex Shader es simple. Su única tarea nueva es recibir las coordenadas de textura (UV) y pasarlas directamente al Fragment Shader.

```glsl
attribute vec4 a_Position;
attribute vec2 a_TexCoordinate; // Atributo para las coordenadas UV

varying vec2 v_TexCoordinate; // Se las pasamos al Fragment Shader

void main() {
    v_TexCoordinate = a_TexCoordinate;
    gl_Position = u_MVPMatrix * a_Position;
}
```

**Fragment Shader (`texturas3d/MyGLRenderer.java`):**

Aquí es donde se "pinta" la textura.

```glsl
precision mediump float;

uniform sampler2D u_Texture; // La unidad de textura (la imagen en sí)

varying vec2 v_TexCoordinate; // Coordenadas UV recibidas del Vertex Shader

void main() {
    // texture2D() busca el color en la imagen de textura
    // en la posición especificada por las coordenadas UV.
    gl_FragColor = texture2D(u_Texture, v_TexCoordinate);
}
```

*   `sampler2D`: Es un tipo de dato especial que representa una textura 2D.
*   `texture2D(sampler, coordinates)`: Es la función mágica. Para el píxel actual, interpola las `v_TexCoordinate` que vienen del Vertex Shader y busca el color correspondiente (el "texel") en la textura `u_Texture`.

El resultado es que la imagen se "envuelve" alrededor de la geometría que hemos definido.

---

## Capítulo 5: Técnicas Avanzadas

### 5.1. Transparencia y Mezcla (Blending)

El proyecto `transparencia` demuestra cómo hacer que los objetos se vean translúcidos. Esto se logra con una técnica llamada **Alpha Blending**.

La idea es que el color final de un píxel es una mezcla del color del objeto que se está dibujando (el "source") y el color que ya está en ese píxel en el framebuffer (el "destination").

La fórmula de mezcla estándar es:

`Color_Final = (Color_Source * Alpha_Source) + (Color_Destination * (1 - Alpha_Source))`

Para activar esto en OpenGL, se necesitan dos comandos, usualmente en `onSurfaceCreated()`:

```java
// transparencia/MyGLRenderer.java
GLES20.glEnable(GLES20.GL_BLEND);
GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
```

*   `glEnable(GL_BLEND)`: Activa el sistema de mezcla.
*   `glBlendFunc(...)`: Configura la fórmula a usar. `GL_SRC_ALPHA` le dice a OpenGL que multiplique el color del source por su propio valor alfa. `GL_ONE_MINUS_SRC_ALPHA` le dice que multiplique el color del destination por `1 - alfa_del_source`.

En el Fragment Shader, simplemente tienes que asegurarte de que el color que generas tenga un componente alfa menor que 1.0.

```glsl
// transparencia/MyGLRenderer.java -> Fragment Shader
void main() {
    // ... (cálculos de luz, etc.)
    // El color final tiene un alfa de 0.5, lo que lo hace 50% transparente.
    gl_FragColor = vec4(diffuseColor, 0.5);
}
```

**¡Importante!** Los objetos transparentes deben dibujarse **después** de los objetos opacos, y preferiblemente ordenados de más lejano a más cercano para que la mezcla funcione correctamente.

---

¡Felicidades! Has completado un recorrido intensivo por los conceptos fundamentales y avanzados de OpenGL ES 2.0, todos ilustrados con tu propio código. Este libro es una referencia viva; úsalo para experimentar, cambiar valores en los shaders, añadir nuevas formas y explorar los infinitos caminos de los gráficos por computadora. ¡El límite es tu imaginación!

---

## Apéndice A: GLSL y la Conexión Java-Shader

Esta es una de las áreas que más confusión causa al principio. ¿Cómo se comunican exactamente mi código Java (que se ejecuta en la CPU) y mis shaders (que se ejecutan en la GPU)? La respuesta está en un "contrato" bien definido a través de variables especiales en GLSL y un conjunto de funciones de la API de OpenGL en Java.

### A.1. Introducción a GLSL (OpenGL Shading Language)

GLSL es un lenguaje de programación similar a C, diseñado para ejecutarse en las GPUs. Tiene tipos de datos que ya conoces (`int`, `float`, `bool`) y otros específicos para gráficos:

*   **Vectores**: `vec2`, `vec3`, `vec4` (para posiciones, colores, coordenadas de textura, etc.).
*   **Matrices**: `mat2`, `mat3`, `mat4` (para transformaciones).
*   **Sampler**: `sampler2D`, `samplerCube` (para acceder a texturas).

La comunicación entre Java y GLSL se basa en tres tipos de "calificadores de almacenamiento":

1.  **`attribute` (Atributo):**
    *   **Propósito**: Pasar datos que son **diferentes para cada vértice**. Esto incluye la posición del vértice, el color del vértice, el vector normal o las coordenadas de textura.
    *   **Dónde se usa**: **Solo en el Vertex Shader**. Es una entrada de datos.
    *   **Cómo se alimenta desde Java**: A través de un `Buffer` (como `FloatBuffer`) y la función `glVertexAttribPointer()`.

2.  **`uniform` (Uniforme):**
    *   **Propósito**: Pasar datos que son **iguales para todos los vértices** en una misma llamada a `draw`. Piensa en ellos como variables globales de solo lectura para los shaders. Ejemplos típicos son la matriz de transformación (MVP), la posición de la luz o el color base de un objeto.
    *   **Dónde se usa**: Tanto en el Vertex Shader como en el Fragment Shader.
    *   **Cómo se alimenta desde Java**: Usando la familia de funciones `glUniform...()` (por ejemplo, `glUniformMatrix4fv` para una matriz 4x4, `glUniform4fv` para un `vec4` de color, `glUniform1i` para un entero).

3.  **`varying` (Variable):**
    *   **Propósito**: Pasar datos **desde el Vertex Shader al Fragment Shader**. OpenGL toma el valor de la `varying` en cada vértice del polígono y lo **interpola** suavemente a lo largo de la superficie del polígono. El Fragment Shader recibe el valor ya interpolado para cada píxel.
    *   **Dónde se usa**: Se declara en ambos shaders con el mismo nombre. Se escribe en el Vertex Shader y se lee en el Fragment Shader.
    *   **Cómo se alimenta**: Es una comunicación interna de la GPU. Java no interactúa directamente con las `varying`.

### A.2. El Ritual de Conexión: Java -> GLSL

Veamos el proceso paso a paso, como se ve en tu código (`primitivas/Triangle.java`).

**Paso 1: Compilar y Vincular los Shaders (en el constructor del objeto)**

Primero, el código de los shaders (que son strings en Java) se compila y se vincula en un "programa" de OpenGL.

```java
// 1. Compilar Vertex Shader
int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
// 2. Compilar Fragment Shader
int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

// 3. Crear un programa de Shaders vacío
mProgram = GLES20.glCreateProgram();

// 4. Adjuntar los shaders al programa
GLES20.glAttachShader(mProgram, vertexShader);
GLES20.glAttachShader(mProgram, fragmentShader);

// 5. Vincular el programa (crea los ejecutables para la GPU)
GLES20.glLinkProgram(mProgram);
```

`mProgram` ahora contiene un ID numérico que es nuestro programa de shaders listo para usar.

**Paso 2: Obtener "Handles" (Punteros) a las Variables del Shader (en el método `draw`)**

No podemos simplemente decir "cambia la variable `vPosition`". Necesitamos obtener un puntero numérico (un "handle" o "location") a esa variable dentro del programa compilado. **Este es el paso clave que conecta el nombre de la variable en el string de GLSL con un ID que Java puede usar.**

```java
// Se le dice a OpenGL: "Voy a usar este programa ahora"
GLES20.glUseProgram(mProgram);

// Obtener el handle para el atributo 'vPosition' del Vertex Shader
mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

// Obtener el handle para la uniform 'vColor' del Fragment Shader
mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

// Obtener el handle para la uniform 'uMVPMatrix' del Vertex Shader
mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
```
*   `glGetAttribLocation`: Se usa para obtener el handle de una variable `attribute`.
*   `glGetUniformLocation`: Se usa para obtener el handle de una variable `uniform`.

Ahora, las variables de Java `mPositionHandle`, `mColorHandle`, y `mMVPMatrixHandle` contienen los IDs que necesitamos.

**Paso 3: Usar los Handles para Enviar Datos (en el método `draw`)**

Una vez que tenemos los handles, podemos usarlos para enviar los datos justo antes de dibujar.

**Para los `attribute`:**

```java
// 1. Habilitar el handle del atributo. Sin esto, la GPU no lo leerá.
GLES20.glEnableVertexAttribArray(mPositionHandle);

// 2. Apuntar al buffer de datos de vértices.
// Esto le dice a la GPU: "Para el atributo en mPositionHandle,
// toma los datos de 'vertexBuffer', cada vértice tiene 'COORDS_PER_VERTEX' floats,
// no están normalizados, el 'stride' (distancia entre vértices) es tanto..."
GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                             GLES20.GL_FLOAT, false,
                             vertexStride, vertexBuffer);
```

**Para los `uniform`:**

```java
// Para la uniform 'vColor', usa el handle mColorHandle para enviar
// el array de floats 'color'.
GLES20.glUniform4fv(mColorHandle, 1, color, 0);

// Para la uniform 'uMVPMatrix', usa el handle mMVPMatrixHandle para enviar
// la matriz 'mvpMatrix'.
GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
```

**Paso 4: Dibujar y Limpiar**

Después de enviar todos los datos, finalmente podemos dibujar.

```java
// Dibuja los triángulos usando los datos y shaders configurados.
GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

// Deshabilitar el handle del atributo como buena práctica.
GLES20.glDisableVertexAttribArray(mPositionHandle);
```

¡Y eso es todo! Este ritual, aunque verboso, es el corazón de la comunicación entre Java y GLSL. Una vez que entiendes que se trata de un proceso de **Compilar -> Obtener Handles -> Usar Handles para Enviar Datos**, el flujo se vuelve mucho más claro.
