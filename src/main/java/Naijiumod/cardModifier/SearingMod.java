/*    */ package Naijiumod.cardModifier;
/*    */ 


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InfiniteUpgradesPatches;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class SearingMod
/*    */   extends AbstractAugment
/*    */ {
/* 13 */   public static final String ID = ModHelper.makePath(SearingMod.class.getSimpleName());
/* 14 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 15 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 19 */     InfiniteUpgradesPatches.InfUpgradeField.inf.set(card, Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 24 */     return ((card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL) && card.canUpgrade() && cardCheck(card, c -> upgradesAVariable()) && doesntOverride(card, "canUpgrade", new Class[0]) && !(card instanceof com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard) && !(card instanceof com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 29 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 34 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 39 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 44 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 49 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 54 */     return (AbstractCardModifier)new SearingMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 59 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\SearingMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */