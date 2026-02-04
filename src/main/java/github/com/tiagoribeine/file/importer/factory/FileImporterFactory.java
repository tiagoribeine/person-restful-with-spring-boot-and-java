package github.com.tiagoribeine.file.importer.factory;

import github.com.tiagoribeine.exception.BadRequestException;
import github.com.tiagoribeine.file.importer.contract.FileImporter;
import github.com.tiagoribeine.file.importer.impl.CsvImporter;
import github.com.tiagoribeine.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component //Para que possamos injetar em outras classes que iremos utilizar
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception{
        if (fileName.endsWith(".xlsx")) {
            return context.getBean(XlsxImporter.class);
        } else if (fileName.endsWith(".csv")){
            return context.getBean(CsvImporter.class);
        } else{
            throw new BadRequestException("Invalid File Format!");
        }
    }
}
