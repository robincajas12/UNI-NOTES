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

En tu código (`primitivas/Triangle.java`), las coordenadas de los vértices se definen en un array de `float` y luego se convierten en un `FloatBuffer`.

```java
// primitivas/Triangle.java

// Coordenadas de los vértices del triángulo en el orden: X, Y, Z
static float triangleCoords[] = {
    0.0f,  0.5f, 0.0f, // Vértice superior
   -0.5f, -0.5f, 0.0f, // Vértice inferior izquierdo
    0.5f, -0.5f, 0.0f  // Vértice inferior derecho
};

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
uniform mat4 uMVPMatrix; // Matriz de transformación (Modelo-Vista-Proyección)
attribute vec4 vPosition;  // Atributo de entrada: la posición del vértice

void main() {
  // Calcula la posición final del vértice en la pantalla
  gl_Position = uMVPMatrix * vPosition;
}
```

**Fragment Shader (`primitivas/MyGLRenderer.java`)**

```glsl
precision mediump float; // Define la precisión por defecto para los floats
uniform vec4 vColor;     // La variable de color que pasamos desde Java

void main() {
  // Asigna el color final al píxel
  gl_FragColor = vColor;
}
```

### 1.4. El Proceso de Dibujado (`draw` method)

El método `draw()` en tus clases de primitivas (`Triangle.java`) orquesta el renderizado:

```java
public void draw(float[] mvpMatrix) {
    GLES20.glUseProgram(mProgram);

    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    GLES20.glEnableVertexAttribArray(mPositionHandle);

    GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                 GLES20.GL_FLOAT, false,
                                 vertexStride, vertexBuffer);

    GLES20.glUniform4fv(mColorHandle, 1, color, 0);

    GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    GLES20.glDisableVertexAttribArray(mPositionHandle);
}
```

---

## Capítulo 2: El Mundo 3D - Transformaciones y Cámara

Un objeto 3D no vive en el vacío. Necesita ser posicionado, orientado y visto desde una perspectiva. Esto se logra con la **matriz Modelo-Vista-Proyección (MVP)**.

`uMVPMatrix = mProjectionMatrix * mViewMatrix * mModelMatrix`

Esta multiplicación se aplica a cada vértice en el Vertex Shader. El orden es crucial (se lee de derecha a izquierda):

1.  **Matriz de Modelo (`mModelMatrix`):** Transforma el objeto desde su espacio local al espacio del mundo.
2.  **Matriz de Vista (`mViewMatrix`):** Posiciona la "cámara".
3.  **Matriz de Proyección (`mProjectionMatrix`):** Define el campo de visión de la cámara, creando la perspectiva.

En tu código de `manejocamara/MyGLRenderer.java`, se ve claramente:

```java
// manejocamara/MyGLRenderer.java -> onSurfaceChanged()
GLES20.glViewport(0, 0, width, height);
float ratio = (float) width / height;
Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

// manejocamara/MyGLRenderer.java -> onDrawFrame()
Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
```

---

## Capítulo 3: Iluminando la Escena

Un mundo 3D sin luces es plano y aburrido. La iluminación es una de las técnicas más importantes para dar profundidad y realismo.

**Concepto Clave: Las Normales**

Para que la iluminación funcione, cada vértice debe tener un **vector normal**. Una normal es un vector unitario que es perpendicular a la superficie y que indica hacia dónde "mira".

### 3.1. Luz Difusa (Proyecto `diffuselight`)

La luz difusa es la luz que incide en una superficie y se dispersa en todas las direcciones por igual. La intensidad depende del ángulo entre la normal de la superficie (`N`) y el vector de luz (`L`).

**Vertex Shader (`diffuselight/MyGLRenderer.java`):**

```glsl
uniform mat4 u_MVPMatrix;   // Matriz Modelo-Vista-Proyección (para la posición final)
uniform mat4 u_MVMatrix;    // Matriz Modelo-Vista (para transformar a espacio de vista)

attribute vec4 a_Position;
attribute vec3 a_Normal;

varying vec3 v_Position;    // Pasa la posición en el espacio de la vista
varying vec3 v_Normal;      // Pasa la normal transformada

void main() {
    // 1. Calcular la posición del vértice en el espacio de la vista y pasarla al Fragment Shader.
    v_Position = vec3(u_MVMatrix * a_Position);

    // 2. Transformar el vector normal al espacio de la vista.
    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));

    // 3. Calcular la posición final del vértice en coordenadas de recorte de la pantalla.
    gl_Position = u_MVPMatrix * a_Position;
}
```

**Fragment Shader (`diffuselight/MyGLRenderer.java`):**

```glsl
precision mediump float;

uniform vec3 u_LightPos;   // Posición de la luz (en espacio de la vista)
uniform vec4 u_Color;      // Color base del objeto

varying vec3 v_Position;   // Posición del fragmento (interpolada)
varying vec3 v_Normal;     // Normal del fragmento (interpolada)

void main() {
    vec3 normal = normalize(v_Normal);
    vec3 lightVector = normalize(u_LightPos - v_Position);

    float diffuseFactor = max(dot(normal, lightVector), 0.0);

    vec3 ambientColor = 0.2 * u_Color.rgb;
    vec3 diffuseColor = diffuseFactor * u_Color.rgb;

    gl_FragColor = vec4(ambientColor + diffuseColor, u_Color.a);
}
```

### 3.2. Luz Especular (Proyecto `especular`)

La luz especular simula los reflejos brillantes en superficies pulidas. Se usa el modelo **Blinn-Phong**.

**Vertex Shader (`especular/MyGLRenderer.java`):**

El Vertex Shader es idéntico al del caso de la luz difusa. Su trabajo sigue siendo pasar la posición y la normal en el espacio de la vista.

```glsl
uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;

attribute vec4 a_Position;
attribute vec3 a_Normal;

varying vec3 v_Position;
varying vec3 v_Normal;

void main() {
    v_Position = vec3(u_MVMatrix * a_Position);
    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));
    gl_Position = u_MVPMatrix * a_Position;
}
```

**Fragment Shader (`especular/MyGLRenderer.java`):**

Este shader es más complejo, ya que calcula tres componentes de luz: ambiente, difusa y especular.

```glsl
precision mediump float;

uniform vec3 u_LightPos;   // Posición de la luz en el espacio de la vista
uniform vec4 u_Color;      // Color base del objeto

varying vec3 v_Position;   // Posición del fragmento en el espacio de la vista
varying vec3 v_Normal;     // Normal del fragmento en el espacio de la vista

void main() {
    // --- Componente Ambiente ---
    vec3 ambientColor = 0.2 * u_Color.rgb;

    // --- Componente Difusa ---
    vec3 normal = normalize(v_Normal);
    vec3 lightVector = normalize(u_LightPos - v_Position);
    float diffuseFactor = max(dot(normal, lightVector), 0.0);
    vec3 diffuseColor = u_Color.rgb * diffuseFactor;

    // --- Componente Especular (Blinn-Phong) ---
    vec3 viewVector = normalize(-v_Position); // Vector desde el fragmento hacia la cámara
    vec3 halfwayVector = normalize(lightVector + viewVector);
    float shininess = 32.0; // Controla el tamaño del brillo
    float specularFactor = pow(max(dot(normal, halfwayVector), 0.0), shininess);
    vec3 specularColor = vec3(0.8, 0.8, 0.8) * specularFactor; // Color del brillo (blanco)

    // --- Color Final ---
    vec3 finalColor = ambientColor + diffuseColor + specularColor;

    gl_FragColor = vec4(finalColor, u_Color.a);
}
```

### 3.3. Foco de Luz (Spotlight) (Proyecto `spotlight`)

Un foco de luz es una fuente de luz limitada a un cono.

**Vertex Shader (`spotlight/MyGLRenderer.java`):**

Nuevamente, el Vertex Shader no cambia. Su responsabilidad es la misma.

```glsl
uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;

attribute vec4 a_Position;
attribute vec3 a_Normal;

varying vec3 v_Position;
varying vec3 v_Normal;

void main() {
    v_Position = vec3(u_MVMatrix * a_Position);
    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));
    gl_Position = u_MVPMatrix * a_Position;
}
```

**Fragment Shader (`spotlight/MyGLRenderer.java`):**

Este shader calcula la iluminación estándar, pero la modula por un `spotEffect` para atenuar la luz fuera del cono.

```glsl
precision mediump float;

uniform vec4 u_Color;
uniform vec3 u_LightPos;
uniform vec3 u_SpotDirection;   // Dirección normalizada del foco
uniform float u_SpotCutoff;     // Coseno del ángulo de corte del cono
uniform float u_SpotExponent;   // Exponente para suavizar el borde del cono

varying vec3 v_Position;
varying vec3 v_Normal;

void main() {
    vec3 normal = normalize(v_Normal);
    vec3 lightVector = u_LightPos - v_Position;
    vec3 L = normalize(lightVector);

    // Factor de atenuación del foco
    float spotEffect = dot(normalize(u_SpotDirection), -L);

    vec3 finalColor = 0.15 * u_Color.rgb; // Luz ambiente mínima

    if (spotEffect > u_SpotCutoff) {
        spotEffect = pow(spotEffect, u_SpotExponent);

        // Iluminación Difusa
        float diffuseFactor = max(dot(normal, L), 0.0);
        vec3 diffuseColor = u_Color.rgb * diffuseFactor;

        // Iluminación Especular
        vec3 viewVector = normalize(-v_Position);
        vec3 halfwayVector = normalize(L + viewVector);
        float specularFactor = pow(max(dot(normal, halfwayVector), 0.0), 32.0);
        vec3 specularColor = vec3(0.8, 0.8, 0.8) * specularFactor;

        // El color se modula por el efecto del foco
        finalColor = finalColor + (diffuseColor + specularColor) * spotEffect;
    }

    gl_FragColor = vec4(finalColor, u_Color.a);
}
```

---

## Capítulo 4: Vistiendo los Objetos - Texturas

Las texturas aplican imágenes 2D a las superficies 3D.

### 4.1. Coordenadas de Textura (UV)

Cada vértice necesita coordenadas de textura (U, V) que indican qué punto de la imagen le corresponde.

### 4.2. Shaders para Texturizado

**Vertex Shader (`texturas3d/MyGLRenderer.java`):**

```glsl
uniform mat4 u_MVPMatrix;

attribute vec4 a_Position;
attribute vec2 a_TexCoordinate; // Atributo para las coordenadas UV

varying vec2 v_TexCoordinate;   // Pasa las coordenadas UV al Fragment Shader

void main() {
    v_TexCoordinate = a_TexCoordinate;
    gl_Position = u_MVPMatrix * a_Position;
}
```

**Fragment Shader (`texturas3d/MyGLRenderer.java`):**

```glsl
precision mediump float;

uniform sampler2D u_Texture;    // La unidad de textura (la imagen)

varying vec2 v_TexCoordinate;   // Coordenadas UV interpoladas

void main() {
    // texture2D() busca el color en la textura en las coordenadas dadas
    gl_FragColor = texture2D(u_Texture, v_TexCoordinate);
}
```

---

## Capítulo 5: Técnicas Avanzadas

### 5.1. Transparencia y Mezcla (Blending)

La transparencia se logra con **Alpha Blending**, mezclando el color del objeto nuevo con el que ya está en pantalla.

**Activación en Java:**
```java
GLES20.glEnable(GLES20.GL_BLEND);
GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
```

**Vertex Shader (`transparencia/MyGLRenderer.java`):**

El Vertex Shader es el mismo que para la iluminación, ya que necesitamos la posición y la normal para calcular el color base antes de aplicar la transparencia.

```glsl
uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;

attribute vec4 a_Position;
attribute vec3 a_Normal;

varying vec3 v_Position;
varying vec3 v_Normal;

void main() {
    v_Position = vec3(u_MVMatrix * a_Position);
    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));
    gl_Position = u_MVPMatrix * a_Position;
}
```

**Fragment Shader (`transparencia/MyGLRenderer.java`):**

Este shader calcula el color de la iluminación y luego le asigna un valor alfa específico para la transparencia.

```glsl
precision mediump float;

uniform vec4 u_Color;      // Color base (RGBA). El alfa viene de aquí.
uniform vec3 u_LightPos;   // Posición de la luz

varying vec3 v_Position;
varying vec3 v_Normal;

void main() {
    // --- Cálculo de Iluminación (ejemplo con difusa) ---
    vec3 normal = normalize(v_Normal);
    vec3 lightVector = normalize(u_LightPos - v_Position);
    float diffuseFactor = max(dot(normal, lightVector), 0.0);

    vec3 ambient = 0.2 * u_Color.rgb;
    vec3 diffuse = diffuseFactor * u_Color.rgb;
    vec3 lightedColor = ambient + diffuse;

    // --- Aplicación de Transparencia ---
    // El color final usa el RGB de la luz, pero el Alfa se toma del
    // color que se pasa desde Java (u_Color.a).
    gl_FragColor = vec4(lightedColor, u_Color.a);
}
```

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

```java
int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

mProgram = GLES20.glCreateProgram();
GLES20.glAttachShader(mProgram, vertexShader);
GLES20.glAttachShader(mProgram, fragmentShader);
GLES20.glLinkProgram(mProgram);
```

**Paso 2: Obtener "Handles" (Punteros) a las Variables del Shader (en el método `draw`)**

```java
GLES20.glUseProgram(mProgram);

mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
```

**Paso 3: Usar los Handles para Enviar Datos (en el método `draw`)**

```java
// Para los 'attribute':
GLES20.glEnableVertexAttribArray(mPositionHandle);
GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                             GLES20.GL_FLOAT, false,
                             vertexStride, vertexBuffer);

// Para los 'uniform':
GLES20.glUniform4fv(mColorHandle, 1, color, 0);
GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
```

**Paso 4: Dibujar y Limpiar**

```java
GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
GLES20.glDisableVertexAttribArray(mPositionHandle);
```
