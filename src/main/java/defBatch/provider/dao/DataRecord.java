package defBatch.provider.dao;


public class DataRecord implements BaseRecord {

   private int outletGroupNo;
   private Long extOutletIdFrom;
   private Long ext_outletIdTo;
   private String description;

    public DataRecord(int outletGroupNo, Long extOutletIdFrom, Long ext_outletIdTo, String description) {
        this.outletGroupNo = outletGroupNo;
        this.extOutletIdFrom = extOutletIdFrom;
        this.ext_outletIdTo = ext_outletIdTo;
        this.description = description;
    }

    public int getOutletGroupNo() {
        return outletGroupNo;
    }

    public void setOutletGroupNo(int outletGroupNo) {
        this.outletGroupNo = outletGroupNo;
    }

    public Long getExtOutletIdFrom() {
        return extOutletIdFrom;
    }

    public void setExtOutletIdFrom(Long extOutletIdFrom) {
        this.extOutletIdFrom = extOutletIdFrom;
    }

    public Long getExt_outletIdTo() {
        return ext_outletIdTo;
    }

    public void setExt_outletIdTo(Long ext_outletIdTo) {
        this.ext_outletIdTo = ext_outletIdTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getRecordType() {
        return null;
    }
}
