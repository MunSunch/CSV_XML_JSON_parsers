package org.example.task1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "./src/main/resources/data.csv";
        Class<? extends Employee> type = Employee.class;
        String newFileName = "./src/main/resources/data.json";
        try {
            List<Employee> employees = parseCSV(columnMapping, fileName, type);
            String json = listToJson(employees);
            writeString(json, newFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static <T> List<T> parseCSV(String[] columnMapping,
                                        String fileName,
                                        Class<? extends T> type) throws IOException
    {
        CSVReader reader = new CSVReader(new FileReader(fileName));
        ColumnPositionMappingStrategy<T> strategy =
                new ColumnPositionMappingStrategy<>();
        strategy.setColumnMapping(columnMapping);
        strategy.setType(type);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withMappingStrategy(strategy)
                .build();
        List<T> objects = csvToBean.parse();
        reader.close();
        return objects;
    }


    public static <T> String listToJson(List<T> objects)
    {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<T>>() {}.getType();
        return gson.toJson(objects, listType);
    }

    public static void writeString(String string, String path) throws IOException{
        File newFile = new File(path);
        if(!newFile.exists()) {
            newFile.createNewFile();
        }
        FileWriter writer = new FileWriter(newFile);
        writer.write(string);
        writer.flush();
        writer.close();
    }
}
