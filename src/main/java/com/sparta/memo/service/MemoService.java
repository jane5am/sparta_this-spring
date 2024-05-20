package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(JdbcTemplate jdbcTemplate) { //JDBC 템플릿 final이기때문에 초기화

        this.memoRepository = new MemoRepository(jdbcTemplate);
    }


    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {


        // DB 조회
        return memoRepository.findAll();


    }

    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            // memo 내용 수정
            memoRepository.update(id, requestDto);


            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }



    public Long deleteMemo(Long id) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            // memo 삭제
            memoRepository.delete(id);


            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
