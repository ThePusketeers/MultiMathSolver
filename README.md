# MultiMathSolver

![Contributors](https://img.shields.io/github/contributors/ThePusketeers/MultiMathSolver?color=dark-green) ![Issues](https://img.shields.io/github/issues/ThePusketeers/MultiMathSolver) ![License](https://img.shields.io/github/license/ThePusketeers/MultiMathSolver?color=dark-green)

## Table Of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Requirements](#requirements)
* [Installation](#installation)
* [Usage](#usage)
* [License](#license)
* [Authors](#authors)

## About The Project

Наш проект представляет собой мультифункциональный калькулятор для решения типовых задач высшей математики для устройств на платформе Android.

## Built With
- Java
- Android
- MVVM architectural pattern
- Clean architecture
- Retrofit
- RxJava
- Junit 5
- Git

## Requirements
1. Android Gradle Plugin Version - `8.3.2`
2. Gradle Version - `8.4`
3. JDK version - `17`
4. Min SDK Version - `24`
5. Target SDK Version - `34`

## Installation

1. Получить API ключ от [WolframAlpha](https://products.wolframalpha.com/api/).

2. Клонировать репозиторий в Android Studio.

```sh
git clone https://github.com/ThePusketeers/MultiMathSolver.git
```

3. Добавить строчку с API ключом в файл `local.properties`.
   (В примере apiKey неверный)

```java
apiKey="9Z7ZKJ-ERR0RK1NG7"
```
4. Нажать на кнопку Run 'app'. Всё готово!

## Usage
1. Работа с булевыми операциями (нахождение минимальной ДНФ, сокращённой ДНФ, тупиковой ДНФ; СКНФ/СДНФ; таблица истинности; полином Жегалкина).
   ![alt text](https://i.yapx.ru/XgwD1.jpg)
2. Операции с матрицами (сложение, вычитание, умножение на число и на матрицу, нахождение обратной, возведение в степень, нахождение ранга, вычисление определителя).
   ![alt text](https://i.yapx.ru/XgwHu.jpg)
3. Решение СЛАУ.
   ![alt text](https://i.yapx.ru/XgwHv.jpg)
4. Операции с матрицами (сложение, вычитание, умножение на число и на матрицу, нахождение обратной, возведение в степень, нахождение ранга, вычисление определителя).
   ![alt text](https://i.yapx.ru/XgwHw.jpg)

## License

Распространяется по лицензии GPL-3.0.  [Нажмите](https://github.com/ThePusketeers/MultiMathSolver/blob/master/LICENSE.txt) для большей информации.

## Authors

* **Мартиросян Ишхан** - *Telegram: [@I_MART0](https://t.me/I_MART0)* - [Мартиросян Ишхан](https://github.com/IMART0) - *imartirosyan@vk.com*
  **mobile dev** (разработчик логики для работы с пределами, а также интерфейса для СЛАУ)

* **Воропаев Фёдор** - *Telegram: [@fedu_usha](https://t.me/fedu_usha)* - [Воропаев Фёдор](https://github.com/feduuusha) - *voropaevfedor2005@mail.ru*
  **mobile dev** (разработчик логики для работы с булевыми операциями, а также интерфейса для пределов)
* **Дивиров Арсен** - *Telegram: [@kiz_zyaka](https://t.me/kiz_zyaka)* - [Дивиров Арсен](https://github.com/Archemalit) - *ArsenDivirov@yandex.ru*
  **mobile dev** (разработчик логики для работы с матрицами, а также интерфейса для булевых операций)

* **Романов Роберт** - *Telegram: [@doflmgo](https://t.me/doflmgo)* - [Романов Роберт](https://github.com/robl0xx) - *robertromanov2018@mail.ru*
  **mobile dev** (разработчик логики для работы с СЛАУ, а также интерфейса для матриц)

*  **Ремизов Павел** - *Telegram: [@B1BLETUM](https://t.me/B1BLETUM)* - [Ремизов Павел](https://github.com/BIBLETUM) - *pasha.remizov@mail.ru*
   **Project manager** (наставник проекта)
