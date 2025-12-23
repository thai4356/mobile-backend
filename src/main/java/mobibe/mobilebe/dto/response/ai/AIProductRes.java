package mobibe.mobilebe.dto.response.ai;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class AIProductRes {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String link;

    public AIProductRes(Integer id, String link, String name, BigDecimal price) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.price = price;
    }

    public AIProductRes() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}