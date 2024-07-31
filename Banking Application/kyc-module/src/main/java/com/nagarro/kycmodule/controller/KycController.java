package com.nagarro.kycmodule.controller;

import com.nagarro.kycmodule.entity.Kyc;
import com.nagarro.kycmodule.response.KycDocumentResponse;
import com.nagarro.kycmodule.service.KycService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class KycController {

    private final Logger logger = LoggerFactory.getLogger(KycController.class);

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to KYC Application!");
        return "index";
    }

    @PostMapping("/uploadKyc")
    public ResponseEntity<Kyc> uploadKyc(@RequestBody Kyc kyc) {
        Kyc uploadedKyc = kycService.uploadKyc(kyc);
        logger.info("KYC: {}", uploadedKyc);
        return new ResponseEntity<>(uploadedKyc, HttpStatus.CREATED);
    }

    @PostMapping("/uploadDocument")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("kycId") int kycId, Model model) throws IOException {
        kycService.storeDocument(file, kycId, file.getOriginalFilename());
        return "uploadSuccess";
    }

    @RequestMapping("/view")
    public String showDocuments(Model model) {
        List<Kyc> documents = kycService.getAllKyc();
        model.addAttribute("documents", documents);
        return "viewAllDocs";
    }

    @GetMapping("/document/text/{id}")
    public ResponseEntity<KycDocumentResponse> extractTextFromDocument(@PathVariable int id) {
        logger.info("Inside extract text controller");
        try {
            KycDocumentResponse kycDocumentResponse = kycService.getDocumentText(id);
            return new ResponseEntity<>(kycDocumentResponse, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("IO Exception in controller");
            throw new RuntimeException(e.getMessage());
        }
    }
}
