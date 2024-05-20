//package com.sparta.memo.controller;
//
//import com.sparta.memo.dto.MemoRequestDto;
//import com.sparta.memo.dto.MemoResponseDto;
//import com.sparta.memo.entity.Memo;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/api")
//public class MemoController {
//
//    private final Map<Long, Memo> memoList = new HashMap<>();
//
//    @PostMapping("/memos")
//    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
//        // 1. RequestDto → Entity로 변환해주어야 한다
//        Memo memo = new Memo(requestDto);
//
//        // 2. memo의 max id를 찾아준다
//        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
//        memo.setId(maxId);
//
//        // DB 저장
//        memoList.put(memo.getId(), memo);
//
//        // Entity → ResponseDto 로 바꿔준다
//        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
//
//        return memoResponseDto;
//    }
//
//    @GetMapping("/memos")
//    public List<MemoResponseDto> getMemos() {
//        // Map 구조를 List로 변환
//        List<MemoResponseDto> responseList = memoList.values().stream()
//                .map(MemoResponseDto::new).toList();
//        // memoList.values() : memoList에 있는 모든 값을 꺼내옴
//        // .stream() : values를 하면 나오는 여러 값을 하나씩 for문처럼 돌려준다
//        // .map(MemoResponseDto) : for문으로 하나씩 튀어나온 데이터를 변환한다. 뭘로? MemoResopnseDto로
//        // ::new : 이걸 사용하면 생성자가 호출이 된다
//
//        return responseList;
//    }
//
//
//    @PutMapping("/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        // 해당 메모가 DB에 존재하는지 확인
//        if (memoList.containsKey(id)) {
//            // 해당 메모 가져오기
//            Memo memo = memoList.get(id);
//
//            // memo 수정
//            memo.update(requestDto);
//            return memo.getId();
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//    }
//
//    @DeleteMapping("/memos/{id}")
//    public Long deleteMemo(@PathVariable Long id) {
//        // 해당 메모가 DB에 존재하는지 확인
//        if (memoList.containsKey(id)) {
//            // 해당 메모 삭제하기
//            memoList.remove(id);
//            return id;
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
//    }
//}
//

package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final JdbcTemplate jdbcTemplate; //JDBC 템플릿 변수 설정

    public MemoController(JdbcTemplate jdbcTemplate) { //JDBC 템플릿 final이기때문에 초기화
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.createMemo(requestDto);
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {

        MemoService memoService = new MemoService(jdbcTemplate);
        return  memoService.getMemos();


    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.updateMemo(id,requestDto);

    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return  memoService.deleteMemo(id);

    }


}
