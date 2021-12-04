package defBatch.export.record;


import java.util.List;

public abstract class Record {
    protected static final String EMPTY_COLUMN = null;
    protected static final String EMPTY_SPACE = " ";
    protected static final String COLUMN_X = "X";
    
    protected RecordFormatter getRecordFormatter() {
        return new RecordFormatter();
    }

    protected static String xIf(Boolean b) {
        return b != null && b ? COLUMN_X : EMPTY_COLUMN;
    }
    
    /*
     * return true if
     *      - iObject is not null
     *      - AND iObject = i
     */
    protected boolean compareInteger(Integer iObject, int i) {
        return iObject != null && iObject.equals(i);
    }

    public abstract <T> StringBuilder format( T object) ;

    public abstract StringBuilder formatObj( Object object) ;

    public abstract <T> StringBuilder formatList(List<T> objList);

    public abstract StringBuilder sumFormat(Integer jid, int recordCounter);

}
