package dat.dtos;

import lombok.Data;

@Data
public class GuideTotalPriceDTO {
    private Long guideId;
    private Double totalPrice;

    public GuideTotalPriceDTO(Long guideId, Double totalPrice) {
        this.guideId = guideId;
        this.totalPrice = totalPrice;
    }
}
