/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class StickyMod extends AbstractAugment {
/* 10 */   public static final String ID = ModHelper.makePath(StickyMod.class.getSimpleName());
/* 11 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 12 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 16 */     card.selfRetain = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 21 */     return (card.type != AbstractCard.CardType.CURSE && cardCheck(card, AbstractAugment::notRetain) && doesntOverride(card, "triggerOnEndOfTurnForPlayingCard", new Class[0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 26 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 31 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 36 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 41 */     return CARD_TEXT[0] + rawDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 46 */     return AbstractAugment.AugmentRarity.COMMON;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 51 */     return (AbstractCardModifier)new StickyMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 56 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\common\StickyMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */