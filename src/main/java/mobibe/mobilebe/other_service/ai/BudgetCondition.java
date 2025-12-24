package mobibe.mobilebe.other_service.ai;

import mobibe.mobilebe.other_service.ai.enumuration.BudgetType;

public class BudgetCondition {

    private BudgetType type; // UNDER / OVER / BETWEEN
    private Integer min;
    private Integer max;

    public BudgetCondition(BudgetType type, Integer min, Integer max) {
        this.type = type;
        this.min = min;
        this.max = max;
    }

    public BudgetType getType() {
        return type;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }
}
