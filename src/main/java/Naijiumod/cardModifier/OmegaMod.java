/*    */ package Naijiumod.cardModifier;
/*    */ 


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class OmegaMod extends AbstractAugment {
/* 13 */   public static final String ID = ModHelper.makePath(OmegaMod.class.getSimpleName());
/* 14 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 15 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private static final float multiplier = 3.0F;
/*    */   
/*    */   boolean modMagic;
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 22 */     if (cardCheck(card, c -> doesntDowngradeMagic())) {
/* 23 */       this.modMagic = true;
/*    */     }
/* 25 */     InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 30 */     if (card.baseDamage > 0) {
/* 31 */       return damage * 3.0F;
/*    */     }
/* 33 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseBlock(float block, AbstractCard card) {
/* 38 */     if (card.baseBlock > 0) {
/* 39 */       return block * 3.0F;
/*    */     }
/* 41 */     return block;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseMagic(float magic, AbstractCard card) {
/* 46 */     if (this.modMagic) {
/* 47 */       return magic * 3.0F;
/*    */     }
/* 49 */     return magic;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 54 */     return (noShenanigans(card) && cardCheck(card, c -> (c.cost >= 2 && doesntUpgradeCost() && (c.baseDamage > 0 || c.baseBlock > 0 || doesntDowngradeMagic()))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 59 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 64 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 69 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 74 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 79 */     return (AbstractCardModifier)new OmegaMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 84 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmod\\util\OmegaMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */