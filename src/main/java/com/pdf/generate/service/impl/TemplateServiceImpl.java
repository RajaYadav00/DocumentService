package com.pdf.generate.service.impl;

import java.io.IOException;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.pdf.generate.model.TemplateModel;
import com.pdf.generate.model.TemplateVariables;
import com.pdf.generate.model.common.ResponseModel;
import com.pdf.generate.repository.TemplateRepository;
import com.pdf.generate.service.ITemplateService;

@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	TemplateRepository templaterepo;

	@Override
	public ResponseModel saveTemplate(MultipartFile file, String type, List<TemplateVariables> templateVariables,
			MultipartFile waterMark) {

		TemplateModel templatemodel = new TemplateModel();

		String tempB64 = null;
		String waterMarkB64 = null;
		try {
			tempB64 = Base64.encodeBase64String(file.getBytes());
			waterMarkB64 = Base64.encodeBase64String(waterMark.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		templatemodel.setTemplate(tempB64);
		templatemodel.setTemplateType(type);
		templatemodel.setWaterMark(waterMarkB64);
		templatemodel.setTemplateVariables(templateVariables);

		templaterepo.save(templatemodel);

		return ResponseModel.builder().statusCode(HttpStatus.OK.value()).message("File saved successfully").build();
	}
}
