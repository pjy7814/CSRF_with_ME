package com.ssafy.vue.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.ssafy.vue.model.MemberDto;

public interface FileHandlerService {
	public List<String> parseFileInfo(String string, List<MultipartFile> multipartFiles) throws Exception;
}
