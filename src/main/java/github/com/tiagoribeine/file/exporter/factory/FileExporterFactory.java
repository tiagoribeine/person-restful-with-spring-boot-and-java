package github.com.tiagoribeine.file.exporter.factory;

import github.com.tiagoribeine.exception.BadRequestException;
import github.com.tiagoribeine.file.exporter.MediaTypes;
import github.com.tiagoribeine.file.exporter.contract.PersonExporter;
import github.com.tiagoribeine.file.exporter.impl.CsvExporter;
import github.com.tiagoribeine.file.exporter.impl.XlsxExporter;
import github.com.tiagoribeine.file.exporter.impl.PdfExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component //Para que possamos injetar em outras classes que iremos utilizar
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public PersonExporter getExporter(String acceptHeader) throws Exception{
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) { //Content-Type
            return context.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)){
            return context.getBean(CsvExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)){
            return context.getBean(PdfExporter.class);
        } else{
            throw new BadRequestException("Invalid File Format!");
        }
    }
}
