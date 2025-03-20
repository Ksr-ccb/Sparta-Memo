package com.example.springlecturememo.entity;

import com.example.springlecturememo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Memo {

    private Long id; //널 포함할 수 있고 범위가 넓어서( PK가 되는 번호는 Long을 많이 쓴다고 함)
    private String title;
    private String contents;

    public void update(MemoRequestDto dto){
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }

    public void updateTitle(MemoRequestDto dto) {
        this.title = dto.getTitle();
    }
}
