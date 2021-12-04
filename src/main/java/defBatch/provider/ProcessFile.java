package defBatch.provider;

import defBatch.provider.dao.DataRecord;
import org.apache.log4j.Logger;

import java.util.List;

public class ProcessFile {
    private final Logger log = Logger.getLogger(ProcessFile.class);


    public ProcessedInputFile getProcessedInputFile(String importFilePath, Integer jid) {
        ProcessedInputFile processedInputFile = new ProcessedInputFile();

        IInputProvider inputProvider = new FileInputProvider(importFilePath);
        try {
            inputProvider.init();
        } catch (Exception e) {
            log.error(String.format("File not found %s", importFilePath));
            //throw new XSERuntimeException("FILE_FIL_NOT_FOUND",new Object[] {"File not found"});
        }
        try {
            List<String> lines = inputProvider.getLines();
            importAndCheckDetailedRecord(inputProvider, processedInputFile, lines);
            log.info(String.format("The file with path: %s",importFilePath ));
            log.info("Imported and Checked successfully! ");
        } finally {
            try {
                inputProvider.close();
            } catch (Exception e) {
                //throw new XSERuntimeException("FILE_NOT_OPEN", new Object[]{"File not open."});
            }

        }
        return processedInputFile;
    }
    private  void importAndCheckDetailedRecord(IInputProvider inputProvider, ProcessedInputFile processedInputFile, List<String> lines) {
        for (int i = 1; i < lines.size() - 1; i++) {
            try {
                DataRecord detailedRecord = inputProvider.parseDetailed(lines.get(i));
                processedInputFile.addDetailedRecord(detailedRecord);
            } catch (Exception e) {
                //throw new XSERuntimeException("Wrong Format ", new Object[]{e.getMessage()});
            }
        }
    }

}
