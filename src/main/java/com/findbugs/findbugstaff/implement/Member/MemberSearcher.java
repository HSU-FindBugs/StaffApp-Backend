package com.findbugs.findbugstaff.implement.Member;

import com.findbugs.findbugstaff.domain.Member;
import com.findbugs.findbugstaff.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberSearcher {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String,String> redisTemplate;

    public List<Member> getAllMembers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Member> memberPage = memberRepository.findAll(pageable);

        return memberPage.getContent(); // Member 객체를 그대로 반환
    }

    public List<Member> searchMemberData(String name,Long staffId){

        // 멤버 검색
        List<Member> members = memberRepository.findByName(name);

        // 최근 검색어 저장
        saveRecentSearch(staffId, name);

        return members;
    }

    private void saveRecentSearch(Long staffId, String searchTerm) {
        String key = "recent_search" + staffId;
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        
        // 검색어를 리스트의 맨 뒤에 추가
        listOperations.rightPush(key, searchTerm);
    
        // 최대 10개의 정보만 저장 (리스트의 0~9 인덱스 범위에서 10개를 유지)
        long listSize = listOperations.size(key); // 현재 리스트의 길이 확인
        if (listSize > 10) {
            // 10개가 넘으면 첫 번째 항목 제거
            listOperations.leftPop(key);
        }
    }


    public List<String> getRecentSearchData(Long staffId){
        // 스테프 id에 맞는 최근 검색어 서치
        String key = "recent_search" + staffId;
        List<String> recentSearches = redisTemplate.opsForList().range(key, 0, -1);

        return recentSearches;
    }

    public Optional<Member> memberSearcher(Long memberId) {
        return memberRepository.findById(memberId); }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
