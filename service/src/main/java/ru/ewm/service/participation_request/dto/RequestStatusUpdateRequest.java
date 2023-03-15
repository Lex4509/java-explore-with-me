package ru.ewm.service.participation_request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ewm.service.util.enums.RequestStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    private RequestStatus status;

    @Override
    public String toString() {
        return "RequestStatusUpdateRequest{" +
                "requestIds=" + requestIds +
                ", status=" + status +
                '}';
    }
}
