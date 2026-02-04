package github.com.tiagoribeine.file.importer.impl;

import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) { //cada arquivo XLSX se torna um workbook dentro do Apache POI
            //Informando qual das abas da planilha estamos lidando
            XSSFSheet sheet = workbook.getSheetAt(0); //Posição 0 - Primeira aba
            Iterator<Row> rowIterator = sheet.iterator(); //Cria um rowIterator

            //Pulando a primeira linha de cabeçalho:
            if (rowIterator.hasNext()) rowIterator.next(); //Pula a primeira linha
            return parseRowsToPersonDtoList(rowIterator); //Faz o Parse de Rows para uma lista de PersonDTO
        }
    }

    private List<PersonDTO> parseRowsToPersonDtoList(Iterator<Row> rowIterator){
        List<PersonDTO> people = new ArrayList<>();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next(); //Para cada Row Iterator
            //Verificando se row é válido:
            if (isRowValid(row)){ //Se a célula nao for nula e nem vazia então irá prosseguir
                people.add(parseRowsToPersonDto(row)); //Se a linha for válida a linha será convertida para uma PersonDTO
            }
        }
        return people;
    }

    private PersonDTO parseRowsToPersonDto(Row row) {
        PersonDTO person = new PersonDTO();
        //Pegando cada uma das colunas
        person.setFirstName(row.getCell(0).getStringCellValue());
        person.setLastName(row.getCell(1).getStringCellValue());
        person.setAddress(row.getCell(2).getStringCellValue());
        person.setGender(row.getCell(3).getStringCellValue());
        person.setEnabled(true);
        return person;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}
