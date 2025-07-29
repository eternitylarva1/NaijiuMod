/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class FormMod extends AbstractAugment {
/* 10 */   public static final String ID = ModHelper.makePath(FormMod.class.getSimpleName());
/* 11 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*    */   
/*    */   private int baseCost;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 17 */     this.baseCost = Math.max(1, card.cost);
/* 18 */     card.cost = 3;
/* 19 */     card.costForTurn = card.cost;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseMagic(float magic, AbstractCard card) {
/* 24 */     return magic * 3.0F / this.baseCost;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 29 */     return (card.type == AbstractCard.CardType.POWER && card.cost >= 0 && card.cost < 3 && cardCheck(card, c -> (doesntUpgradeCost() && doesntDowngradeMagic() && c.baseMagicNumber > 0 && card.cost <= c.baseMagicNumber)));
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
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 49 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 54 */     return (AbstractCardModifier)new FormMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 59 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\FormMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */