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
                    int maxLength = 0;
                    int minLength = Integer.MAX_VALUE;

                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        int length = line.length();

                        if (length > maxLength) {
                            maxLength = length;
                        }

                        if (length < minLength) {
                            minLength = length;
                        }

                        if (length > 1024) {
                            throw new RuntimeException("Строка длиннее 1024 символов");
                        }
                    }

                    System.out.println("Общее количество строк в файле: " + lineNumber);
                    System.out.println("Длина самой длинной строки в файле: " + maxLength);
                    System.out.println("Длина самой короткой строки в файле: " + minLength);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                System.out.println("Данный путь некорректен. Повторите ввод.");
            }
        }

    }

}






