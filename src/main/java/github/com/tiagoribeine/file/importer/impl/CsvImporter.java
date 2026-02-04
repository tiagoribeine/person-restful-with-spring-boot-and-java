package github.com.tiagoribeine.file.importer.impl;

import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader() //Linha de cabeçalho
                .setSkipHeaderRecord(true) //Cada linha no OpenCSV é considerada um record
                .setIgnoreEmptyLines(true) //Ignora linhas vazias
                .setTrim(true) //Corta os espaços antes e depois dos valores
                .build();

        //Parsea em um objeto java chamado CsvRecord -
        // Pegamos cada linha da planilha csv e convertermos elas em um iterable de records
        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToPersonDTOs(records);
    }

    private List<PersonDTO> parseRecordsToPersonDTOs(Iterable<CSVRecord> records) {

        List<PersonDTO> people = new ArrayList<>();
        for (CSVRecord record: records){
            //Iterando convertendo cada um deles em um PersonDTO
            PersonDTO person = new PersonDTO();
            //Pegando cada uma das colunas
            person.setFirstName(record.get("first_name"));
            person.setLastName(record.get("last_name"));
            person.setAddress(record.get("address"));
            person.setGender(record.get("gender"));
            person.setEnabled(true);
            people.add(person);
        }
        return people;
    }
}
