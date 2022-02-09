package com.puresoftware.quickmemo.artifacts;

public class HomeDO {

    private String leftTitle;
    private String rightTitle;

    public HomeDO() {

    }

    public HomeDO(String leftTitle, String rightTitle) {
        this.leftTitle = leftTitle;
        this.rightTitle = rightTitle;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
    }
}
