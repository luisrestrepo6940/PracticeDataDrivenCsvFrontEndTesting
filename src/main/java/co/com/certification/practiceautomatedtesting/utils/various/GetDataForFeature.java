package co.com.certification.practiceautomatedtesting.utils.various;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static co.com.certification.practiceautomatedtesting.utils.constants.Constants.AT_PATTERN;
import static co.com.certification.practiceautomatedtesting.utils.constants.Constants.REPLACE_PATTERN;
import static java.nio.charset.StandardCharsets.UTF_8;

public class GetDataForFeature {
    public static final String UTILITY_CLASS = "Utility Class";
    protected static String[] dataVector;

    private GetDataForFeature() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static List<String> readCsvFile(File featureFile) {
        List<String> fileData = new ArrayList<>();
        try (BufferedReader buffReader = new BufferedReader(
                new InputStreamReader(new BufferedInputStream(Files.newInputStream(featureFile.toPath())), UTF_8))) {
            String data;
            boolean replace = false;
            boolean externalData = false;
            String filePath;
            while ((data = buffReader.readLine()) != null) {
                if (data.trim().contains(REPLACE_PATTERN)) {
                    fileData.add(data);
                    dataVector = getDataFilePath(data);
                    replace = true;
                    externalData = true;
                }
                if (replace) {
                    filePath = dataVector[2].trim();
                    List<String[]> listData = CsvReader.getCsvData(filePath);
                    String dataTable = createDataTable(listData, Boolean.parseBoolean(dataVector[3].trim()));
                    fileData.add(dataTable);
                    replace = false;
                    continue;
                }
                externalData = writeLine(externalData, data);
                if (!externalData) {
                    fileData.add(data);
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return fileData;
    }

    public static boolean writeLine(boolean externalData, String data) {
        if (externalData && !data.trim().contains("|")) {
            externalData = false;
        }
        return externalData;
    }

    public static String createDataTable(List<String[]> listData, boolean randomize) {
        StringBuilder stringBuilder = new StringBuilder();
        if (randomize) {
            String[] dataArray = listData.get(Utils.getRandomNumberAnInterval(listData.size() - 1) + 1);
            for (String data : dataArray) {
                stringBuilder.append("      | ").append(data);
            }
            return stringBuilder.toString().concat("|");
        } else {
            for (int i = 1; i < listData.size(); i++) {
                String[] dataArray = listData.get(i);
                for (String data : dataArray) {
                    stringBuilder.append("      | ").append(data);
                }
                stringBuilder.append("|");
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }

    public static String[] getDataFilePath(String data) {
        return data.trim().split(AT_PATTERN);
    }

    public static void writeFeatureWithCsvData(String featurePath) {
        File file = new File(featurePath);
        List<String> featureWithData = GetDataForFeature.readCsvFile(file);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), UTF_8))) {
            for (String string : featureWithData) {
                writer.write(string);
                writer.write("\n");
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
