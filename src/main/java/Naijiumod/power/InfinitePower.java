/*    */ package Naijiumod.power;

/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.utils.FormatHelper;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.localization.PowerStrings;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ 
/*    */ public class InfinitePower extends AbstractPower implements NonStackablePower {
/* 14 */   public static final String POWER_ID = ModHelper.makePath(InfinitePower.class.getSimpleName());
/* 15 */   private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
/*    */   private final AbstractCard card;
/*    */   
/*    */   public InfinitePower(AbstractCreature owner, AbstractCard card) {
/* 19 */     this.name = TEXT.NAME + card.name;
/* 20 */     this.ID = POWER_ID;
/* 21 */     this.owner = owner;
/* 22 */     this.card = card.makeStatEquivalentCopy();
/* 23 */     this.type = PowerType.BUFF;
/* 24 */     this.isTurnBased = false;
/* 25 */     loadRegion("infiniteBlades");
/* 26 */     updateDescription();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateDescription() {
/* 31 */     this.description = String.format(TEXT.DESCRIPTIONS[0], new Object[] { FormatHelper.prefixWords(this.card.name, "#y") });
/*    */   }
/*    */ 
/*    */   
/*    */   public void atStartOfTurnPostDraw() {
/* 36 */     addToBot((AbstractGameAction)new MakeTempCardInHandAction(this.card.makeStatEquivalentCopy()));
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\powers\InfinitePower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */