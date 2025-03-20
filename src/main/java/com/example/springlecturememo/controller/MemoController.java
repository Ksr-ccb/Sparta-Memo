package com.example.springlecturememo.controller;

import com.example.springlecturememo.dto.MemoRequestDto;
import com.example.springlecturememo.dto.MemoResponseDto;
import com.example.springlecturememo.entity.Memo;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import java.util.*;

@RequestMapping("/memos")
@RestController //데이터를 제이슨 형태로 주고받기위함
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();
    private final FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter;

    public MemoController(FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter) {
        this.resourceUrlEncodingFilter = resourceUrlEncodingFilter;
    }

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto){
        //식별자가 1씩 증가해야 함.
        Long memoId = memoList.isEmpty() ? 1: Collections.max(memoList.keySet()) +1 ;

        //요천받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 메모 저장
        memoList.put(memoId, memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos(){

//        List<MemoResponseDto> responseList = new ArrayList<>();

//        for(Memo memo : memoList.values()){
//            MemoResponseDto responseDto = new MemoResponseDto(memo);
//            responseList.add(responseDto);
//        }
        List<MemoResponseDto> responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id){

        Memo memo = memoList.get(id);

        if(memo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemobyId(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ){
        Memo memo = memoList.get(id);

        if(memo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(dto.getTitle() == null || dto.getContents() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        memo.update(dto);

        return  new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ){
        Memo memo = memoList.get(id);

        if(memo == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(dto.getTitle() == null || dto.getContents() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        memo.updateTitle(dto);

        return  new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemoById(
            @PathVariable Long id
    ){

        if(memoList.containsKey(id)){

            memoList.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
