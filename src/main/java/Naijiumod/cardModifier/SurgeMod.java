/*    */ package Naijiumod.cardModifier;

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ import com.megacrit.cardcrawl.powers.ArtifactPower;
/*    */ 
/*    */ public class SurgeMod extends AbstractAugment {
/* 15 */   public static final String ID = ModHelper.makePath(SurgeMod.class.getSimpleName());
/* 16 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 17 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private static final int AMOUNT = 1;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 23 */     card.exhaust = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 28 */     return (card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.COMMON && card.cost != -2 && card.type != AbstractCard.CardType.POWER && cardCheck(card, AbstractAugment::notExhaust));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 33 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 38 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 43 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 48 */     return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(1) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 53 */     addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new ArtifactPower((AbstractCreature)AbstractDungeon.player, 1)));
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 58 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 63 */     return (AbstractCardModifier)new SurgeMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 68 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\SurgeMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */