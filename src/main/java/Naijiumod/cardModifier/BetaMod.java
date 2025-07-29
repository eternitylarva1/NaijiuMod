/*     */ package Naijiumod.cardModifier;




/*     */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.FormatHelper;
import Naijiumod.utils.PortraitHelper;
import basemod.abstracts.AbstractCardModifier;
/*     */ import basemod.helpers.CardModifierManager;
/*     */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
/*     */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*     */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
/*     */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ 
/*     */ public class BetaMod extends AbstractAugment {
/*  18 */   public static final String ID = ModHelper.makePath(BetaMod.class.getSimpleName());
/*  19 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*  20 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*     */   
/*     */   private boolean inherentHack = true;
/*     */ 
/*     */   
/*     */   public void onInitialApplication(AbstractCard card) {
/*  26 */     AbstractCard preview = card.makeStatEquivalentCopy();
/*  27 */     this.inherentHack = false;
/*  28 */     CardModifierManager.addModifier(preview, (AbstractCardModifier)new OmegaMod());
/*  29 */     MultiCardPreview.add(card, new AbstractCard[] { preview });
/*  30 */     InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));
/*  31 */     card.cost--;
/*  32 */     card.costForTurn--;
/*  33 */     if (card.cost < 0) {
/*  34 */       card.cost = 0;
/*     */     }
/*  36 */     if (card.costForTurn < 0) {
/*  37 */       card.costForTurn = 0;
/*     */     }
/*  39 */     card.isEthereal = false;
/*  40 */     card.exhaust = true;
/*  41 */     card.target = AbstractCard.CardTarget.NONE;
/*  42 */     if (card.type != AbstractCard.CardType.SKILL) {
/*  43 */       card.type = AbstractCard.CardType.SKILL;
/*  44 */       PortraitHelper.setMaskedPortrait(card);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validCard(AbstractCard card) {
/*  50 */     return (noShenanigans(card) && cardCheck(card, c -> (c.cost >= 2 && doesntUpgradeCost() && (c.baseDamage > 0 || c.baseBlock > 0 || doesntDowngradeMagic()))));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  55 */     return TEXT[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/*  60 */     return TEXT[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAugmentDescription() {
/*  65 */     return TEXT[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public String modifyDescription(String rawDescription, AbstractCard card) {
/*  70 */     return String.format(CARD_TEXT[0], new Object[] { FormatHelper.prefixWords(card.name, "*") }) + ((card.type == AbstractCard.CardType.POWER) ? "" : CARD_TEXT[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpgradeCheck(AbstractCard card) {
/*  75 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  76 */       if (CardModifierManager.hasModifier(c, OmegaMod.ID)) {
/*  77 */         c.upgrade();
/*  78 */         c.initializeDescription();
/*     */       } 
/*     */     } 
/*  81 */     card.initializeDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/*  86 */     AbstractCard preview = null;
/*  87 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  88 */       if (CardModifierManager.hasModifier(c, OmegaMod.ID)) {
/*  89 */         preview = c;
/*     */       }
/*     */     } 
/*  92 */     if (preview != null) {
/*  93 */       AbstractCard copy = preview.makeStatEquivalentCopy();
/*  94 */       addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction(copy, 1, true, true));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractAugment.AugmentRarity getModRarity() {
/* 107 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCardModifier makeCopy() {
/* 112 */     return (AbstractCardModifier)new BetaMod();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInherent(AbstractCard card) {
/* 117 */     return this.inherentHack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(AbstractCard card) {
/* 122 */     return ID;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmod\\util\BetaMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */