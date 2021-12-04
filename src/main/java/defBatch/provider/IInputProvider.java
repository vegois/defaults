package defBatch.provider;

import defBatch.provider.dao.DataRecord;
import defBatch.provider.exception.FileFormatWrongException;

import java.util.Iterator;
import java.util.List;

public abstract class IInputProvider {

    public abstract DataRecord parseDetailed(String record) throws FileFormatWrongException;

    public abstract void init() throws Exception;

    public abstract void close() throws Exception;

    public abstract Iterator<String> getRecords();

    public abstract List<String> getLines();

}
