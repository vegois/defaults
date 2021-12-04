package defBatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import defBatch.export.ExportHandler;
import defBatch.export.WriteRecords;
import defBatch.export.exception.ExportFileOperationException;
import defBatch.export.record.RecordFormatter;
import defBatch.provider.json.CsvToJsonTest;
import defBatch.util.Student;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        CsvToJsonTest csvToJsonTest = new CsvToJsonTest();

        List<String> fields = Arrays.stream(Student.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());


        List<Map<String, String>> mapcsv = csvToJsonTest.csvToJson("file.CSV", fields, "|");
        List<Student> mapcsvObj = csvToJsonTest.csvToObj("file.CSV","|", Student.class);

    }
}
