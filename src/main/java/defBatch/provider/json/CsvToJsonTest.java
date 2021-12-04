package defBatch.provider.json;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import defBatch.util.Student;
import org.apache.commons.lang3.StringUtils;

public class CsvToJsonTest {


    public List<Map<String, String>> csvToJson(String csvFileName, List<String> fieldNames, String DELIMITER ){
        List<Map<String,String>> list = new ArrayList<>();

        try (InputStream in = new FileInputStream(csvFileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (reader.ready()) {
                String line = reader.readLine();
                if (StringUtils.isBlank(line))continue;
                if (StringUtils.endsWith(line,DELIMITER)){
                    line = StringUtils.removeEnd(line,DELIMITER);
                }
                List<String> x = Arrays.stream(StringUtils.splitPreserveAllTokens(line, DELIMITER)).collect(Collectors.toList());
                Map<String,String> obj = new LinkedHashMap <> ();
                for (int i = 0; i < fieldNames.size(); i++) {
                    obj.put(fieldNames.get(i), x.get(i));
                }

                list.add(obj);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public <T> List<T> csvToObj(String csvFileName, String DELIMITER, Class<T> className) {
        List<T> list = new ArrayList<>();
        List<String> fieldNames = Arrays.stream(className.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> boolValues = Arrays.stream(className.getDeclaredFields())
                .filter(type-> type.getType() == Boolean.class)
                .map(Field::getName)
                .collect(Collectors.toList());
        List<String> stringValues = Arrays.stream(className.getDeclaredFields())
                .filter(type-> type.getType() == String.class)
                .map(Field::getName)
                .collect(Collectors.toList());

        try (InputStream in = new FileInputStream(csvFileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (reader.ready()) {
                String line = reader.readLine();
                if (StringUtils.isBlank(line))continue;
                if (StringUtils.endsWith(line,DELIMITER)){
                    line = StringUtils.removeEnd(line,DELIMITER);
                }
                List<String> x = Arrays.stream(StringUtils.splitPreserveAllTokens(line, DELIMITER)).collect(Collectors.toList());
                Map<String,String> obj = new LinkedHashMap <> ();
                for (int i = 0; i < fieldNames.size(); i++) {
                    if (boolValues.contains(fieldNames.get(i)) ){
                        if (x.get(i).equals("X")) x.set(i, "true");
                        else x.set(i,"false");
                    }
                    if (stringValues.contains(fieldNames.get(i))){
                        if (x.get(i).equals("null")) x.set(i,"");
                    }
                    obj.put(fieldNames.get(i), x.get(i));
                }
                final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
                list.add(mapper.convertValue( obj, className));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}