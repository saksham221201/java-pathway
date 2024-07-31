package com.nagarro.kycmodule.service.impl;

import com.nagarro.kycmodule.client.UserServiceClient;
import com.nagarro.kycmodule.dao.KycDao;
import com.nagarro.kycmodule.dto.User;
import com.nagarro.kycmodule.entity.Kyc;
import com.nagarro.kycmodule.exception.BadRequestException;
import com.nagarro.kycmodule.exception.IllegalArgumentException;
import com.nagarro.kycmodule.exception.RecordAlreadyExistsException;
import com.nagarro.kycmodule.exception.RecordNotFoundException;
import com.nagarro.kycmodule.response.KycDocumentResponse;
import com.nagarro.kycmodule.service.KycService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {

    private final Logger logger = LoggerFactory.getLogger(KycServiceImpl.class);

    private final KycDao kycDao;
    private final UserServiceClient userServiceClient;

    @Autowired
    public KycServiceImpl(KycDao kycDao, UserServiceClient userServiceClient) {
        this.kycDao = kycDao;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Kyc uploadKyc(Kyc kyc) {
        final User user = userServiceClient.getUserByEmail(kyc.getEmail());

        Optional<Kyc> existingKyc = kycDao.findByEmail(kyc.getEmail());
        if (existingKyc.isPresent()) {
            throw new RecordAlreadyExistsException("KYC already exists for the user " + kyc.getEmail(), HttpStatus.BAD_REQUEST.value());
        }

        kyc.setKycStatus("INCOMPLETE");
        return kycDao.save(kyc);
    }

    @Override
    public void storeDocument(MultipartFile multipartFile, int kycId, String fileName) throws IOException {
        Optional<Kyc> existingKyc = kycDao.findById(kycId);
        if (existingKyc.isEmpty()) {
            throw new BadRequestException("KYC does not exists!!", HttpStatus.BAD_REQUEST.value());
        }
        Kyc kyc = existingKyc.get();
        byte[] content = multipartFile.getBytes();
        kyc.setDocument(content);
        kyc.setFileName(fileName);
        kycDao.save(kyc);
    }

    @Override
    public List<Kyc> getAllKyc() {
        return kycDao.findAll();
    }

    @Override
    public KycDocumentResponse getDocumentText(int id) throws IOException {
        Optional<Kyc> optionalKyc = kycDao.findById(id);
        if (optionalKyc.isEmpty()) {
            logger.error("KYC not found with id {}", id);
            throw new RecordNotFoundException("KYC not found with id " + id, HttpStatus.NOT_FOUND.value());
        }

        User user = userServiceClient.getUserByEmail(optionalKyc.get().getEmail());
        byte[] document = optionalKyc.get().getDocument();
        String fileName = optionalKyc.get().getFileName();

        if (document == null) {
            throw new RecordNotFoundException("Document Not found for the KYC", HttpStatus.NOT_FOUND.value());
        }

        String documentText = null;
        if (fileName.endsWith(".docx")) {
            logger.info("FileName ends with docx");
            documentText = extractTextFromDocx(document);
        } else if (fileName.endsWith(".pdf")) {
            logger.info("FileName ends with PDF");
            documentText = extractTextFromPdf(document);
        }
        return KycDocumentResponse
                .builder()
                .user(user)
                .documentText(documentText)
                .build();
    }

    private String extractTextFromDocx(byte[] document) throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document)){
            XWPFDocument doc = new XWPFDocument(byteArrayInputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            return extractor.getText();
        }
    }

    private String extractTextFromPdf(byte[] document) throws IOException {
        logger.info("Inside Extract From PDF");
        try (PDDocument pdDocument = PDDocument.load(new ByteArrayInputStream(document))){
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(pdDocument);
        }
    }
}
