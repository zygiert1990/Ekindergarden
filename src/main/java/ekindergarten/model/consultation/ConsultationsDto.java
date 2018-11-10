package ekindergarten.model.consultation;

import ekindergarten.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ConsultationsDto {
    private final UserDto user;
    private final List<ConsultationDayDto> consultationDay;
}
