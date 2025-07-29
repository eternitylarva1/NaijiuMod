/*    */ package Naijiumod.cardModifier;
/*    */ 


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
/*    */ 
/*    */ public class XMod
/*    */   extends AbstractAugment {
/* 17 */   public static final String ID = ModHelper.makePath(XMod.class.getSimpleName());
/* 18 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 19 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 23 */     card.cost = card.costForTurn = -1;
/* 24 */     InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 29 */     return ((card.cost == 0 || card.cost == 1) && isNormalCard(card) && cardCheck(card, c -> doesntUpgradeCost()));
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
/* 49 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 54 */     int effect = card.energyOnUse;
/*    */     
/* 56 */     if (AbstractDungeon.player.hasRelic("Chemical X")) {
/* 57 */       effect += 2;
/* 58 */       AbstractDungeon.player.getRelic("Chemical X").flash();
/*    */     } 
/*    */     
/* 61 */     for (int i = 0; i < effect; i++) {
/* 62 */       card.use(AbstractDungeon.player, (target instanceof AbstractMonster) ? (AbstractMonster)target : null);
/*    */     }
/* 64 */     if (!card.freeToPlayOnce) {
/* 65 */       AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 71 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 76 */     return (AbstractCardModifier)new XMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 81 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\XMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */