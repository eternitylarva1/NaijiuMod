/*    */ package Naijiumod.cardModifier;

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.defect.ChannelAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;

/*    */
/*    */ public class MeteorMod extends AbstractAugment {
/* 16 */   public static final String ID = ModHelper.makePath(MeteorMod.class.getSimpleName());
/* 17 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 18 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private int increase;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 24 */     this.increase = 5 - card.cost;
/* 25 */     card.cost = 5;
/* 26 */     card.costForTurn = 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 31 */     return damage * (1.0F + 0.25F * this.increase);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 36 */     return (allowOrbMods() && card.rarity != AbstractCard.CardRarity.BASIC && card.cost >= 1 && card.cost <= 4 && card.baseDamage > 0 && card.type == AbstractCard.CardType.ATTACK && cardCheck(card, c -> doesntUpgradeCost()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 41 */     for (int i = 0; i < this.increase; i++) {
/* 42 */       addToBot((AbstractGameAction)new ChannelAction((AbstractOrb)new Plasma()));
/*    */     }
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
/* 63 */     return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(this.increase) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 68 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 73 */     return (AbstractCardModifier)new MeteorMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 78 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\MeteorMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */