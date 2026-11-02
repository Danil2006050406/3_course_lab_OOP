# SirovarWeather

SirovarWeather — навчальний Android-застосунок для перегляду поточної погоди та прогнозу. Проєкт розміщено в `Lab3` репозиторію [3_course_lab_OOP](https://github.com/Danil2006050406/3_course_lab_OOP). Код застосунку написаний Java, інтерфейс побудований на Android Views та XML.

## Можливості

- Поточна температура, відчутна температура, погодні умови, вологість, вітер, опади та межі температури на день.
- Горизонтальний прогноз на наступні 24 години та прогноз на 7 днів.
- Пошук міст із країною та адміністративним регіоном; вибране і попереднє місто зберігаються локально.
- Компактний і розширений Android App Widget із переходом до застосунку.
- Оновлення віджетів фоновим WorkManager приблизно раз на 6 годин за наявності мережі.
- Кеш останнього прогнозу для показу офлайн.
- Вибір °C/°F, км/год/м/с, української/англійської мови та світлої/темної теми.
- Timber для подій застосунку; у Release залишається лише вивід попереджень і помилок без запитів/координат.

## Технології

Java 11 bytecode, Android Gradle Plugin 8.13.2, Gradle 8.13, AndroidX AppCompat, Material Components, RecyclerView, SwipeRefreshLayout, Retrofit, OkHttp, Gson, SharedPreferences, WorkManager, Timber та JUnit 4.

## Open-Meteo API

- Прогноз: `https://api.open-meteo.com/v1/forecast`
- Геокодування: `https://geocoding-api.open-meteo.com/v1/search`
- API-ключ не потрібен. Назва стартового міста Kyiv також проходить через геокодування; координати з відповіді сервісу використовуються для прогнозу.

## Структура

```text
Lab3/
├── app/src/main/java/com/example/sirovarweather/
│   ├── data/api, data/model, data/repository, data/local
│   ├── ui/activity, ui/adapter
│   ├── widget, worker, utils
│   └── SirovarWeatherApplication.java
├── app/src/main/res/             # XML Views, локалізація, теми, іконки, віджети
├── app/src/test/                 # JVM-тести
├── app/src/androidTest/           # Android-тест налаштувань
├── gradle/                       # Gradle Wrapper
├── PROJECT_CHANGELOG.md
└── README.md
```

## Запуск в Android Studio

1. Відкрийте директорію `Lab3` як окремий Gradle-проєкт.
2. Встановіть Android SDK Platform 35 та сумісний JDK 17 для Gradle.
3. Дочекайтеся синхронізації Gradle; перша синхронізація завантажить Gradle та залежності.
4. Запустіть конфігурацію `app` на пристрої або емуляторі з Android 6.0+ і підключенням до мережі.

## Віджет

На головному екрані пристрою затисніть порожнє місце, відкрийте меню **Віджети**, знайдіть **SirovarWeather** і перетягніть компактний або розширений варіант. Натискання відкриває застосунок. Віджет показує останні збережені дані, а WorkManager оновлює їх за наявності мережі.

## Мови та параметри

Основна мова — українська; додаткова — англійська. Рядки зберігаються у стандартних ресурсах `values`, `values-uk` та `values-en`. Мову, одиниці та тему можна змінити на екрані налаштувань; налаштування зберігаються через SharedPreferences.

## Логування

Timber ініціалізується в `SirovarWeatherApplication`. У Debug використовується `DebugTree`; у Release спеціальне дерево записує лише попередження та помилки й відкидає HTTP debug-рядки із запитами.

## Git

Вихідний репозиторій: [Danil2006050406/3_course_lab_OOP](https://github.com/Danil2006050406/3_course_lab_OOP), основна гілка `main`. Android-застосунок міститься лише в `Lab3`; `Lab1` і `Lab2` не змінюються.

## Тестування

JVM-тести перевіряють JSON-модель відповіді, погодні коди та конвертацію одиниць. Android-тест перевіряє збереження налаштувань. Команди для перевірки з кореня `Lab3`:

```text
./gradlew testDebugUnitTest
./gradlew connectedDebugAndroidTest
./gradlew assembleDebug
```

Android-тест потребує емулятор або підключений пристрій.

## Скріншоти

Додайте скріншоти після запуску на пристрої:

![Головний екран](screenshots/main-screen.png)

![Пошук міста та налаштування](screenshots/search-and-settings.png)

![Віджети](screenshots/widgets.png)
