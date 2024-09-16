package com.findbugs.findbugstaff.dto.Member;

import com.findbugs.findbugstaff.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MemberUpdateRequestDto {
    @Positive(message = "staff아이디는 0초과인 양수여야합니다.")
    public Long staffId;
    @Positive(message = "member 아이디는 0초과인 양수여야합니다.")
    public Long memberId;

    @NotEmpty(message = "이름을 작성해주세요")
    public String name;
    @Email(message = "유효한 이메일 형식으로 작성해주세요")
    public String email;
    @Pattern(regexp = "^(010-\\d{4}-\\d{4})$", message = "전화번호는 010-0000-0000 형식이여야 합니다.")
    public String phoneNumber;
    @Positive(message = "멤버쉽은 0초과인 양수여야합니다.")
    public Long memberShip;

    @NotEmpty(message = "주소를 입력해주세요!")
    public Address address;
}
