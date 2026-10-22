# MilitaryPlanesXML — варіант №8

Навчальний Java 17 проєкт для роботи з XML про військові літаки.

## Можливості
- XML-дані та XSD-схема з простими/комплексними типами, переліками, межами, обов'язковими атрибутами та `xs:ID`.
- Валідація XML за XSD.
- Три способи читання: SAX, DOM і StAX.
- Спільна модель `Aircraft`, колекція об'єктів та сортування через `Comparator`.
- XSLT-перетворення з групуванням за країною (`Origin`).
- Unit-тести JUnit 5.
- Логування через `java.util.logging`.

## Вимоги
- JDK 17+
- Maven 3.8+

## Запуск
З кореня проєкту:

```bash
mvn test
mvn package
java -jar target/military-planes-xml-1.0.0.jar
```

Для запуску без налаштування executable jar:
```bash
mvn test
mvn exec:java -Dexec.mainClass="ua.edu.lab.planes.Main"
```
Якщо `exec-maven-plugin` не встановлений, можна запустити `Main` з IDE.

## Структура
- `src/main/java/ua/edu/lab/planes/model` — модель даних
- `src/main/java/ua/edu/lab/planes/parser` — SAX, DOM, StAX
- `src/main/java/ua/edu/lab/planes/service` — валідація, XSLT, сортування
- `src/main/resources` — XML, XSD, XSL
- `src/test/java` — unit-тести

## Примітка щодо XSD
XSD перевіряє структуру, типи, обмеження чисел, допустимі значення та унікальність ID. Залежність «розвідник не має боєкомплекту, інші типи мають» додатково перевіряється в Java, оскільки така умовна залежність незручно виражається в XSD 1.0.
