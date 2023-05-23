package com.ssafy.vue.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.ibatis.session.SqlSession;
import com.ssafy.util.OpenCrypt;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.MemberMapper;
import com.ssafy.vue.model.service.FileHandlerService;
@Service
public class FileHandlerServiceImpl implements FileHandlerService{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
    public List<String> parseFileInfo(MemberDto memberDto, List<MultipartFile> multipartFiles) throws Exception{
        // 파일들의 서버 내 경로를 저장하는 fileList
        List<String> filePathList = new ArrayList<>();

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if(multipartFiles.isEmpty()){
            return filePathList;
        }
        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "/";

        // 경로를 지정하고 그곳에다가 저장할 심산이다
        String path = "images/";
        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if(!file.exists()){
            file.mkdirs();
        }

        // 파일들을 이제 만져볼 것이다
        for (MultipartFile multipartFile : multipartFiles){
            // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
            if(!multipartFile.isEmpty()){
                // jpg, jpeg, png, gif 파일들만 받아서 처리할 예정
                String contentType = multipartFile.getContentType();
                String originalName = multipartFile.getOriginalFilename(); 
                String originalFileExtension;
                    // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)){
                    break;
                }
                else{
                    if(contentType.contains("image/jpeg") || contentType.contains("image/jpg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else{
                        break;
                    }
                }
                // 암호화된 네이밍 만들기
                String salt = sqlSession.getMapper(MemberMapper.class).getSalt(memberDto.getMemberId());
                String hashFileName = OpenCrypt.getSHA256(originalName, salt);
                String resultPath = absolutePath + path + "/" + hashFileName + originalFileExtension;
                // 생성 후 리스트에 추가
                filePathList.add(hashFileName + originalFileExtension);

                // 저장된 파일로 변경하여 이를 보여주기 위함
                file = new File(absolutePath + path + "/" + hashFileName + originalFileExtension);
                multipartFile.transferTo(file);
            }
        }

        return filePathList;
    }

}