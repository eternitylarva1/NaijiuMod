/*     */ package Naijiumod.cardModifier;
/*     */ 

/*     */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ 
/*     */ public class JankMod extends AbstractAugment {
/*  10 */   public static final String ID = ModHelper.makePath(JankMod.class.getSimpleName());
/*  11 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*     */   
/*  13 */   public int upDamage = -1;
/*  14 */   public int upBlock = -1;
/*  15 */   public int upMagic = -1;
/*     */ 
/*     */   
/*     */   public void onInitialApplication(AbstractCard card) {
/*  19 */     boolean hasDamage = (card.baseDamage > 0);
/*  20 */     boolean hasBlock = (card.baseBlock > 0);
/*  21 */     int d = card.baseDamage;
/*  22 */     int b = card.baseBlock;
/*  23 */     int m = card.baseMagicNumber;
/*  24 */     if (hasDamage && hasBlock) {
/*  25 */       card.baseDamage = m;
/*  26 */       card.baseBlock = d;
/*  27 */       card.baseMagicNumber = b;
/*  28 */       card.magicNumber = card.baseMagicNumber;
/*  29 */     } else if (hasDamage) {
/*  30 */       card.baseDamage = m;
/*  31 */       card.baseMagicNumber = d;
/*  32 */       card.magicNumber = card.baseMagicNumber;
/*     */     } else {
/*  34 */       card.baseBlock = m;
/*  35 */       card.baseMagicNumber = b;
/*  36 */       card.magicNumber = card.baseMagicNumber;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validCard(AbstractCard card) {
/*  42 */     return ((card.baseDamage > 0 || card.baseBlock > 0) && cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpgradeCheck(AbstractCard card) {
/*  47 */     super.onUpgradeCheck(card);
/*  48 */     boolean hasDamage = (card.baseDamage > 0);
/*  49 */     boolean hasBlock = (card.baseBlock > 0);
/*  50 */     if (hasDamage && hasBlock) {
/*  51 */       if (this.upDamage > 0) {
/*  52 */         card.baseBlock += this.upDamage;
/*  53 */         card.upgradedBlock = true;
/*     */       } 
/*  55 */       if (this.upBlock > 0) {
/*  56 */         card.baseMagicNumber += this.upBlock;
/*  57 */         card.magicNumber = card.baseMagicNumber;
/*  58 */         card.upgradedMagicNumber = true;
/*     */       } 
/*  60 */       if (this.upMagic > 0) {
/*  61 */         card.baseDamage += this.upMagic;
/*  62 */         card.upgradedBlock = true;
/*     */       } 
/*  64 */     } else if (hasDamage) {
/*  65 */       if (this.upDamage > 0) {
/*  66 */         card.baseMagicNumber += this.upDamage;
/*  67 */         card.magicNumber = card.baseMagicNumber;
/*  68 */         card.upgradedMagicNumber = true;
/*     */       } 
/*  70 */       if (this.upMagic > 0) {
/*  71 */         card.baseDamage += this.upMagic;
/*  72 */         card.upgradedDamage = true;
/*     */       } 
/*     */     } else {
/*  75 */       if (this.upBlock > 0) {
/*  76 */         card.baseMagicNumber += this.upBlock;
/*  77 */         card.magicNumber = card.baseMagicNumber;
/*  78 */         card.upgradedMagicNumber = true;
/*     */       } 
/*  80 */       if (this.upMagic > 0) {
/*  81 */         card.baseBlock += this.upMagic;
/*  82 */         card.upgradedBlock = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  89 */     return TEXT[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/*  94 */     return TEXT[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAugmentDescription() {
/*  99 */     return TEXT[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractAugment.AugmentRarity getModRarity() {
/* 104 */     return AbstractAugment.AugmentRarity.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCardModifier makeCopy() {
/* 109 */     return (AbstractCardModifier)new JankMod();
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(AbstractCard card) {
/* 114 */     return ID;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\JankMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */