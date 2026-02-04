package github.com.tiagoribeine.file.importer.contract;

import github.com.tiagoribeine.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
