package org.example.task3;

import com.google.gson.Gson;
import org.example.task1.Employee;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String json = readString("./src/main/resources/data.json");
            List<Employee> employees = jsonToList(json, Employee.class);
            employees.stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readString(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder result = new StringBuilder();
        String temp;
        while((temp = reader.readLine()) != null) {
            result.append(temp);
        }
        reader.close();
        return result.toString();
    }

    private static <T> List<T> jsonToList(String json, Class<? extends T> type) throws ParseException {
        JSONArray array = (JSONArray) new JSONParser().parse(json);
        Gson gson = new Gson();
        List<T> result = new ArrayList<>(array.size());
        for (var object: array) {
            result.add(gson.fromJson(object.toString(), type));
        }
        return result;
    }
}