package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;  //사용자가 업로드한 파일명
    private String storeFileName;   //서버 내부에서 관리하는 파일명 (파일 이름이 같을 수 있기때문)

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}