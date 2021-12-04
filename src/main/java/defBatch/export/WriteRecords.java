package defBatch.export;

import defBatch.export.record.Record;
import defBatch.export.record.RecordFormatter;
import org.springframework.util.ReflectionUtils;

import java.util.List;


public class WriteRecords extends Record {

    @Override
    public <T> StringBuilder format(T obj){
        RecordFormatter rF =  getRecordFormatter();

        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            rF.addColumn(field.get(obj));
        });
        rF.addColumn("");

        return rF.format().append(System.lineSeparator());
    }

    @Override
    public StringBuilder formatObj(Object obj){
        RecordFormatter rF =  getRecordFormatter();

        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            rF.addColumn(field.get(obj));
        });
        rF.addColumn("");

        return rF.format().append(System.lineSeparator());
    }

    @Override
    public <T> StringBuilder formatList(List<T> objList) {
        StringBuilder str = new StringBuilder();
        objList.forEach(s -> {
            str.append(format(s));
        });
        return str;
    }

    @Override
    public StringBuilder sumFormat(Integer jid, int recordCounter) {
        RecordFormatter rF =  getRecordFormatter()
                .addColumn(2)
                .addColumn(jid)
                .addColumn(1)
                .addColumn(1)
                .addColumn("")
                //.addColumn(DigestUtils.sha1Hex(recordCounter+""))
                .addColumn(recordCounter);
        return rF.format().append(System.lineSeparator());
    }


}
