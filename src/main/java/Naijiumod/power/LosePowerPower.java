package Naijiumod.power;


import Naijiumod.helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LosePowerPower extends AbstractPower {
  public static String TEXT_ID = ModHelper.makePath("LosePowerPower");
  
  public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(TEXT_ID);
  
  private AbstractPower powerToLose;
  
  public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount) {
    this.img = powerToLose.img;
    this.region48 = powerToLose.region48;
    this.region128 = powerToLose.region128;
    this.powerToLose = powerToLose;
    updateDescription();
  }
  
  public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
    super.renderIcons(sb, x, y, Color.RED.cpy());
  }
  
  public void atEndOfTurn(boolean isPlayer) {
    flash();
    addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this.powerToLose.ID, this.amount));
    addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
  }
  
  public void updateDescription() {
    if (this.powerToLose == null) {
      this.description = "???";
    } else {
      this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1] + this.powerToLose.name + strings.DESCRIPTIONS[2];
    } 
  }
}
