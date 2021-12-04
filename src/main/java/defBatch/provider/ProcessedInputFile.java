package defBatch.provider;



import defBatch.provider.dao.DataRecord;

import java.util.ArrayList;
import java.util.List;

public class ProcessedInputFile {

    private List<DataRecord> detailedRecordList;


    public List<DataRecord> getDetailedRecordList() {
        if (this.detailedRecordList == null) {
            detailedRecordList = new ArrayList<>();
        }
        return detailedRecordList;
    }

    public void setDetailedRecordList(List<DataRecord> detailedRecordList) {
        this.detailedRecordList = detailedRecordList;
    }

    public void addDetailedRecord(DataRecord detailedRecord) {
        if (this.detailedRecordList == null) {
            detailedRecordList = new ArrayList<>();
        }
        detailedRecordList.add(detailedRecord);
    }

}
