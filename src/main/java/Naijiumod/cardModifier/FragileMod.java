/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class FragileMod extends AbstractAugment {
/* 10 */   public static final String ID = ModHelper.makePath(FragileMod.class.getSimpleName());
/* 11 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 12 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 16 */     card.cost--;
/* 17 */     if (card.cost < 0) {
/* 18 */       card.cost = 0;
/*    */     }
/* 20 */     card.costForTurn = card.cost;
/* 21 */     card.exhaust = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 26 */     return (card.cost > 0 && card.type != AbstractCard.CardType.POWER && card.type != AbstractCard.CardType.CURSE && cardCheck(card, c -> (doesntUpgradeCost() && doesntExhaust(c))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 31 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 36 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 41 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 46 */     return rawDescription + CARD_TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 51 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 56 */     return (AbstractCardModifier)new FragileMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 61 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\FragileMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */