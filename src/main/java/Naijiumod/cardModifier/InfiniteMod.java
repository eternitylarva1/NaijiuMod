/*     */ package Naijiumod.cardModifier;





/*     */ 
/*     */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.power.InfinitePower;
import Naijiumod.utils.FormatHelper;
import Naijiumod.utils.PortraitHelper;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
/*     */ import basemod.helpers.CardModifierManager;
/*     */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
/*     */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*     */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*     */ 
/*     */ public class InfiniteMod extends AbstractAugment {
/*  21 */   public static final String ID = ModHelper.makePath(InfiniteMod.class.getSimpleName());
/*  22 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*  23 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*     */   
/*     */   private boolean inherentHack = true;
/*     */ 
/*     */   
/*     */   public void onInitialApplication(AbstractCard card) {
/*  29 */     this.inherentHack = true;
/*  30 */     AbstractCard preview = card.makeStatEquivalentCopy();
/*  31 */     this.inherentHack = false;
/*  32 */     CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
/*  33 */     MultiCardPreview.add(card, new AbstractCard[] { preview });
/*  34 */     InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));
/*  35 */     card.isEthereal = false;
/*  36 */     card.cost = 1;
/*  37 */     card.costForTurn = 1;
/*  38 */     card.target = AbstractCard.CardTarget.NONE;
/*  39 */     if (card.type != AbstractCard.CardType.POWER) {
/*  40 */       card.type = AbstractCard.CardType.POWER;
/*  41 */       PortraitHelper.setMaskedPortrait(card);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validCard(AbstractCard card) {
/*  47 */     return ((card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) && noShenanigans(card) && cardCheck(card, c -> (c.cost == 0 && doesntUpgradeCost() && notExhaust(c))));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  52 */     return TEXT[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/*  57 */     return TEXT[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAugmentDescription() {
/*  62 */     return TEXT[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public String modifyDescription(String rawDescription, AbstractCard card) {
/*  67 */     return (card.isInnate ? CARD_TEXT[1] : "") + String.format(CARD_TEXT[0], new Object[] { FormatHelper.prefixWords(card.name, "*") });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpgradeCheck(AbstractCard card) {
/*  72 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  73 */       if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
/*  74 */         c.upgrade();
/*  75 */         c.initializeDescription();
/*     */       } 
/*     */     } 
/*  78 */     card.initializeDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/*  83 */     AbstractCard preview = null;
/*  84 */     for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
/*  85 */       if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
/*  86 */         preview = c;
/*     */       }
/*     */     } 
/*  89 */     if (preview != null) {
/*  90 */       AbstractCard copy = preview.makeStatEquivalentCopy();
/*  91 */       Wiz.applyToSelf((AbstractPower)new InfinitePower((AbstractCreature)AbstractDungeon.player, copy));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractAugment.AugmentRarity getModRarity() {
/*  97 */     return AbstractAugment.AugmentRarity.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCardModifier makeCopy() {
/* 102 */     return (AbstractCardModifier)new InfiniteMod();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInherent(AbstractCard card) {
/* 107 */     return this.inherentHack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(AbstractCard card) {
/* 112 */     return ID;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\InfiniteMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */