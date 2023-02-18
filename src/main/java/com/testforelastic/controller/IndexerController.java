package com.testforelastic.controller;

import com.testforelastic.DTO.UploadModel;
import com.testforelastic.indexing.Indexer;
import com.testforelastic.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

@RestController
@RequestMapping("api/index")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IndexerController {

    private static String DATA_DIR_PATH;

    static {
        ResourceBundle rb=ResourceBundle.getBundle("application");
        DATA_DIR_PATH=rb.getString("dataDir");
    }

    @Autowired
    private Indexer indexer;

    @PostMapping("/add")
    public ResponseEntity<String> multiUploadFileModel(@ModelAttribute UploadModel model) {


        try {

            indexUploadedFile(model);

        } catch (IOException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);

    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

    private File getResourceFilePath(String path) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println(this.getClass().getClassLoader());
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }


    private void indexUploadedFile(UploadModel model) throws IOException{

        for (MultipartFile file : model.getFiles()) {

            if (file.isEmpty()) {
                continue; //next please
            }
            String fileName = saveUploadedFile(file);
            if(fileName != null){
                Applicant indexUnit = indexer.getHandler(fileName).getIndexUnit(new File(fileName));
                indexUnit.setName(model.getName());
                indexUnit.setLastName(model.getLastName());
                indexUnit.setEducation(model.getEducation());
                indexer.add(indexUnit);
            }
        }
    }

}
