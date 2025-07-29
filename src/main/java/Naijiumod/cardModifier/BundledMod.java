/*     */ package Naijiumod.cardModifier;
/*     */ 




/*     */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.FormatHelper;
import Naijiumod.utils.PortraitHelper;
import basemod.abstracts.AbstractCardModifier;
/*     */ import basemod.helpers.CardModifierManager;
/*     */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
/*     */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*     */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
/*     */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ 
/*     */ public class BundledMod
/*     */   extends AbstractAugment
/*     */ {
/*  22 */   public static final String ID = ModHelper.makePath(BundledMod.class.getSimpleName());
/*  23 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*  24 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*     */   
/*     */   private boolean inherentHack = true;
/*     */ 
/*     */   
/*     */   public void onInitialApplication(AbstractCard card) {
/*  30 */     this.inherentHack = true;
/*  31 */     AbstractCard preview = card.makeStatEquivalentCopy();
/*  32 */     this.inherentHack = false;
/*  33 */     CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
/*  34 */     MultiCardPreview.add(card, new AbstractCard[] { preview });
/*  35 */     InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));
/*  36 */     card.isEthereal = false;
/*  37 */     card.exhaust = true;
/*  38 */     card.cost = 0;
/*  39 */     card.costForTurn = 0;
/*  40 */     card.target = AbstractCard.CardTarget.NONE;
/*  41 */     if (card.type != AbstractCard.CardType.SKILL) {
/*  42 */       card.type = AbstractCard.CardType.SKILL;
/*  43 */       PortraitHelper.setMaskedPortrait(card);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validCard(AbstractCard card) {
/*  49 */     return (isNormalCard(card) && noShenanigans(card) && cardCheck(card, c -> (c.cost == 1 && doesntUpgradeCost())));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  54 */     return TEXT[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/*  59 */     return TEXT[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAugmentDescription() {
/*  64 */     return TEXT[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public String modifyDescription(String rawDescription, AbstractCard card) {
/*  69 */     return (card.isInnate ? CARD_TEXT[2] : "") + String.format(CARD_TEXT[0], new Object[] { FormatHelper.prefixWords(card.name, "*") }) + ((card.type == AbstractCard.CardType.POWER) ? "" : CARD_TEXT[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpgradeCheck(AbstractCard card) {
/*  74 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  75 */       if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
/*  76 */         c.upgrade();
/*  77 */         c.initializeDescription();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     card.initializeDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/*  89 */     AbstractCard preview = null;
/*  90 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  91 */       if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
/*  92 */         preview = c;
/*     */       }
/*     */     } 
/*  95 */     if (preview != null) {
/*  96 */       AbstractCard copy = preview.makeStatEquivalentCopy();
/*  97 */       addToBot((AbstractGameAction)new MakeTempCardInHandAction(copy, 2));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractAugment.AugmentRarity getModRarity() {
/* 103 */     return AbstractAugment.AugmentRarity.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCardModifier makeCopy() {
/* 108 */     return (AbstractCardModifier)new BundledMod();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInherent(AbstractCard card) {
/* 113 */     return this.inherentHack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(AbstractCard card) {
/* 118 */     return ID;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\BundledMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */