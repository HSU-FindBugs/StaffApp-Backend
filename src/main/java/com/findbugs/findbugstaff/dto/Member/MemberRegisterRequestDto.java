package com.findbugs.findbugstaff.dto.Member;

import com.findbugs.findbugstaff.domain.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
public class MemberRegisterRequestDto {
    @Positive(message = "staff아이디는 0초과인 양수여야합니다.")
    public Long staffId;

    @NotEmpty(message = "이름을 작성해주세요")
    public String name;

    public String email;
    @Pattern(regexp = "^(010-\\d{4}-\\d{4})$", message = "전화번호는 010-0000-0000 형식이여야 합니다.")
    public String phoneNumber;
    @Positive(message = "멤버쉽은 0초과인 양수여야합니다.")
    public Long memberShip;
    @NotNull(message = "주소를 입력해주세요!")
    public Address address;

}
