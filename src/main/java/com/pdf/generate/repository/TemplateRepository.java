package com.pdf.generate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pdf.generate.model.TemplateModel;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateModel, Integer>{

	public TemplateModel findByTemplateType(String type);
	
}
