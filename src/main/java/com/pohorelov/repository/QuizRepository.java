package com.pohorelov.repository;

import com.pohorelov.model.Answer;
import com.pohorelov.model.Question;
import com.pohorelov.model.Quiz;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * In-memory сховище квізів.
 * У реальному проєкті — JPA repository або будь-який інший data source.
 */
public class QuizRepository {

    private final Map<String, Quiz> quizzes;

    public QuizRepository() {
        List<Quiz> all = List.of(
                buildJavaCorequiz(),
                buildCollectionsQuiz(),
                buildHttpServletQuiz()
        );
        quizzes = all.stream().collect(Collectors.toMap(Quiz::getId, Function.identity()));
    }

    public List<Quiz> findAll() {
        return List.copyOf(quizzes.values());
    }

    public Optional<Quiz> findById(String id) {
        return Optional.ofNullable(quizzes.get(id));
    }

    // =========================================================================
    // Quiz 1 — Java Core
    // =========================================================================

    private static Quiz buildJavaCorequiz() {
        return new Quiz(
                "java-core",
                "Java Core",
                "Базові концепції мови: типи, рядки, оператори",
                90,
                List.of(
                        new Question(
                                "Що виведе: System.out.println(1 + 2 + \"3\");",
                                List.of(
                                        new Answer("A", "\"123\""),
                                        new Answer("B", "\"33\""),
                                        new Answer("C", "6"),
                                        new Answer("D", "Compile error")
                                ),
                                "B",
                                "Оператор + лівоасоціативний: спочатку 1+2=3 (int), потім 3+\"3\"=\"33\" (конкатенація)."
                        ),
                        new Question(
                                "Яке ключове слово робить поле незмінним після ініціалізації?",
                                List.of(
                                        new Answer("A", "static"),
                                        new Answer("B", "const"),
                                        new Answer("C", "final"),
                                        new Answer("D", "immutable")
                                ),
                                "C",
                                "final — єдине коректне слово. const є в C++, але не в Java. immutable — не ключове слово."
                        ),
                        new Question(
                                "Що виведе: String s = null; s += \"!\"; System.out.println(s);",
                                List.of(
                                        new Answer("A", "NullPointerException"),
                                        new Answer("B", "\"null!\""),
                                        new Answer("C", "\"!\""),
                                        new Answer("D", "\"\"")
                                ),
                                "B",
                                "s += \"!\" еквівалентно s = String.valueOf(s) + \"!\". String.valueOf(null) = \"null\"."
                        ),
                        new Question(
                                "Яка різниця між == та equals() для рядків?",
                                List.of(
                                        new Answer("A", "Немає різниці"),
                                        new Answer("B", "== порівнює посилання, equals() — вміст"),
                                        new Answer("C", "equals() порівнює посилання, == — вміст"),
                                        new Answer("D", "== завжди правильний і швидший")
                                ),
                                "B",
                                "== порівнює адреси в пам'яті. \"hello\" == \"hello\" може бути true через string pool, але new String(\"hello\") == new String(\"hello\") — завжди false."
                        ),
                        new Question(
                                "Що таке autoboxing?",
                                List.of(
                                        new Answer("A", "Конвертація між double і float"),
                                        new Answer("B", "Автоматична конвертація між primitive і wrapper-класом"),
                                        new Answer("C", "Паттерн проектування для immutable-об'єктів"),
                                        new Answer("D", "Спосіб серіалізації об'єктів")
                                ),
                                "B",
                                "int i = 5; Integer obj = i; — це autoboxing. Зворотній процес (Integer → int) називається unboxing."
                        )
                )
        );
    }

    // =========================================================================
    // Quiz 2 — Java Collections
    // =========================================================================

    private static Quiz buildCollectionsQuiz() {
        return new Quiz(
                "collections",
                "Java Collections",
                "ArrayList, HashMap, Set — вибір правильної структури даних",
                90,
                List.of(
                        new Question(
                                "Яка колекція гарантує унікальність елементів і зберігає порядок вставки?",
                                List.of(
                                        new Answer("A", "HashSet — унікальність, порядок не гарантований"),
                                        new Answer("B", "TreeSet — унікальність, сортований порядок"),
                                        new Answer("C", "LinkedHashSet — унікальність + порядок вставки"),
                                        new Answer("D", "ArrayList — порядок вставки, але без унікальності")
                                ),
                                "C",
                                "LinkedHashSet внутрішньо використовує LinkedList для збереження порядку вставки і HashMap для перевірки унікальності."
                        ),
                        new Question(
                                "Яка складність операції get(i) для ArrayList та LinkedList?",
                                List.of(
                                        new Answer("A", "Обидві O(1)"),
                                        new Answer("B", "ArrayList O(1), LinkedList O(n)"),
                                        new Answer("C", "ArrayList O(n), LinkedList O(1)"),
                                        new Answer("D", "Обидві O(n)")
                                ),
                                "B",
                                "ArrayList — масив під капотом, get(i) = arr[i], O(1). LinkedList — двозв'язний список, треба пройти до i-го вузла, O(n)."
                        ),
                        new Question(
                                "Що станеться при додаванні null-ключа у HashMap?",
                                List.of(
                                        new Answer("A", "NullPointerException негайно"),
                                        new Answer("B", "null-ключ зберігається у bucket 0"),
                                        new Answer("C", "null-ключ ігнорується"),
                                        new Answer("D", "Compile error")
                                ),
                                "B",
                                "HashMap явно обробляє null-ключ: hash(null) = 0, тому він завжди опиняється у bucket 0. Але TreeMap кине NullPointerException."
                        ),
                        new Question(
                                "Яка колекція є потоко-безпечною з коробки?",
                                List.of(
                                        new Answer("A", "ArrayList"),
                                        new Answer("B", "HashMap"),
                                        new Answer("C", "ConcurrentHashMap"),
                                        new Answer("D", "LinkedList")
                                ),
                                "C",
                                "ConcurrentHashMap використовує lock striping — блокується не вся мапа, а лише окремий bucket. HashMap, ArrayList, LinkedList — не потоко-безпечні."
                        ),
                        new Question(
                                "Що трапиться при Iterator.remove() vs list.remove() під час for-each?",
                                List.of(
                                        new Answer("A", "Обидва кидають ConcurrentModificationException"),
                                        new Answer("B", "Iterator.remove() — безпечно; list.remove() — ConcurrentModificationException"),
                                        new Answer("C", "list.remove() — безпечно; Iterator.remove() — ConcurrentModificationException"),
                                        new Answer("D", "Обидва безпечні")
                                ),
                                "B",
                                "for-each використовує Iterator. list.remove() змінює modCount, ітератор це помічає і кидає виняток. Iterator.remove() синхронізує modCount."
                        )
                )
        );
    }

    // =========================================================================
    // Quiz 3 — HTTP & Servlet API
    // =========================================================================

    private static Quiz buildHttpServletQuiz() {
        return new Quiz(
                "http-servlet",
                "HTTP & Servlet API",
                "Протокол, сесії, редіректи — те, що ми щойно вивчили",
                90,
                List.of(
                        new Question(
                                "Що таке ідемпотентний HTTP-метод?",
                                List.of(
                                        new Answer("A", "Метод без тіла запиту"),
                                        new Answer("B", "Метод, результат якого однаковий при повторних викликах"),
                                        new Answer("C", "Метод, що завжди кешується"),
                                        new Answer("D", "Метод тільки для читання даних")
                                ),
                                "B",
                                "GET, PUT, DELETE — ідемпотентні. POST — ні (кожен виклик створює новий ресурс). Ідемпотентність ≠ безпечність."
                        ),
                        new Question(
                                "Яка різниця між sendRedirect() та RequestDispatcher.forward()?",
                                List.of(
                                        new Answer("A", "forward() змінює URL у браузері, sendRedirect() — ні"),
                                        new Answer("B", "sendRedirect() — новий HTTP-запит браузера, forward() — перенаправлення на сервері"),
                                        new Answer("C", "Немає різниці, обидва однакові"),
                                        new Answer("D", "forward() може перенаправляти на зовнішні сервери")
                                ),
                                "B",
                                "sendRedirect → браузер отримує 302, робить новий GET-запит, URL змінюється. forward → сервер передає запит іншому сервлету/JSP, браузер не знає, URL не змінюється."
                        ),
                        new Question(
                                "Що таке PRG (Post/Redirect/Get) і навіщо він потрібен?",
                                List.of(
                                        new Answer("A", "Патерн для кешування GET-запитів"),
                                        new Answer("B", "Після POST-запиту — redirect, щоб F5 не повторював відправку форми"),
                                        new Answer("C", "Спосіб передачі даних між сесіями"),
                                        new Answer("D", "Метод аутентифікації")
                                ),
                                "B",
                                "Без PRG: F5 на сторінці після форми = повторний POST = дублікат замовлення/запису. З PRG: після POST відповідь 302, браузер робить GET, F5 повторює безпечний GET."
                        ),
                        new Question(
                                "Де за замовчуванням зберігається JSESSIONID?",
                                List.of(
                                        new Answer("A", "localStorage браузера"),
                                        new Answer("B", "Cookie"),
                                        new Answer("C", "Тільки в URL (matrix parameter)"),
                                        new Answer("D", "Заголовок Authorization")
                                ),
                                "B",
                                "За замовчуванням — cookie Set-Cookie: JSESSIONID=abc123. Якщо cookies вимкнені, контейнер може вставляти JSESSIONID у URL (URL rewriting), але це рідко."
                        ),
                        new Question(
                                "Скільки разів викликається Servlet.init() протягом роботи застосунку?",
                                List.of(
                                        new Answer("A", "При кожному HTTP-запиті"),
                                        new Answer("B", "Один раз — при першому запиті або при старті (load-on-startup)"),
                                        new Answer("C", "Один раз на кожну нову HttpSession"),
                                        new Answer("D", "Двічі: при старті і при першому запиті")
                                ),
                                "B",
                                "Servlet — singleton в контейнері. init() — один раз. Якщо load-on-startup >= 0 в web.xml, ініціалізується при старті застосунку, а не при першому запиті."
                        )
                )
        );
    }
}
