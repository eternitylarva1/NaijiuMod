/*    */ package Naijiumod.cardModifier;


/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

/*    */
/*    */ public class BlasphemousMod extends AbstractAugment {
/* 17 */   public static final String ID = ModHelper.makePath(BlasphemousMod.class.getSimpleName());
/* 18 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 19 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   boolean modMagic;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 25 */     if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber >= 1))) {
/* 26 */       this.modMagic = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 32 */     if (card.baseDamage > 0) {
/* 33 */       return damage * 5.0F;
/*    */     }
/* 35 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseBlock(float block, AbstractCard card) {
/* 40 */     if (card.baseBlock > 0) {
/* 41 */       return block * 5.0F;
/*    */     }
/* 43 */     return block;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseMagic(float magic, AbstractCard card) {
/* 48 */     if (this.modMagic) {
/* 49 */       return magic * 5.0F;
/*    */     }
/* 51 */     return magic;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 56 */     return (card.cost != -2 && (card.baseDamage > 0 || card.baseBlock > 0 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber >= 1))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 61 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 66 */     addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature) Wiz.adp(), (AbstractCreature)Wiz.adp(), (AbstractPower)new EndTurnDeathPower((AbstractCreature)Wiz.adp())));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 71 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 76 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 81 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 86 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 91 */     return (AbstractCardModifier)new BlasphemousMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 96 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\BlasphemousMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */