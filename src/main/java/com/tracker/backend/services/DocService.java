package com.tracker.backend.services;

import com.tracker.backend.payloads.DocDto;

public interface DocService {
	public DocDto save(DocDto docDto, Integer id);

}
