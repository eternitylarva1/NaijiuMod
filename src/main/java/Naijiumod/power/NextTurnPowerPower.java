/*    */ package Naijiumod.power;

/*    */ import Naijiumod.helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
/*    */ import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.localization.PowerStrings;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ 
/*    */ public class NextTurnPowerPower extends AbstractPower {
/* 14 */   public static String TEXT_ID = ModHelper.makePath("NextTurnPowerPower");
/* 15 */   public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(TEXT_ID);
/*    */   private AbstractPower powerToGain;
/*    */   
/*    */   public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
/* 19 */     super();
/* 20 */     this.img = powerToGrant.img;
/* 21 */     this.region48 = powerToGrant.region48;
/* 22 */     this.region128 = powerToGrant.region128;
/* 23 */     this.powerToGain = powerToGrant;
/* 24 */     updateDescription();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
/* 29 */     super.renderIcons(sb, x, y, Color.GREEN.cpy());
/*    */   }
/*    */ 
/*    */   
/*    */   public void stackPower(int stackAmount) {
/* 34 */     super.stackPower(stackAmount);
/* 35 */     this.powerToGain.amount += stackAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   public void atEndOfTurn(boolean isPlayer) {
/* 40 */     flash();
/* 41 */     addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, this.powerToGain, this.powerToGain.amount));
/* 42 */     addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateDescription() {
/* 47 */     if (this.powerToGain == null) {
/* 48 */       this.description = "???";
/*    */     } else {
/* 50 */       this.description = strings.DESCRIPTIONS[0] + this.powerToGain.amount + strings.DESCRIPTIONS[1] + this.powerToGain.name + strings.DESCRIPTIONS[2];
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\powers\NextTurnPowerPower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */