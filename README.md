
## 🧰 Categorías Principales de Operadores de Agregación

### 1. Operadores Aritméticos

* `$add`: Suma valores numéricos o fechas.

  ```js
  { $add: [ "$precio", "$impuesto" ] }
  // Acepta números, fechas (para sumar días/horas), o arreglos.
  ```

* `$subtract`: Resta un valor de otro.

  ```js
  { $subtract: [ "$total", "$descuento" ] }
  // Acepta números o fechas.
  ```

* `$multiply`: Multiplica valores numéricos.

  ```js
  { $multiply: [ "$cantidad", "$precioUnitario" ] }
  ```

* `$divide`: Divide un valor por otro. El segundo valor no debe ser cero.

  ```js
  { $divide: [ "$total", "$cantidad" ] }
  ```

* `$abs`: Devuelve el valor absoluto de un número.

  ```js
  { $abs: "$diferencia" }
  ```

* `$ceil`, `$floor`, `$round`, `$trunc`: Redondean valores numéricos.

  ```js
  { $ceil: "$valor" } // Redondea hacia arriba
  { $floor: "$valor" } // Redondea hacia abajo
  { $round: { input: "$valor", place: 2 } } // Redondeo con precisión
  { $trunc: { input: "$valor", place: 1 } } // Trunca el valor
  ```

### 2. Operadores de Acumulación

Usados dentro de `$group`, `$setWindowFields` u otras etapas para cálculos acumulativos.

* `$sum`: Suma los valores de un grupo.

  ```js
  { $sum: "$ventas" }
  ```

* `$avg`: Calcula el promedio.

  ```js
  { $avg: "$calificacion" }
  ```

* `$min`, `$max`: Devuelve el valor mínimo o máximo.

  ```js
  { $min: "$precio" }, { $max: "$precio" }
  ```

* `$push`: Empuja cada valor al arreglo resultante.

  ```js
  { $push: "$producto" }
  ```

* `$addToSet`: Agrega valores únicos al arreglo.

  ```js
  { $addToSet: "$categoria" }
  ```

### 3. Operadores Lógicos y de Comparación

* `$eq`, `$ne`: Igualdad y desigualdad.

  ```js
  { $eq: [ "$estado", "activo" ] }
  ```

* `$gt`, `$gte`, `$lt`, `$lte`: Comparaciones de mayor/menor.

  ```js
  { $gt: [ "$edad", 18 ] }
  ```

* `$and`, `$or`, `$not`: Operadores lógicos.

  ```js
  { $and: [ { $gt: [ "$edad", 18 ] }, { $lt: [ "$edad", 65 ] } ] }
  ```

* `$cond`: Estructura condicional.

  ```js
  {
    $cond: {
      if: { $gte: [ "$puntuacion", 90 ] },
      then: "Aprobado",
      else: "Reprobado"
    }
  }
  ```

### 4. Operadores de Fechas

* `$dateToString`: Convierte fecha a texto formateado.

  ```js
  { $dateToString: { format: "%Y-%m-%d", date: "$fecha" } }
  ```

* `$year`, `$month`, `$dayOfMonth`, `$hour`, `$minute`, `$second`: Extrae componentes de fecha.

  ```js
  { $year: "$fecha" }
  ```

* `$dateAdd`, `$dateSubtract`: Suma o resta unidades de tiempo.

  ```js
  {
    $dateAdd: {
      startDate: "$fecha",
      unit: "day", // Valores válidos: "year", "quarter", "month", "week", "day", "hour", "minute", "second", "millisecond"
      amount: 7
    }
  }
  ```

* `$dateDiff`: Calcula la diferencia entre dos fechas.

  ```js
  {
    $dateDiff: {
      startDate: "$inicio",
      endDate: "$fin",
      unit: "day"
    }
  }
  ```

### 5. Operadores de Arreglos

* `$arrayElemAt`: Accede al elemento en un índice específico.

  ```js
  { $arrayElemAt: [ "$productos", 0 ] }
  ```

* `$size`: Retorna el tamaño de un arreglo.

  ```js
  { $size: "$comentarios" }
  ```

* `$filter`: Filtra elementos de un arreglo.

  ```js
  {
    $filter: {
      input: "$productos",
      as: "producto",
      cond: { $gt: [ "$$producto.precio", 100 ] }
    }
  }
  ```

* `$map`: Aplica transformación a cada elemento del arreglo.

  ```js
  {
    $map: {
      input: "$productos",
      as: "producto",
      in: {
        nombre: "$$producto.nombre",
        precioConIVA: { $multiply: [ "$$producto.precio", 1.12 ] }
      }
    }
  }
  ```

* `$reduce`: Aplica acumulación sobre los elementos del arreglo.

  ```js
  {
    $reduce: {
      input: "$ventas",
      initialValue: 0,
      in: { $add: [ "$$value", "$$this" ] }
    }
  }
  ```

### 6. Operadores de Texto

* `$concat`: Une varias cadenas.

  ```js
  { $concat: [ "$nombre", " ", "$apellido" ] }
  ```

* `$toUpper`, `$toLower`: Convierte a mayúsculas o minúsculas.

  ```js
  { $toUpper: "$nombre" }
  ```

* `$substr`, `$substrBytes`, `$substrCP`: Extrae subcadenas.

  ```js
  { $substr: [ "$codigo", 0, 3 ] } // Obsoleto en versiones nuevas
  ```

* `$split`: Divide una cadena en partes.

  ```js
  { $split: [ "$tags", "," ] }
  ```

* `$trim`, `$ltrim`, `$rtrim`: Elimina caracteres especificados (por defecto espacios).

  ```js
  { $trim: { input: "$nombre", chars: " " } }
  ```

---

## 🧪 Estructura General de una Pipeline de Agregación

```js
 db.coleccion.aggregate([
   { $match: { campo: valor } }, // Filtra documentos
   { $project: { campo1: 1, campo2: 1 } }, // Selecciona campos
   { $group: { _id: "$campo1", total: { $sum: "$campo2" } } }, // Agrupa y acumula
   { $sort: { total: -1 } }, // Ordena resultados
   { $limit: 10 } // Limita el número de documentos
 ])
```

---

## ✅ Buenas Prácticas

* Coloca `$match` y `$project` al inicio para reducir datos.
* Aprovecha índices existentes para rendimiento.
* Minimiza operadores costosos como `$unwind` en arreglos grandes.
* Usa `.explain()` para analizar rendimiento.
* Evita operadores obsoletos (como `$substr`) y prefiere `$substrBytes` o `$substrCP`.

---

Consulta la documentación oficial para más detalles:
👉 [Referencia de Operadores de Agregación en MongoDB](https://www.mongodb.com/docs/manual/reference/operator/aggregation/)

# MongoDB Aggregation 101 ejemplos

Guía básica para entender y utilizar los operadores de agregación más importantes de MongoDB.

---

## 🧰 Categorías Principales de Operadores de Agregación

### 1. Operadores Aritméticos

* `$add`: Suma valores numéricos o fechas.

  ```js
  { $add: [ "$precio", "$impuesto" ] }
  ```

* `$subtract`: Resta un valor de otro.

  ```js
  { $subtract: [ "$total", "$descuento" ] }
  ```

* `$multiply`: Multiplica valores.

  ```js
  { $multiply: [ "$cantidad", "$precioUnitario" ] }
  ```

* `$divide`: Divide un valor por otro.

  ```js
  { $divide: [ "$total", "$cantidad" ] }
  ```

* `$abs`: Valor absoluto.

  ```js
  { $abs: "$diferencia" }
  ```

* `$ceil`, `$floor`, `$round`, `$trunc`: Redondeo hacia arriba, abajo, estándar y truncamiento.

  ```js
  { $ceil: "$valor" }
  ```

### 2. Operadores de Acumulación

Usados en `$group` para cálculos sobre grupos de documentos.

* `$sum`: Suma de valores.

  ```js
  { $sum: "$ventas" }
  ```

* `$avg`: Promedio.

  ```js
  { $avg: "$calificacion" }
  ```

* `$min`, `$max`: Valor mínimo o máximo.

  ```js
  { $min: "$precio" }
  ```

* `$push`: Agrega valores a un arreglo.

  ```js
  { $push: "$producto" }
  ```

* `$addToSet`: Agrega valores únicos a un arreglo.

  ```js
  { $addToSet: "$categoria" }
  ```

### 3. Operadores Lógicos y de Comparación

* `$eq`, `$ne`: Igualdad y desigualdad.

  ```js
  { $eq: [ "$estado", "activo" ] }
  ```

* `$gt`, `$gte`, `$lt`, `$lte`: Comparaciones.

  ```js
  { $gt: [ "$edad", 18 ] }
  ```

* `$and`, `$or`, `$not`: Operadores lógicos.

  ```js
  { $and: [ { $gt: [ "$edad", 18 ] }, { $lt: [ "$edad", 65 ] } ] }
  ```

* `$cond`: Condicional tipo if-then-else.

  ```js
  {
    $cond: {
      if: { $gte: [ "$puntuacion", 90 ] },
      then: "Aprobado",
      else: "Reprobado"
    }
  }
  ```

### 4. Operadores de Fechas

* `$dateToString`: Convierte una fecha a string con formato.

  ```js
  { $dateToString: { format: "%Y-%m-%d", date: "$fecha" } }
  ```

* `$year`, `$month`, `$dayOfMonth`, `$hour`, `$minute`, `$second`: Componentes de fecha.

  ```js
  { $year: "$fecha" }
  ```

* `$dateAdd`, `$dateSubtract`: Suma o resta tiempo.

  ```js
  {
    $dateAdd: {
      startDate: "$fecha",
      unit: "day",
      amount: 7
    }
  }
  ```

* `$dateDiff`: Diferencia entre fechas.

  ```js
  {
    $dateDiff: {
      startDate: "$inicio",
      endDate: "$fin",
      unit: "day"
    }
  }
  ```

### 5. Operadores de Arreglos

* `$arrayElemAt`: Accede por índice.

  ```js
  { $arrayElemAt: [ "$productos", 0 ] }
  ```

* `$size`: Tamaño del arreglo.

  ```js
  { $size: "$comentarios" }
  ```

* `$filter`: Filtra según condición.

  ```js
  {
    $filter: {
      input: "$productos",
      as: "producto",
      cond: { $gt: [ "$$producto.precio", 100 ] }
    }
  }
  ```

* `$map`: Aplica una función a cada elemento.

  ```js
  {
    $map: {
      input: "$productos",
      as: "producto",
      in: {
        nombre: "$$producto.nombre",
        precioConIVA: { $multiply: [ "$$producto.precio", 1.12 ] }
      }
    }
  }
  ```

* `$reduce`: Función acumulativa.

  ```js
  {
    $reduce: {
      input: "$ventas",
      initialValue: 0,
      in: { $add: [ "$$value", "$$this" ] }
    }
  }
  ```

### 6. Operadores de Texto

* `$concat`: Concatena cadenas.

  ```js
  { $concat: [ "$nombre", " ", "$apellido" ] }
  ```

* `$toUpper`, `$toLower`: Mayúsculas y minúsculas.

  ```js
  { $toUpper: "$nombre" }
  ```

* `$substr`, `$substrBytes`, `$substrCP`: Subcadenas.

  ```js
  { $substr: [ "$codigo", 0, 3 ] }
  ```

* `$split`: Divide una cadena.

  ```js
  { $split: [ "$tags", "," ] }
  ```

* `$trim`, `$ltrim`, `$rtrim`: Elimina espacios o caracteres.

  ```js
  { $trim: { input: "$nombre", chars: " " } }
  ```

---

## 🧪 Estructura General de una Pipeline de Agregación

```js
 db.coleccion.aggregate([
   { $match: { campo: valor } },
   { $project: { campo1: 1, campo2: 1 } },
   { $group: { _id: "$campo1", total: { $sum: "$campo2" } } },
   { $sort: { total: -1 } },
   { $limit: 10 }
 ])
```

---

## ✅ Buenas Prácticas

* Coloca `$match` y `$project` al inicio para reducir datos.
* Aprovecha índices existentes para rendimiento.
* Minimiza operadores costosos como `$unwind` en arreglos grandes.
* Usa `.explain()` para analizar rendimiento.

---

Consulta la documentación oficial para más detalles:
👉 [Referencia de Operadores de Agregación en MongoDB](https://www.mongodb.com/docs/manual/reference/operator/aggregation/)
