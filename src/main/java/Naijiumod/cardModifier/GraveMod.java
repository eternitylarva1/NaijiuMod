/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class GraveMod extends AbstractAugment {
/* 11 */   public static final String ID = ModHelper.makePath(GraveMod.class.getSimpleName());
/* 12 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 13 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 17 */     GraveField.grave.set(card, Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 22 */     return !((Boolean)GraveField.grave.get(card)).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 27 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 32 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 37 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 42 */     return rawDescription + CARD_TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 47 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 52 */     return (AbstractCardModifier)new GraveMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 57 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\event\GraveMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */