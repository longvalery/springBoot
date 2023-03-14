1. Взят проект archetype № 274: remote -> com.blazebit:blaze-persistence-archetype-spring-data-sample (-)
В созданном проекте оказалась модель "Человек - коты", её и использовал для изучения.
Проект использует h2. 
Модификация (все изменения отмечал тегами "// RVA" ... "// /RVA" ):
1.1. Добавлен второй тест sampleTestRVA()
1.2. Изменен способ задания количества человек и котов (AbstractSampleTest) через параметры "test.person.count" и "test.cat.count" в application.properties и случайным заполнением.
     Создан класс LoadProperies
1.3. Создал командный файл для запуска тестов "runTest.bat" с фильтрацией протокола и оценкой результата выполнения.

2. Добавлен код для вывода получившихся связей Человек <-> Коты. 

3. Добавлены зависимости для создания HTML отчёта по тестированию и создан командный файл createReport.bat для тестирования с отчётом. 

4. Добавлен метод sampleTestRVACatsWithOwnerByQuery. Подробнее описание в description к соответствующему commit
Но повторю сюда:
4.1. Добавлен третий метод для тестирования Query with Join таблиц.
4.2. Изменен CatSimpleViewRepository создан CatWithPersonView для этого метода.
4.3. Изменен AbstractSampleTest (начальное заполнение) для случайного заполнения котов без хозяина.

5. Добавлен метод sampleTestRVACatsWithMotherAndFatherByQuery. Подробнее описание в description к соответствующему commit.
Всё аналогично п.4


