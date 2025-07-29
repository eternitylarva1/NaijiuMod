package Naijiumod.utils;


import Naijiumod.helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NextTurnPowerPower extends AbstractPower {
    public static String TEXT_ID = ModHelper.makePath("NextTurnPowerPower");

    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(TEXT_ID);

    private AbstractPower powerToGain;

    public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
        super();
        this.img = powerToGrant.img;
        this.region48 = powerToGrant.region48;
        this.region128 = powerToGrant.region128;
        this.powerToGain = powerToGrant;
        updateDescription();
    }

    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.GREEN.cpy());
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.powerToGain.amount += stackAmount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, this.powerToGain, this.powerToGain.amount));
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        if (this.powerToGain == null) {
            this.description = "???";
        } else {
            this.description = strings.DESCRIPTIONS[0] + this.powerToGain.amount + strings.DESCRIPTIONS[1] + this.powerToGain.name + strings.DESCRIPTIONS[2];
        }
    }
}
