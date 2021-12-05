package defBatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import defBatch.util.Student;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestJson {

    public static void main(String[] args) {

        Student vegim = new Student("Vegim", null, true, BigDecimal.TEN, 22.22);
        Student mereme = new Student("Mereme", 19, false, BigDecimal.TEN, 22.23);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonVegim = null;
        String jsonMereme = null;

        try {
            jsonVegim = objectMapper.writeValueAsString(vegim);
            jsonMereme = objectMapper.writeValueAsString(mereme);


        System.out.println(jsonVegim);
        jsonVegim = StringUtils.replace(jsonVegim,"true","\"X\"");
        jsonVegim = StringUtils.replace(jsonVegim,"false","");
        //jsonVegim = StringUtils.replace(jsonVegim,"null","");


        System.out.println(jsonMereme);
        jsonMereme = StringUtils.replace(jsonMereme,"true","\"X\"");
        jsonMereme = StringUtils.replace(jsonMereme,"false","");
        //jsonMereme = StringUtils.replace(jsonMereme,"null","");

        List<String> boolValues = Arrays.stream(Student.class.getDeclaredFields())
                    .filter(type-> type.getType() == Boolean.class)
                    .map(Field::getName)
                    .collect(Collectors.toList());
            for (String bools: boolValues) {
                String checkFalse = "\"" + bools + "\":,";
                String checkTrue = "\"" + bools + "\":\"X\",";

                jsonVegim = StringUtils.replace(jsonVegim,"\"" + bools + "\":,","\"" + bools + "\":false," );
                    jsonVegim = StringUtils.replace(jsonVegim,"\"" + bools + "\":\"X\",","\"" + bools + "\":true," );

            }
            Student vegObj = objectMapper.readValue(jsonVegim,Student.class);
            System.out.println(vegObj.toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
