package com.nagarro.kycmodule.service;

import com.nagarro.kycmodule.entity.Kyc;
import com.nagarro.kycmodule.response.KycDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface KycService {
    Kyc uploadKyc(Kyc kyc);
    void storeDocument(MultipartFile multipartFile, int kycId, String fileName) throws IOException;
    List<Kyc> getAllKyc();
    KycDocumentResponse getDocumentText(int id) throws IOException;
}
