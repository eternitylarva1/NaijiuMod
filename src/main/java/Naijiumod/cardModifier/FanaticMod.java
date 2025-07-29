/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class FanaticMod extends AbstractAugment {
/* 12 */   public static final String ID = ModHelper.makePath(FanaticMod.class.getSimpleName());
/* 13 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 14 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private boolean addedExhaust;
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 19 */     card.exhaust = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 24 */     return damage * 1.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 29 */     return (card.type == AbstractCard.CardType.ATTACK && cardCheck(card, AbstractAugment::notExhaust));
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
/* 49 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 54 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 59 */     return (AbstractCardModifier)new FanaticMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 64 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\event\FanaticMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */