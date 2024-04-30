#### Practice 
```r
  plot(1:10) //creating a plot
```
el objeto mas básico de R es un vector
asignación
```r
x <- "a"
// vectores
x <- c(0.1,2,3,4.5)
```

##### alguas funciones útiles
```r
a <- c(8.9, 12.4,8.6,11.3,9.2,8.8,35.1,6.2,7.0,7.1)
mean(a)
sd(a)
summary(a)
max(a)
hist(a)


a1 <- a[-7]## quitamos el elemento de la posicion 7
a1


hist(a,main="Hola", ylab="Frecuencia", xlab="aaaaaaaaaaaaaaaaaaaaaa", col="lightblue")

```
### libreria fdth
```r
install.packages("fdth")
library(fdth)
```

### code of today
```r
a <- c(8.9, 12.4,8.6,11.3,9.2,8.8,35.1,6.2,7.0,7.1)
mean(a)
sd(a)
summary(a)
max(a)
## hist(a,prob=TRUE,main="Hola", ylab="Frecuencia", xlab="aaaaaaaaaaaaaaaaaaaaaa", col="lightblue")

a1 <- a[-7]## quitamos el elemento de la posicion 7
a1


lines(density(a), lwd = 5, col = 'red')

set.seed(1)

# Datos normales
x <- rnorm(n = 5000, mean = 110, sd = 5)

# Datos exponenciales
y <- rexp(n = 3000, rate = 1)

par(mfcol = c(1, 2))

histDenNorm(x, prob = TRUE, main = "Histograma de X")
histDenNorm(y, prob = TRUE, main = "Histograma de Y")

par(mfcol = c(1, 1))
grid(nx = NA, ny = NULL, lty = 2, col = "gray", lwd = 1)
stem(a, scale=1)


class(data)
## [1] "numeric"

edades/100 ## divide todos mis datos para 100





```

```r
## tidyverse
## boxplot en R
boxplot(data1, horizontal = TRUE)
```
#### bloxplot en R
```r
data1 <- c(7.5, 8.2, 6.9, 7.8, 9.0, 6.5, 8.7, 7.2, 6.8, 8.5,
          7.3, 8.1, 6.7, 7.9, 8.8, 6.4, 7.6, 8.3, 6.6, 7.4,
          8.0, 6.3, 7.1, 8.6, 6.2, 7.7, 8.4, 6.1, 7.0, 8.9,
          6.0, 7.5, 8.2, 6.9, 7.8, 9.0, 6.5, 8.7, 7.2, 6.8,
          8.5, 7.3, 8.1, 6.7, 7.9, 8.8, 6.4, 7.6, 8.3, 6.6,4.5, 5.2, 3.8, 6.0, 7.2, 5.0, 4.2, 5.5, 4.8, 6.5,
          4.0, 5.8, 3.6, 6.8, 8.0, 5.4, 4.4, 6.2, 4.6, .70,
          4.3, 5.6, 3.9, 6.1, 7.3, 5.1, 4.1, 5.7, 3.7, 6.9,
          4.7, 6.4, 4.9, 6.3, 7.5, 5.3, 4.5, 5.9, 3.5, 6.7,
          5.2, 3.8, 6.0, 7.2, 5.0, 4.2, 5.5, 4.8, 6.5, 4.0,2.50, 2.80, 2.10, 3.20, 3.80, 2.60, 2.30, 3.00, 2.70, 3.50,
          2.40, 2.70, 2.00, 3.10, 3.70, 2.50, 2.20, 2.90, 2.60, 3.40,
          2.30, 2.60, 1.90, 3.00, 3.60,20) 

stem(data1)
length(data1)
class(data1)

edades
set.seed(9999)
x <- data.frame(data1,sample(size = 126, x=150))
boxplot(data1, horizontal = FALSE)
hist(data1)
```
