import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;

        while (true) {
            System.out.println("Введите путь к файлу: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);

            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            int googlebot = 0;
            int yandexbot = 0;
            int total = 0;

            if (isDirectory) {
                System.out.println("Данный путь ведет к папке. Повторите ввод.");
                continue;
            }

            if (fileExists) {
                count += 1;
                System.out.println("Путь указан верно.");
                System.out.println("Это файл номер " + count);

                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);

                    int lineNumber = 0;

                    String line;
                    while ((line = reader.readLine())!= null) {
                        lineNumber++;
                        int length = line.length();

                        if (length > 1024) {
                            throw new RuntimeException("Строка длиннее 1024 символов");
                        }

                        int firstBracketIndex = line.indexOf("(");
                        int lastBracketIndex = line.indexOf(")");

                        if (firstBracketIndex == -1 || lastBracketIndex == -1 || firstBracketIndex > lastBracketIndex) {
                            continue;
                        }

                        String firstBrackets = line.substring(firstBracketIndex + 1, lastBracketIndex);
                        String[] parts = firstBrackets.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();
                            if (fragment.indexOf("/") != -1) {
                                String program = fragment.substring(0, fragment.indexOf("/"));

                                if (program.equals("Googlebot")) {
                                    googlebot++;
                                } else if (program.equals("YandexBot")) {
                                    yandexbot++;
                                }
                                total++;
                            }
                        }
                    }

                    System.out.println("Общее количество строк в файле: " + lineNumber);

                    double googlebotRatio = (double) googlebot / total * 100;
                    double yandexbotRatio = (double) yandexbot / total * 100;

                    System.out.println("Доля запросов Googlebot: " + googlebotRatio + "%");
                    System.out.println("Доля запросов YandexBot: " + yandexbotRatio + "%");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                System.out.println("Данный путь некорректен. Повторите ввод.");
            }
        }
    }
}






