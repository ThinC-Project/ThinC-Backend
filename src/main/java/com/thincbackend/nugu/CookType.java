package com.thincbackend.nugu;

public enum CookType {

    DRY("식품건조"),
    OVEN("오븐"),
    STEAM("스팀"),
    AIRFRY("에어프라이"),
    SLOWCOOK("슬로우쿡"),
    FERMENTATION("발효"),
    RANGE("레인지"),
    ROAST("구이"),
    TOAST("토스트"),
    INDUCTION("인덕션");
    private String type;

    CookType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
