package github.com.tiagoribeine.file.exporter.impl;

import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.file.exporter.contract.PersonExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class XlsxExporter implements PersonExporter {
    @Override
    public Resource exportPeople(List<PersonDTO> people) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("people"); //Criando e nomeando a aba "people"

            //Criando o cabeçalho
            Row headerRow = sheet.createRow(0); //Linha de numero 0 - Header
            String[] headers = {"ID", "First Name", "Last Name", "Address" , "Gender", "Enabled"};
            for (int i = 0; i< headers.length; i++){
                //Para cada item será criado uma célula:
                Cell cell = headerRow.createCell(i);
                //settando os valores:
                cell.setCellValue(headers[i]);
                //Settando o estilo:
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowIndex = 1;
            for (PersonDTO person : people) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(person.getId());
                row.createCell(1).setCellValue(person.getFirstName());
                row.createCell(2).setCellValue(person.getLastName());
                row.createCell(3).setCellValue(person.getAddress());
                row.createCell(4).setCellValue(person.getGender());
                row.createCell(5).setCellValue(
                        person.getEnabled() != null && person.getEnabled() ? "Yes": "No");
            }

            //Formatando a planilha - Tamanho das colunas
            for (int i = 0; i< headers.length; i++){
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    @Override
    public Resource exportPerson(PersonDTO person) throws Exception {
        return null;
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont(); //Definindo a fonte
        font.setBold(true); //Cabeçalho em negrito
        style.setFont(font); //Settando a fonte
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
