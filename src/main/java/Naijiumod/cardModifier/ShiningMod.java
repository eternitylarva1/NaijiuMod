/*    */ package Naijiumod.cardModifier;


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class ShiningMod extends AbstractAugment {
/* 12 */   public static final String ID = ModHelper.makePath(ShiningMod.class.getSimpleName());
/* 13 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 14 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 18 */     card.isEthereal = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 23 */     return (isNormalCard(card) && cardCheck(card, AbstractAugment::notEthereal));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDrawn(AbstractCard card) {
/* 28 */     card.superFlash();
/* 29 */     Wiz.atb((AbstractGameAction)new GainEnergyAction(1));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 34 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 39 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 44 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 49 */     return CARD_TEXT[0] + insertAfterText(rawDescription, CARD_TEXT[1]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 54 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 59 */     return (AbstractCardModifier)new ShiningMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 64 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\event\ShiningMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */