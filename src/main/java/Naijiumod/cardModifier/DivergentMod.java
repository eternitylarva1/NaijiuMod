/*    */ package Naijiumod.cardModifier;
/*    */ 


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.EchoFieldPatches;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class DivergentMod extends AbstractAugment {
/* 13 */   public static final String ID = ModHelper.makePath(DivergentMod.class.getSimpleName());
/* 14 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 15 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private static final int AMOUNT = 1;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 21 */     EchoFieldPatches.EchoFields.echo.set(card, Integer.valueOf(((Integer) EchoFieldPatches.EchoFields.echo.get(card)).intValue() + 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 26 */     if (card.baseDamage > 1) {
/* 27 */       return damage * 0.5F;
/*    */     }
/* 29 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseBlock(float block, AbstractCard card) {
/* 34 */     if (card.baseBlock > 1) {
/* 35 */       return block * 0.5F;
/*    */     }
/* 37 */     return block;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 42 */     return (card.cost != -2 && (card.baseDamage > 1 || card.baseBlock > 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 47 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 52 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 57 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 62 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 67 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 72 */     return (AbstractCardModifier)new DivergentMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 77 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\DivergentMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */