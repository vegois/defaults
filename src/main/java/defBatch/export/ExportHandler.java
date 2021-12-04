package defBatch.export;


import defBatch.export.exception.ExportFileOperationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportHandler {
    private String fileName;
    private String checkSumFile;
    private WriteRecords writeRecords;
    private BufferedWriter writer;
    private BufferedWriter checkSumWriter;
    private static final Logger xlog = Logger.getLogger(ExportHandler.class);

    public void init( String outputFilePath, String checkSumFile) throws ExportFileOperationException {
        this.fileName = outputFilePath;
        this.writeRecords = new WriteRecords();
        this.checkSumFile = checkSumFile;

        xlog.info("Export file  path: " + fileName);
        if (StringUtils.isEmpty(fileName)) {
            throw new ExportFileOperationException(
                    ExportFileOperationException.OperationType.OPEN, "OS error occurred during file operation.");
        }

        try {
            writer = new BufferedWriter(new FileWriter(fileName,true));
            checkSumWriter = new BufferedWriter(new FileWriter(checkSumFile,true));
        } catch (IOException e) {
            throw new ExportFileOperationException(
                    ExportFileOperationException.OperationType.OPEN, String.format("Filename: [%s]", fileName), e);
        }

        xlog.info("Export file open");
    }

    public void close() throws ExportFileOperationException {
        try {
            if (writer != null) {
                writer.close();
                xlog.info("Export file closed");
            }
            if (checkSumWriter != null) {
                checkSumWriter.close();
                xlog.info("Export checkSumFile closed");
            }
        } catch (IOException e) {
            throw new ExportFileOperationException(
                    ExportFileOperationException.OperationType.CLOSE, String.format("Filename: [%s]", fileName), e);
        }
    }

    public <T> void exportLine(T line) throws ExportFileOperationException {
        try {
            synchronized (writer) {
                writer.append(writeRecords.format(line));
            }

        } catch (IOException e) {
            throw new ExportFileOperationException(
                    ExportFileOperationException.OperationType.WRITE,
                    String.format("Filename: [%s]", fileName), e);
        }
    }

    public void exportCheckSum(Integer jid, int recNumber) throws ExportFileOperationException{
        try {
            synchronized (checkSumWriter) {
                checkSumWriter.append(writeRecords.sumFormat(jid, recNumber));
            }

        } catch (IOException e) {
            throw new ExportFileOperationException(
                    ExportFileOperationException.OperationType.WRITE,
                    String.format("Filename: [%s]", checkSumFile), e);
        }
    }

    public WriteRecords getWriteRecords() {
        return writeRecords;
    }

    public void setWriteRecords(WriteRecords writeRecords) {
        this.writeRecords = writeRecords;
    }

    public <T> void exportFile(List<T> objList) throws ExportFileOperationException {

        try {
            synchronized (writer) {
                writer.append(writeRecords.formatList(objList));
            }

        } catch (IOException e) {
            throw new ExportFileOperationException(
                    ExportFileOperationException.OperationType.WRITE,
                    String.format("Filename: [%s]", fileName), e);
        }
    }
}
