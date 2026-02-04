package github.com.tiagoribeine.controllers;

import github.com.tiagoribeine.controllers.docs.FileControllerDocs;
import github.com.tiagoribeine.data.dto.UploadFileResponseDTO;
import github.com.tiagoribeine.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController //Spring entenderá que é um controlador REST
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {
    //Controlador REST para opçeraões de upload/download de arquivos.

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired //Spring injeta automaticamente uma instância de FileStorageService
    private FileStorageService service;

    //======== Upload a file
    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(
            @RequestParam("file") MultipartFile file //Extrai o parâmetro "file" do corpo da requisição
    ) { //Multipart File recebido através da Request

        // 1 -> Chama o serviço para salvar o arquivo fisicamente no disco - Retorna o nome único gerado para o arquivo salvo
        var fileName = service.storeFile(file);

        // 2 -> Cria uma URL completa para permitir o download do arquivo posteriormente
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath() //Constroi o caminho do arquivo. Base Path mudará dependendo de onde a plicação ta implamantada
                .path("/api/file/v1/downloadFile/")
                .path(fileName)
                .toUriString();
                //Link será montado no formato: http://localhost:8080/api/file/v1/downloadFile/filename.docx

        // 3 -> Retorno da resposta - Cria e retorna um DTO com informações sobre o upload
        return new UploadFileResponseDTO(
                fileName, // Nome do arquivo salvo
                fileDownloadUri, //URL para download
                file.getContentType(), //Tipo do arquivo(ex: image/jpeg)
                file.getSize()); // Tamanho do arquivo em bytes
    }

    // ======= Uploading multiplefiles
@PostMapping("/uploadMultipleFiles")
    @Override
    public List<UploadFileResponseDTO> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files //Extrai o parametro files do corpo da requisição e os armazena em um array
){
        return Arrays.asList(files)
                .stream() //Cria um Stream a partir da lista para processamento funcional
                .map(file -> uploadFile(file)) //map: aplica uma função a cada elemento do Stream. Para cada file iremos executar a função uploadFile
                .collect(Collectors.toList()); //Coleta os resultados do Stream em uma nova lista
    }

    @GetMapping("/downloadFile/{fileName:.+}") //:.+ é referente a extensão
    @Override
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName, //Nome do arquivo a ser baixado
            HttpServletRequest request //Gerado pelo próprio HTTP
    ){
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e){
            logger.error("Could not determine file type!");
        }

        if (contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
