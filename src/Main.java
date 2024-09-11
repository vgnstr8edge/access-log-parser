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

                Statistics statistics = new Statistics();

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

                        LogEntry entry = new LogEntry(line);
                        statistics.addEntry(entry);


                        if (entry.getUserAgent().getBrowser().equals("Googlebot")) {
                            googlebot++;
                        } else if (entry.getUserAgent().getBrowser().equals("YandexBot")) {
                            yandexbot++;
                        }
                        total++;
                    }

                    System.out.println("Общее количество строк в файле: " + lineNumber);

                    double googlebotRatio = (double) googlebot / total * 100;
                    double yandexbotRatio = (double) yandexbot / total* 100;

                    System.out.println("Доля запросов Googlebot: " + googlebotRatio + "%");
                    System.out.println("Доля запросов YandexBot: " + yandexbotRatio + "%");


                    System.out.println("Общий объем трафика: " + statistics.totalTraffic);
                    System.out.println("Скорость трафика: " + statistics.getTrafficRate() + " байт/час");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                System.out.println("Данный путь некорректен. Повторите ввод.");
            }
        }

    }

}