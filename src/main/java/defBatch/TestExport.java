package defBatch;

import defBatch.exportVI.ExportHandler;
import defBatch.exportVI.WriteRecords;
import defBatch.exportVI.exception.ExportFileOperationException;
import defBatch.exportVI.record.RecordFormatter;
import defBatch.provider.json.TextToJson;
import defBatch.util.Student;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class TestExport {

    public static void main(String[] args) {

        List<Student> studentList = new ArrayList<>();
        WriteRecords writeRecords = new WriteRecords();

        Student vegim = new Student("Vegim", null, true, BigDecimal.TEN, 22.22);
        Student mereme = new Student("Mereme", 19, false, BigDecimal.TEN, 22.23);



        ExportHandler exportHandler = new ExportHandler();
        try {
            exportHandler.init("file.CSV", "checkSumFile.CKS");
            RecordFormatter.setDELIMITER("|");
            exportHandler.exportLine(vegim);
            exportHandler.exportLine(mereme);
            exportHandler.exportCheckSum(3,2);
            exportHandler.close();
        } catch (ExportFileOperationException e) {
            e.printStackTrace();
        }
        TextToJson textToJson = new TextToJson();

        List<String> fields = Arrays.stream(Student.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> boolFields = getBooleanFields(Student.class);


        List<Map<String, String>> mapcsv = textToJson.csvToJson("file.CSV", fields, "|");
        List<Student> mapcsvObj = textToJson.csvToObj("file.CSV","|", Student.class);



        Set<Student> studentList1 = textToJson.convertListToObj(mapcsv, textToJson.getNeededClassFieldTypes(Student.class, Boolean.class),"X","null",Student.class);

        studentList1.forEach(
                student -> System.out.println(student.toString())
        );
    }

    private static List<String> getBooleanFields(Class<?> className){

        return Arrays.stream(className.getDeclaredFields())
                .filter(type-> type.getType() == Boolean.class)
                .map(Field::getName)
                .collect(Collectors.toList());
    }

}
