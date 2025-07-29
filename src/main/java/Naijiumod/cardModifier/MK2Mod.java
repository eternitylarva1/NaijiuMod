/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class MK2Mod extends AbstractAugment {
/* 12 */   public static final String ID = ModHelper.makePath(MK2Mod.class.getSimpleName());
/* 13 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*    */   
/*    */   boolean modMagic;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 19 */     if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber >= 5))) {
/* 20 */       this.modMagic = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 26 */     if (card.baseDamage > 0) {
/* 27 */       return damage * 1.2F;
/*    */     }
/* 29 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseBlock(float block, AbstractCard card) {
/* 34 */     if (card.baseBlock > 0) {
/* 35 */       return block * 1.2F;
/*    */     }
/* 37 */     return block;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseMagic(float magic, AbstractCard card) {
/* 42 */     if (this.modMagic) {
/* 43 */       return magic * 1.2F;
/*    */     }
/* 45 */     return magic;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 50 */     return (card.baseDamage >= 5 || card.baseBlock >= 5 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber >= 5)));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 55 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 60 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 65 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 70 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 75 */     return (AbstractCardModifier)new MK2Mod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 80 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\MK2Mod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */