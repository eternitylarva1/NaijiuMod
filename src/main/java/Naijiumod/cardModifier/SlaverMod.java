/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class SlaverMod extends AbstractAugment {
/* 12 */   public static final String ID = ModHelper.makePath(SlaverMod.class.getSimpleName());
/* 13 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 14 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public boolean atBattleStartPreDraw(AbstractCard card) {
/* 18 */     boolean activated = (AbstractDungeon.getCurrRoom()).eliteTrigger;
/* 19 */     for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
/* 20 */       if (m.type == AbstractMonster.EnemyType.BOSS) {
/* 21 */         activated = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 25 */     if (activated) {
/* 26 */       card.cost--;
/* 27 */       if (card.cost < 0) {
/* 28 */         card.cost = 0;
/*    */       }
/* 30 */       card.costForTurn = card.cost;
/* 31 */       card.isCostModified = true;
/*    */     } 
/* 33 */     return activated;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCreatedMidCombat(AbstractCard card) {
/* 38 */     atBattleStartPreDraw(card);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 43 */     return (card.cost > 0 && cardCheck(card, c -> doesntUpgradeCost()));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 48 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 53 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 58 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 63 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 68 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 73 */     return (AbstractCardModifier)new SlaverMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 78 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\SlaverMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */