/*    */ package Naijiumod.power;

/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.utils.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.localization.PowerStrings;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ 
/*    */ public class RunicPower extends AbstractPower implements CloneablePowerInterface {
/* 14 */   public static final String POWER_ID = ModHelper.makePath(RunicPower.class.getSimpleName());
/* 15 */   private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
/*    */   
/*    */   public RunicPower(AbstractCreature owner, int amount) {
/* 18 */     this.name = TEXT.NAME;
/* 19 */     this.ID = POWER_ID;
/*    */     
/* 21 */     this.owner = owner;
/* 22 */     this.amount = amount;
/*    */     
/* 24 */     this.type = PowerType.DEBUFF;
/* 25 */     this.isTurnBased = true;
/*    */     
/* 27 */     this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture("CardAugmentsResources/images/powers/blahblah84.png"), 0, 0, 84, 84);
/* 28 */     this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture("CardAugmentsResources/images/powers/blahblah32.png"), 0, 0, 32, 32);
/*    */     
/* 30 */     updateDescription();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateDescription() {
/* 35 */     this.description = TEXT.DESCRIPTIONS[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public void atEndOfTurn(boolean isPlayer) {
/* 40 */     if (isPlayer) {
/* 41 */       addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this, 1));
/*    */     }
/*    */   }
/*    */   
/*    */   public AbstractPower makeCopy() {
/* 46 */     return new RunicPower(this.owner, this.amount);
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\powers\RunicPower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */