package github.com.tiagoribeine.controllers.docs;

import github.com.tiagoribeine.data.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpointg")
public interface FileControllerDocs {

    //Estrutura dos metodos principais: Um para Download, Um para Upload e outro para Upload múltiplo

    UploadFileResponseDTO uploadFile(MultipartFile file);

    List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] files);
    ResponseEntity<Resource> downloadFile(String filename,
                                          HttpServletRequest request);
}
