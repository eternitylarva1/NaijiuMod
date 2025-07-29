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
/*     */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
/*     */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ 
/*     */ public class AlphaMod extends AbstractAugment {
/*  21 */   public static final String ID = ModHelper.makePath(AlphaMod.class.getSimpleName());
/*  22 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*  23 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*     */   
/*     */   private boolean inherentHack = true;
/*     */ 
/*     */   
/*     */   public void onInitialApplication(AbstractCard card) {
/*  29 */     AbstractCard preview1 = card.makeStatEquivalentCopy();
/*  30 */     AbstractCard preview2 = card.makeStatEquivalentCopy();
/*  31 */     this.inherentHack = false;
/*  32 */     CardModifierManager.addModifier(preview1, (AbstractCardModifier)new BetaMod());
/*  33 */     CardModifierManager.addModifier(preview2, (AbstractCardModifier)new OmegaMod());
/*  34 */     MultiCardPreview.add(card, new AbstractCard[] { preview1, preview2 });
/*  35 */     InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));
/*  36 */     card.cost -= 2;
/*  37 */     card.costForTurn -= 2;
/*  38 */     if (card.cost < 0) {
/*  39 */       card.cost = 0;
/*     */     }
/*  41 */     if (card.costForTurn < 0) {
/*  42 */       card.costForTurn = 0;
/*     */     }
/*  44 */     card.isEthereal = false;
/*  45 */     card.exhaust = true;
/*  46 */     card.target = AbstractCard.CardTarget.NONE;
/*  47 */     if (card.type != AbstractCard.CardType.SKILL) {
/*  48 */       card.type = AbstractCard.CardType.SKILL;
/*  49 */       PortraitHelper.setMaskedPortrait(card);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validCard(AbstractCard card) {
/*  55 */     return (noShenanigans(card) && cardCheck(card, c -> (c.cost >= 2 && doesntUpgradeCost() && (c.baseDamage > 0 || c.baseBlock > 0 || doesntDowngradeMagic()))));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  60 */     return TEXT[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/*  65 */     return TEXT[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAugmentDescription() {
/*  70 */     return TEXT[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public String modifyDescription(String rawDescription, AbstractCard card) {
/*  75 */     return (card.isInnate ? CARD_TEXT[2] : "") + String.format(CARD_TEXT[0], new Object[] { FormatHelper.prefixWords(card.name, "*") }) + ((card.type == AbstractCard.CardType.POWER) ? "" : CARD_TEXT[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpgradeCheck(AbstractCard card) {
/*  80 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  81 */       if (CardModifierManager.hasModifier(c, BetaMod.ID) || CardModifierManager.hasModifier(c, OmegaMod.ID)) {
/*  82 */         c.upgrade();
/*  83 */         c.initializeDescription();
/*     */       } 
/*     */     } 
/*  86 */     card.initializeDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/*  91 */     AbstractCard preview = null;
/*  92 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  93 */       if (CardModifierManager.hasModifier(c, BetaMod.ID)) {
/*  94 */         preview = c;
/*     */       }
/*     */     } 
/*  97 */     if (preview != null) {
/*  98 */       AbstractCard copy = preview.makeStatEquivalentCopy();
/*  99 */       addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction(copy, 1, true, true));
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
/* 112 */     return AbstractAugment.AugmentRarity.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCardModifier makeCopy() {
/* 117 */     return (AbstractCardModifier)new AlphaMod();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInherent(AbstractCard card) {
/* 122 */     return this.inherentHack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(AbstractCard card) {
/* 127 */     return ID;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\AlphaMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */