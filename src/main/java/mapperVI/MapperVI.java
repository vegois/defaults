package mapperVI;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MapperVI {

    private Map<String, Object> mapPojo;

    public <T> List<T> mapObjToClass(List<Object []> listOfObjects, Class<T> className ){
        return convertObjToClass(listOfObjects,className,0 , new ArrayList<>());
    }

    public <T> List<T> mapObjToClass(List<Object []> listOfObjects, Class<T> className, int index ){
        return convertObjToClass(listOfObjects,className,index, new ArrayList<>());
    }

    public <T> List<T> mapObjToClass(List<Object []> listOfObjects, Class<T> className, List<String> fields ){
        return convertObjToClass(listOfObjects,className,0,fields);
    }

    private <T> List<T> convertObjToClass(List<Object []> listOfObjects, Class<T> className, int index, List<String> fields ) {
        if (listOfObjects.isEmpty()) return new ArrayList<>();

        List<T> classList = new ArrayList<>();
        mapPojo = new HashMap<>();

        if (fields.isEmpty()) fields = Arrays.stream(className.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> finalFields = fields;


        listOfObjects.forEach(obj -> {
            AtomicInteger idx = new AtomicInteger(index);
            for (int i = 0; i < obj.length; i++) {
                mapPojo.put(finalFields.get(idx.get()), obj[i]);
                idx.getAndIncrement();
            }
            final ObjectMapper mapper = new ObjectMapper();
            classList.add(mapper.convertValue(mapPojo, className));
        });

        return classList;
    }
}
