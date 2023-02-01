package com.pdf.generate.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import com.pdf.generate.model.MetaData;
import com.pdf.generate.service.ISetPasswordRules;

@Service
public class SetPasswordRulesImpl implements ISetPasswordRules{

	@Override
	public String generatePassword(MetaData mdata) {
		
		String name1 = (String) mdata.getCreator().subSequence(0, 3);
		String sub1=(String) mdata.getSubject().subSequence(0, 2);
		String pass1=name1.concat(sub1);
		
		String name2=mdata.getCreator();
		String num2="123";
		String pass2=name2.concat(num2);
		
		String name3=mdata.getTitle();
		String num3="123";
		String pass3=name3.concat(num3);
		List<String> password = Arrays.asList(pass1, pass2, pass3);
		Random rand = null;
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}  
		String pdfpassword = password.get(rand.nextInt(password.size()));
		return pdfpassword;
		
		
	
}
}
