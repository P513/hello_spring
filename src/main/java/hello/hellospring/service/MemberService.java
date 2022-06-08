package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// business 처리용 서비스
public class MemberService {
    private final MemberRepository memberRepository;

    // DI(Dependency Injection) 외부에서 주입한다.
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /*
        회원가입 비즈니스 로직
        1) 이름에 중복이 없어야 한다
     */
    public Long join(Member member) {
        // 중복 회원 검증 method
        validateDuplicateMemer(member);
        memberRepository.save(member);
        return member.getId();
    }

    // Optional이기 때문에 ifPresent로 판단이 가능하다
    private void validateDuplicateMemer(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
        전체 회원 조회
        service class는 business에 가까운 이름을 써야한다
        Repository 쪽과 다르다(직관적)
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
