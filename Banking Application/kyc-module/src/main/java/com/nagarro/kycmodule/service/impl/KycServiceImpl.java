package com.nagarro.kycmodule.service.impl;

import com.nagarro.kycmodule.client.UserServiceClient;
import com.nagarro.kycmodule.controller.KycController;
import com.nagarro.kycmodule.dao.KycDao;
import com.nagarro.kycmodule.dto.User;
import com.nagarro.kycmodule.entity.Kyc;
import com.nagarro.kycmodule.exception.BadRequestException;
import com.nagarro.kycmodule.exception.RecordNotFoundException;
import com.nagarro.kycmodule.response.KycDocumentResponse;
import com.nagarro.kycmodule.service.KycService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
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

    @Autowired
    public KycServiceImpl(KycDao kycDao) {
        this.kycDao = kycDao;
    }

    @Override
    public Kyc uploadKyc(Kyc kyc) {
        return kycDao.save(kyc);
    }

    @Override
    public void storeDocument(MultipartFile multipartFile, int kycId) throws IOException {
        Optional<Kyc> existingKyc = kycDao.findById(kycId);
        if (existingKyc.isEmpty()) {
            throw new BadRequestException("KYC does not exists!!", HttpStatus.BAD_REQUEST.value());
        }
        Kyc kyc = existingKyc.get();
        byte[] content = multipartFile.getBytes();
        kyc.setDocument(content);
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
        byte[] document = optionalKyc.get().getDocument();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document)){
            XWPFDocument doc = new XWPFDocument(byteArrayInputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String text = extractor.getText();
            return KycDocumentResponse
                    .builder()
                    .documentText(text)
                    .build();
        }
    }
}
