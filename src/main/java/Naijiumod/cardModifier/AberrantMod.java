/*    */ package Naijiumod.cardModifier;


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.utils.Wiz;
import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
/*    */ 
/*    */ public class AberrantMod extends AbstractAugment {
/* 14 */   public static final String ID = ModHelper.makePath(AberrantMod.class.getSimpleName());
/* 15 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 16 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 19 */     card.exhaust = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 24 */     return (card.cost > -2 && cardCheck(card, AbstractAugment::notExhaust) && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 29 */     Wiz.applyToSelf((AbstractPower)new IntangiblePlayerPower((AbstractCreature)Wiz.adp(), 1));
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
/* 49 */     return rawDescription + CARD_TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 54 */     return AbstractAugment.AugmentRarity.SPECIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 59 */     return (AbstractCardModifier)new AberrantMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 64 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\event\AberrantMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */