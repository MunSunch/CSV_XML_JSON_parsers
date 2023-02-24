package org.example.task1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
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
        try {
            List<Employee> employees = parseCSV(columnMapping, fileName, type);
            String json = listToJson(employees);
            System.out.println(json);
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
        List<T> employees = csvToBean.parse();
        reader.close();
        return employees;
    }


    private static <T> String listToJson(List<T> objects)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<T>>() {}.getType();
        return gson.toJson(objects, listType);
    }
}
