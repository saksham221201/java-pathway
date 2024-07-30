package com.nagarro.kycmodule.service.impl;

import com.nagarro.kycmodule.dao.KycDao;
import com.nagarro.kycmodule.entity.Kyc;
import com.nagarro.kycmodule.exception.BadRequestException;
import com.nagarro.kycmodule.service.KycService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {

    private final KycDao kycDao;

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
}
