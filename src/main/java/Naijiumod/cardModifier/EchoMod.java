/*    */ package Naijiumod.cardModifier;
/*    */ 


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.EchoFieldPatches;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class EchoMod extends AbstractAugment {
/* 11 */   public static final String ID = ModHelper.makePath(EchoMod.class.getSimpleName());
/* 12 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 13 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private static final int AMOUNT = 1;
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 18 */     EchoFieldPatches.EchoFields.echo.set(card, Integer.valueOf(((Integer)EchoFieldPatches.EchoFields.echo.get(card)).intValue() + 1));
/* 19 */     card.exhaust = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 24 */     return (card.cost != -2 && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) && cardCheck(card, AbstractAugment::notExhaust));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 29 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 34 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 39 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 44 */     return rawDescription + CARD_TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 49 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 54 */     return (AbstractCardModifier)new EchoMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 59 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\event\EchoMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */