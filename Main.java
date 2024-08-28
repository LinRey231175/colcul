import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine().trim();
            char operator;
            String[] parts;

            // ���������� �������� � ��������� ������ �� �����
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
                throw new Exception("������������ ���� ��������");
            }

            // �������� ������������ ����� � ������
            if (parts.length != 2 || parts[0].length() > 12 || parts[1].length() > 12) {
                throw new Exception("������������ ���������� ��������� ��� ����� ������");
            }

            // ��������, ��� ������ �������� - ������ � ������� ��������
            if (!parts[0].startsWith("\"") || !parts[0].endsWith("\"")) {
                throw new Exception("������ ���������� ������ ���� ������ � ������� ��������");
            }

            // ������� ������� �� ������� �������� � ������������ ������ �������
            parts[0] = parts[0].substring(1, parts[0].length() - 1).trim();
            parts[1] = parts[1].replace("\"", "").trim();

            int number = 0;
            if (operator == '*' || operator == '/') {
                try {
                    // �������� � �������������� ������� �������� � �����
                    number = Integer.parseInt(parts[1]);
                    if (number < 1 || number > 10) throw new Exception("����� ������ ���� �� 1 �� 10 ������������");
                } catch (NumberFormatException e) {
                    throw new Exception("������ �������� ������ ���� ������");
                }
            } else if (operator == '-' || operator == '+') {
                // ��������, ��� ������ �������� �������� ������� � ������� ��������
                if (parts[1].startsWith("\"") && !parts[1].endsWith("\"")) {
                    throw new Exception("������ �������� ������ ���� ������� � ������� ��������");
                }
                if (parts[1].length() > 10) {
                    throw new Exception("������ �� ����� ���� ������� 10 ��������");
                }
            }

            // ��������� �������� � ����������� �� ����� ��������
            String result = switch (operator) {
                case '+' -> parts[0] + parts[1]; // �������� �����
                case '*' -> parts[0].repeat(number); // ���������� ������
                case '-' -> parts[0].replace(parts[1], ""); // �������� ��������� �� ������
                case '/' -> {
                    if (number <= 0) throw new Exception("�������� ������ ���� �������������");
                    // ������� ������ � ����������� ����� ����������
                    yield parts[0].substring(0, Math.min(parts[0].length(), parts[0].length() / number));
                }
                default -> throw new Exception("����������� ��������");
            };

            // ��������� ����� ���������� � ��������� (...) ���� �����
            if (result.length() > 40) {
                result = result.substring(0, 40) + "...";
            }

            // ������� ��������� � ��������
            printInQuotes(result);
        } catch (Exception e) {
            // ������� ��������� �� ������
            System.err.println(e.getMessage());
        }
    }

    // ����� ��� ������ ������ � ��������
    static void printInQuotes(String text) {
        System.out.println("\"" + text + "\"");
    }
}
