package com.example.springlecturememo.dto;

import com.example.springlecturememo.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {

    //저장될 때 필요한 데이터들
    private Long id; //널 포함할 수 있고 범위가 넓어서( PK가 되는 번호는 Long을 많이 쓴다고 함)
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo){
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
    }

}
