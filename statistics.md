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

```
