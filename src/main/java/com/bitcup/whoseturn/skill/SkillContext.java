package com.bitcup.whoseturn.skill;

/**
 * @author bitcup
 */
public class SkillContext {
    private boolean needsMoreHelp = true;

    public boolean needsMoreHelp() {
        return needsMoreHelp;
    }

    public void setNeedsMoreHelp(boolean needsMoreHelp) {
        this.needsMoreHelp = needsMoreHelp;
    }
}
