/*    */ package Naijiumod.cardModifier;


/*    */ import Naijiumod.action.AutoplayOnRandomEnemyAction;
import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.DrawCardAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class AutoMod extends AbstractAugment {
/* 15 */   public static final String ID = ModHelper.makePath(AutoMod.class.getSimpleName());
/* 16 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 17 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   public static final int DRAW = 1;
/*    */ 
/*    */   
/*    */   public void onDrawn(AbstractCard card) {
/* 23 */     addToBot((AbstractGameAction)new AutoplayOnRandomEnemyAction(card));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 28 */     addToBot((AbstractGameAction)new DrawCardAction(1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 33 */     return (!((Boolean)AutoplayField.autoplay.get(card)).booleanValue() && card.cost >= 0 && cardCheck(card, AbstractAugment::notRetain));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 38 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 43 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 48 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 53 */     return CARD_TEXT[0] + insertAfterText(rawDescription, String.format(CARD_TEXT[1], new Object[] { Integer.valueOf(1) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 58 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 63 */     return (AbstractCardModifier)new AutoMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 68 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\event\AutoMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */