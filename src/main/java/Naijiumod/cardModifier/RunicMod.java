/*    */ package Naijiumod.cardModifier;


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.power.RunicPower;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ 
/*    */ public class RunicMod extends AbstractAugment {
/* 15 */   public static final String ID = ModHelper.makePath(RunicMod.class.getSimpleName());
/* 16 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 17 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   public static final int DURATION = 2;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 23 */     card.cost--;
/* 24 */     if (card.cost < 0) {
/* 25 */       card.cost = 0;
/*    */     }
/* 27 */     card.costForTurn = card.cost;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 32 */     return (card.cost > 0 && cardCheck(card, c -> doesntUpgradeCost()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 37 */     addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RunicPower((AbstractCreature)AbstractDungeon.player, 2), 2));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 42 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 47 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 52 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 57 */     return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(2) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 62 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 67 */     return (AbstractCardModifier)new RunicMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 72 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\RunicMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */