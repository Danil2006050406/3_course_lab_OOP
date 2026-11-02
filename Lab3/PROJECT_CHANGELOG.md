# PROJECT CHANGELOG

## 2026-10-30

### Initial project setup

- Изучен репозиторий `Danil2006050406/3_course_lab_OOP`: локальная рабочая папка изначально содержала пустой Git-каталог, без remote и истории.
- Подключён существующий remote `origin`, получена ветка `main` и восстановлена существующая история репозитория.
- Проверено: рабочее дерево чистое; `Lab1` и `Lab2` существуют в удалённой истории и не изменялись.
- В `Lab3` на начало работы находился только `.gitkeep`; существующего changelog и Android-проекта не было.
- Найдена Java 25. Команды Gradle и Android SDK/ADB в PATH и переменных среды не обнаружены.

Проверка:
- Проверены текущая ветка, remote, статус и последние коммиты.
- Проверено содержимое `Lab3` и наличие Java/Gradle/ADB.
- Результат: `main` отслеживает `origin/main`, локальных изменений перед разработкой нет. Сборка пока не запускалась: Gradle и Android SDK не обнаружены.

### Android app, API, UI, widgets, and documentation

Создано:
- Gradle-проект: `settings.gradle`, `build.gradle`, `gradle.properties`, `app/build.gradle`, `app/proguard-rules.pro`, `app/src/main/AndroidManifest.xml`, `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.properties`, `gradle/wrapper/gradle-wrapper.jar` та `Lab3/.gitignore`.
- Java-класи Application, API-сервісів, моделей міста/погоди, `WeatherRepository`, `PreferencesManager`, екранів погоди/пошуку/налаштувань, RecyclerView-адаптерів, AppWidgetProvider, оновлювача віджета, WorkManager-задачі, LoggerConfig, WeatherCodeMapper та UnitConverter.
- XML-екрани та елементи списків, українські й англійські ресурси, теми, locale config, векторні погодні drawable, макети та конфігурації компактного/розширеного віджета.
- JUnit-тести моделей/погодних кодів/перерахунків, Android-тест PreferencesManager, український README та changelog.

Перелік ключових файлів:
- Java: `SirovarWeatherApplication.java`; `data/api/WeatherApiService.java`, `GeocodingApiService.java`; `data/model/City.java`, `WeatherResponse.java`, `GeocodingResponse.java`, `HourlyForecast.java`, `DailyForecast.java`; `data/local/PreferencesManager.java`; `data/repository/WeatherRepository.java`; `ui/activity/MainActivity.java`, `SearchActivity.java`, `SettingsActivity.java`; `ui/adapter/HourlyForecastAdapter.java`, `DailyForecastAdapter.java`, `CitySearchAdapter.java`; `widget/WeatherWidgetProvider.java`, `WeatherWidgetLargeProvider.java`, `WeatherWidgetUpdater.java`; `worker/WeatherUpdateWorker.java`; `utils/LoggerConfig.java`, `WeatherCodeMapper.java`, `UnitConverter.java`, `LocaleUtils.java`.
- Ресурси: `activity_main.xml`, `activity_search.xml`, `activity_settings.xml`, `item_hourly_forecast.xml`, `item_daily_forecast.xml`, `item_city.xml`, `widget_weather_compact.xml`, `widget_weather_large.xml`; `values/strings.xml`, `values-uk/strings.xml`, `values-en/strings.xml`, `values/colors.xml`, `values/themes.xml`; `xml/locales_config.xml`, `weather_widget_compact_info.xml`, `weather_widget_large_info.xml`; 8 погодних vector drawable та фон віджета.
- Перевірки: `UnitConverterTest.java`, `WeatherCodeMapperTest.java`, `WeatherResponseTest.java`, `PreferencesManagerTest.java`.

Реалізовано:
- Реальні запити до Open-Meteo forecast та geocoding API без API-ключа. Початковий Kyiv отримується через геокодування, координати не фіксуються в коді.
- Поточні умови, погодні параметри, 24 години погодинного прогнозу, 7 днів, пошук із країною/регіоном та збереження попереднього міста.
- Запит одиниць безпосередньо в API, збереження параметрів і мови через SharedPreferences, AppCompat locales, світлу/темну тему.
- Кеш останніх даних, стани мережі/помилок, два стандартні Android App Widgets із натисканням для відкриття застосунку та плановим WorkManager оновленням кожні 6 годин.
- Timber DebugTree у Debug і дерево Release, що відкидає debug HTTP-рядки та виводить лише попередження/помилки.

Виправлення:
- Відхиляються відповіді без потрібних hourly/daily масивів; некоректний кеш видаляється, а відсутня ймовірність опадів не підмінюється нульовою.
- При виборі іншого міста чи одиниць старий кеш очищається, щоб не показувати числа у неправильній системі вимірювання.
- Віджет одразу оновлюється після вибору міста/параметрів; застарілі відповіді пошуку та погоди не замінюють актуальні.
- Видалено початковий `Lab3/.gitkeep`, оскільки тепер у каталозі є Android-проєкт.

Файли змінено: лише файли всередині `Lab3`; на кореневому рівні репозиторію та в `Lab1`/`Lab2` файли не змінювалися. Окремо змінено `app/build.gradle`, `app/src/main/AndroidManifest.xml`, `app/src/main/res/layout/activity_main.xml`, кодові файли при уточненні логіки, `Lab3/.gitignore`, `README.md` і цей changelog.

Перевірки:
- XML: 25 файлів розібрано XML-парсером без помилок.
- Ресурси: перевірені посилання Java `R.id`, drawable/layout/XML; набір із 50 ключів збігається у `values`, `values-uk` і `values-en`.
- Open-Meteo: виконано живі запити геокодування Kyiv і 7-денного forecast за координатами з відповіді геокодера. Отримані поточні поля, 168 погодинних записів і 7 денних записів; потрібні поля відповіді присутні.
- Gradle Wrapper JAR перевірено командою `jar tf`; у ньому є `GradleWrapperMain`.
- Запуск `gradlew.bat testDebugUnitTest` не дійшов до конфігурації: стандартна папка `C:\Users\SOTY\.gradle` недоступна в sandbox. Повторний запуск із `GRADLE_USER_HOME` усередині `Lab3` зупинився на `UnknownHostException: services.gradle.org`.
- Android SDK/ADB та емулятор у середовищі не знайдені, тому Gradle build, JUnit та Android UI/widget-перевірки не виконані.
- Результат: статичні перевірки та live API-запити пройшли; компіляція й тести залишаються неперевіреними через відсутність SDK і мережеву недоступність Gradle distribution.

### Windows Gradle path check

- Причина: Android Gradle Plugin зупинявся, бо абсолютний шлях до проєкту містить кириличні символи.
- Змінено: `gradle.properties` — додано `android.overridePathCheck=true`, як пропонує повідомлення AGP для такого шляху.
- Перевірка: значення властивості присутнє у файлі. Повторну Gradle-синхронізацію в цьому середовищі не вдалося запустити через відсутній локальний Gradle distribution та заблокований DNS до `services.gradle.org`.
