import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine().trim();
            char operator;
            String[] parts;

            // Определяем операцию и разбиваем строку на части
            if (userInput.contains(" + ")) {
                parts = userInput.split(" \\+ ");
                operator = '+';
            } else if (userInput.contains(" - ")) {
                parts = userInput.split(" - ");
                operator = '-';
            } else if (userInput.contains(" * ")) {
                parts = userInput.split(" \\* ");
                operator = '*';
            } else if (userInput.contains(" / ")) {
                parts = userInput.split(" / ");
                operator = '/';
            } else {
                throw new RuntimeException("");
            }

            // Проверка корректности числа и строки
            if (parts.length != 2 || parts[0].length() > 12 || parts[1].length() > 12) {
                throw new Exception("Некорректное количество операндов или длина строки");
            }

            // Проверка, что первый аргумент - строка в двойных кавычках
            if (!parts[0].startsWith("\"") || !parts[0].endsWith("\"")) {
                throw new Exception("Первым аргументом должно быть строка в двойных кавычках");
            }

            // Удаляем кавычки из первого операнда и обрабатываем второй операнд
            parts[0] = parts[0].substring(1, parts[0].length() - 1).trim();
            parts[1] = parts[1].replace("\"", "").trim();

            int number = 0;
            if (operator == '*' || operator == '/') {
                try {
                    // Проверка и преобразование второго операнда в число
                    number = Integer.parseInt(parts[1]);
                    if (number < 1 || number > 10) throw new Exception("Число должно быть от 1 до 10 включительно");
                } catch (NumberFormatException e) {
                    throw new Exception("Второй аргумент должен быть числом");
                }
            } else if (operator == '-' || operator == '+') {
                // Проверка, что второй аргумент является строкой в двойных кавычках
                if (parts[1].startsWith("\"") && !parts[1].endsWith("\"")) {
                    throw new Exception("Второй аргумент должен быть строкой в двойных кавычках");
                }
                if (parts[1].length() > 10) {
                    throw new Exception("Строка не может быть длиннее 10 символов");
                }
            }

            // Выполняем операцию в зависимости от знака действия
            String result = switch (operator) {
                case '+' -> parts[0] + parts[1]; // Сложение строк
                case '*' -> parts[0].repeat(number); // Повторение строки
                case '-' -> parts[0].replace(parts[1], ""); // Удаление подстроки из строки
                case '/' -> {
                    if (number <= 0) throw new Exception("Делитель должен быть положительным");
                    // Деление строки и ограничение длины результата
                    yield parts[0].substring(0, Math.min(parts[0].length(), parts[0].length() / number));
                }
                default -> throw new Exception("Неизвестный оператор");
            };

            // Проверяем длину результата и добавляем (...) если нужно
            if (result.length() > 40) {
                result = result.substring(0, 40) + "...";
            }

            // Выводим результат в кавычках
            printInQuotes(result);
        } catch (Exception e) {
            // Выводим сообщение об ошибке
            System.err.println(e.getMessage());
        }
    }

    // Метод для вывода строки в кавычках
    static void printInQuotes(String text) {
        System.out.println("\"" + text + "\"");
    }
}
