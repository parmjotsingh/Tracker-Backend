package com.tracker.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracker.backend.entities.Document;
import com.tracker.backend.entities.User;
import com.tracker.backend.exceptions.ResourceNotFoundException;
import com.tracker.backend.payloads.DocDto;
import com.tracker.backend.repositories.DocumentRepo;
import com.tracker.backend.repositories.UserRepo;
import com.tracker.backend.services.DocService;

@Service
public class DocServiceImpl implements DocService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	DocumentRepo documentRepo;
	
	@Override
	public DocDto save(DocDto docDto, Integer userId) {
		User user=this.userRepo.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User", "user ID ", userId));
		
		Document savedDoc=null;
		Optional<Document> documentEntry=documentRepo.findByUser(user);
		if(documentEntry.isPresent()) {
			documentEntry.get().setData(docDto.getData());
			savedDoc = documentRepo.save(documentEntry.get());
		}else {
			Document doc = new Document();
			doc.setUser(user);
			doc.setData(docDto.getData());
			savedDoc = documentRepo.save(doc);
		}
		
		docDto.setEntryId(savedDoc.getEntryId().toString());
		return docDto;
	}

}
