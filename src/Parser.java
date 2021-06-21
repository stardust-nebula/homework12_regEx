import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements IParser {

    private Map<String, Document> resultMap = new HashMap<>();

    @Override
    public void parse(String pathToFolder, int countToParse) {

        File folder = new File(pathToFolder);
        int countProcessedFiles = 0;
        int countInvalidFiles = 0;


        if (folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith("txt"));

            if (files.length == 0) {
                System.out.println("Нет подходящих файлов");
                return;
            }

            if (files.length < countToParse || files.length == countToParse) {
                for (File file : files) {
                    readFile(file);
                    countProcessedFiles = countProcessedFiles + 1;
                    System.out.println("------ End of reading the file ------" + '\n');
                }
            } else {
                System.out.println("В директории больше txt файлов");
                return;
            }

            countInvalidFiles = folder.listFiles().length - files.length;
        } else {
            System.out.println("Невалидный путь");
        }

        System.out.println("Обработано документов: " + countProcessedFiles);
        System.out.println("Количество документов невалидного формата: " + countInvalidFiles);
    }

    private void readFile(File file) {
        Pattern docPattern = Pattern.compile("\\d{4}[-][a-zа-я]{3}[-]\\d{4}[-][a-zа-я]{3}[-]\\d[a-zа-я]\\d[a-zа-я]", Pattern.CASE_INSENSITIVE);
        Pattern phonePattern = Pattern.compile("[+][(]\\d{2}[)]\\d{7}");
        Pattern emailPattern = Pattern.compile("^\\S+@\\S+\\.\\S+$", Pattern.CASE_INSENSITIVE);

        String fileNameWithoutExtension = file.getName().replaceFirst("[.]txt", "");

        List docList = new ArrayList<>();
        String phoneNumber = null;
        String emailAddress = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String docOneLine;

            while ((docOneLine = reader.readLine()) != null) {
                Matcher docMatcher = docPattern.matcher(docOneLine);
                Matcher phoneMatcher = phonePattern.matcher(docOneLine);
                Matcher emailMatcher = emailPattern.matcher(docOneLine);

                if (docMatcher.find()) {
                    String line = docOneLine.substring(docMatcher.start(), docMatcher.end());
                    docList.add(line);
                }

                if (phoneMatcher.find()) {
                    phoneNumber = docOneLine.substring(phoneMatcher.start(), phoneMatcher.end());
                }

                if (emailMatcher.find()) {
                    emailAddress = docOneLine.substring(emailMatcher.start(), emailMatcher.end());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        resultMap.put(fileNameWithoutExtension, new Document(docList, phoneNumber, emailAddress));
        System.out.println("Контент коллекции: " + resultMap.entrySet());
    }
}
