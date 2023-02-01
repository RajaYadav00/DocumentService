package com.pdf.generate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.itextpdf.text.DocumentException;
import com.pdf.generate.model.MetaData;
import com.pdf.generate.model.TemplateRequest;
import com.pdf.generate.repository.TemplateRepository;
import com.pdf.generate.service.IPdfGeneratorService;
import com.pdf.generate.service.ISetPasswordRules;
import com.pdf.generate.service.impl.PdfGeneratorServiceImpl;

@SpringBootTest
class PdfGeneratorApplicationTests {

	@Autowired
	TemplateRepository templateRepository;
	@Autowired
	IPdfGeneratorService iPdfGeneratorService;
	@Autowired
	ISetPasswordRules iSetPasswordRules;
	@BeforeEach
	public void set() {
		this.iPdfGeneratorService=new PdfGeneratorServiceImpl(iSetPasswordRules,templateRepository);
	}

	@Test
	void contextLoads() throws IOException, DocumentException {
		HttpServletResponse request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
		Map< String, String> map=new HashMap<>();
		map.put("{{nameL}}", "Raja");
		TemplateRequest templateRequest=TemplateRequest.builder()
				.templateType("loanAgreement")
				.waterMark("Y")
				.metadata(MetaData.builder().creationDate("01/02/2013").creator("Raja").subject("loagAgreement").title("loagAgreement").build())
				.waterMarktype("image")
				.templateVariables(map)
				.build();
		
		String str=iPdfGeneratorService.exportPdf(templateRequest, request);
		assertEquals("pdf generated successfully", str);
	}

}
