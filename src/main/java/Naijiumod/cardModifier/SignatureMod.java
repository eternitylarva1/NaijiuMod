/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class SignatureMod
/*    */   extends AbstractAugment {
/* 15 */   public static final String ID = ModHelper.makePath(SignatureMod.class.getSimpleName());
/* 16 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 17 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 21 */     return damage * 1.75F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 26 */     return (card.type == AbstractCard.CardType.ATTACK && card.baseDamage >= 2 && doesntOverride(card, "canUse", new Class[] { AbstractPlayer.class, AbstractMonster.class }));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 31 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 36 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 41 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 46 */     if (card instanceof com.megacrit.cardcrawl.cards.purple.SignatureMove) {
/* 47 */       return rawDescription;
/*    */     }
/* 49 */     return insertBeforeText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlayCard(AbstractCard card) {
/* 54 */     for (AbstractCard c : AbstractDungeon.player.hand.group) {
/* 55 */       if (c.type == AbstractCard.CardType.ATTACK && c != card) {
/* 56 */         return false;
/*    */       }
/*    */     } 
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 64 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 69 */     return (AbstractCardModifier)new SignatureMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 74 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\SignatureMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */