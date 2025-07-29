/*    */ package Naijiumod.cardModifier;

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.orbs.AbstractOrb;
/*    */ import com.megacrit.cardcrawl.orbs.Plasma;
/*    */ 
/*    */ public class NuclearMod extends AbstractAugment {
/* 14 */   public static final String ID = ModHelper.makePath(NuclearMod.class.getSimpleName());
/* 15 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 16 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   private static final int ORBS = 1;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 23 */     card.costForTurn = ++card.cost;
/* 24 */     card.showEvokeValue = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 29 */     return (card.cost >= 0 && allowOrbMods() && card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.COMMON && cardCheck(card, c -> doesntUpgradeCost()));
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
/* 49 */     return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(1) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 54 */     addToBot((AbstractGameAction)new ChannelAction((AbstractOrb)new Plasma()));
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 59 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 64 */     return (AbstractCardModifier)new NuclearMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 69 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\NuclearMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */