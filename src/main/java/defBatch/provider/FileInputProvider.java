package defBatch.provider;

import defBatch.provider.dao.DataRecord;
import defBatch.provider.exception.FileFormatWrongException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileInputProvider extends IInputProvider {
    private static final Logger log = Logger.getLogger(FileInputProvider.class);
    private static final String charSet = "ISO-8859-1";
    private static final SimpleDateFormat fileDateFormat = new SimpleDateFormat("ddMMyy");
    private static final String CSV_SEPARATOR = ";";

    private final String fileName;
    private RecordIterator recordIterator;
    private BufferedReader reader;

    public FileInputProvider(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Iterator<String> getRecords() {
        return recordIterator;
    }

    @Override
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        while (recordIterator.hasNext()) {
            lines.add(recordIterator.next());
        }
        return lines;
    }


    @Override
    public DataRecord parseDetailed(String record) throws FileFormatWrongException {

        if (record == null || record.isEmpty()) {
            throw new FileFormatWrongException("Wrong File format data-record null");
        }

        String[] splittedRecord = StringUtils.splitPreserveAllTokens(record, CSV_SEPARATOR);


        if (splittedRecord.length !=4) {
            throw new FileFormatWrongException("Wrong data-record length");
        }
        try {
            return new DataRecord(Integer.parseInt(splittedRecord[0]), Long.parseLong(splittedRecord[1]), Long.parseLong(splittedRecord[2]), splittedRecord[3]);
        }catch (Exception e){
            throw new FileFormatWrongException("Wrong format on data records");
        }

    }

    @Override
    public void init() throws FileNotFoundException, UnsupportedEncodingException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
        recordIterator = new RecordIterator();
        log.info("Reading file : " + fileName);
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    public class RecordIterator implements Iterator<String> {

        @Override
        public boolean hasNext() {
            boolean isReady;
            try {
                isReady = reader.ready();
            } catch (IOException e) {
                isReady = false;
            }

            return isReady;
        }

        @Override
        public String next() {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                line = null;
            }
            return line;
        }
    }

}
