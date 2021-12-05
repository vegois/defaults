package defBatch.exportVI.record;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordFormatter {
    public enum DateFormat {
        YYYYMMDDHHMMSS,
        HHMM
    }
    
    private static final SimpleDateFormat DATE_FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat TIME_FORMAT_HHMM = new SimpleDateFormat("HH:mm");
    private static  String DELIMITER = ";";
    
    // a string type column may be decorated, ie. pre- and postfixed with this char
    private static final String DECORATOR = "\"";
    
    // config parameter if a string is never/always decorated or only if it contains the delimiter char
    private static final StringDecoratorMode stringDecoratorMode = StringDecoratorMode.ONLY_DECORATE_IF_CONTAINING_DELIMITER;
    
    private static final String EMPTY_ITEM = "";
    
    private final StringBuilder sb;
    private int itemCounter = 0;

    RecordFormatter() {
        this.sb = new StringBuilder();
    }

    public RecordFormatter addColumn(Object obj) {

        if (obj instanceof String){
            return addColumn((String) obj);
        } else if (obj instanceof Date){
            return addColumn((Date) obj, DateFormat.YYYYMMDDHHMMSS);
        }else if (obj instanceof BigDecimal){
            return addColumn((BigDecimal) obj);
        }else if (obj instanceof Long){
            return addColumn((Long) obj);
        }else if (obj instanceof Boolean){
            return addColumn((Boolean) obj);
        }else if (obj instanceof Double){
            return addColumn((Double) obj);
        }else if (obj instanceof Integer){
            return addColumn((Integer) obj);
        }else if (obj == null){
            return addColumn("");
        }
        return this;
    }
    
    public RecordFormatter addColumn(String s) {
        addItem(s != null ? s : EMPTY_ITEM, DataType.STRING);
        return this;
    }
    
    public RecordFormatter addColumn(Date d, DateFormat format) {
        addItem(d != null ? formatDate(d, format) : EMPTY_ITEM);
        return this;
    }
    
    public RecordFormatter addColumn(Integer i) {
        addItem(i != null ? i.toString() : EMPTY_ITEM);
        return this;
    }
    
    public RecordFormatter addColumn(Long l) {
        addItem(l != null ? l.toString() : EMPTY_ITEM);
        return this;
    }
    public RecordFormatter addColumn(Boolean b) {
        addItem(b != null && b.equals(true) ? "X" : EMPTY_ITEM);
        return this;
    }
    public RecordFormatter addColumn(Double l) {
        return addColumn(BigDecimal.valueOf(l));
    }
    
    public RecordFormatter addColumn(BigDecimal bd) {
    	if(bd != null)
    		bd
                    = bd.setScale(2,RoundingMode.HALF_UP);
        addItem(bd != null ? bd.toString() : EMPTY_ITEM);
        return this;
    }
    
    public StringBuilder format() {
        return sb;
    }
    
    private String formatDate(Date d, DateFormat format) {
        SimpleDateFormat sdf = null ;
        switch (format) {
            case YYYYMMDDHHMMSS:
                sdf = DATE_FORMAT_YYYYMMDDHHMMSS;
                break;
            case HHMM:
                sdf = TIME_FORMAT_HHMM;
                break;
        }
        return sdf.format(d);
    }
    
    private String decorateItem(String itemTxt, DataType dataType) {
        boolean doDecoration = false;

        if (dataType.equals(DataType.STRING)) {
            switch (stringDecoratorMode) {
            case ALWAYS_DECORATE:
                doDecoration = true;
                break;
            case ONLY_DECORATE_IF_CONTAINING_DELIMITER:
                if (itemTxt.contains(DELIMITER)) {
                    doDecoration = true;
                }
                break;
            default:
                break;
            }
        }
        return doDecoration ? DECORATOR + itemTxt + DECORATOR : itemTxt;
    }
    
    private void addItem(String itemTxt, DataType dataType) {
        if (itemCounter > 0) {
            sb.append(DELIMITER);
        }
        sb.append(decorateItem(itemTxt, dataType));
        itemCounter ++;        
    }

    private void addItem(String itemTxt) {
        addItem(itemTxt, DataType.NON_STRING);
    }
    
    private enum DataType {
        STRING, NON_STRING
    }

    private enum StringDecoratorMode {
        NEVER_DECORATE,
        ALWAYS_DECORATE, 
        ONLY_DECORATE_IF_CONTAINING_DELIMITER
    }


    public static void setDELIMITER(String DELIMITER) {
        RecordFormatter.DELIMITER = DELIMITER;
    }
}
