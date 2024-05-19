package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // 1. RequestDto → Entity로 변환해주어야 한다
        Memo memo = new Memo(requestDto);

        // 2. memo의 max id를 찾아준다
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity → ResponseDto 로 바꿔준다
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map 구조를 List로 변환
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
        // memoList.values() : memoList에 있는 모든 값을 꺼내옴
        // .stream() : values를 하면 나오는 여러 값을 하나씩 for문처럼 돌려준다
        // .map(MemoResponseDto) : for문으로 하나씩 튀어나온 데이터를 변환한다. 뭘로? MemoResopnseDto로
        // ::new : 이걸 사용하면 생성자가 호출이 된다

        return responseList;
    }


}



































