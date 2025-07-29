/*    */ package Naijiumod.cardModifier;

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.LoseHPAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.SFXAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class HemoMod extends AbstractAugment {
/* 17 */   public static final String ID = ModHelper.makePath(HemoMod.class.getSimpleName());
/* 18 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 19 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private static final int LOSS = 2;
/*    */ 
/*    */   
/*    */   public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 25 */     if (card.baseDamage > 0) {
/* 26 */       return damage * 1.5F;
/*    */     }
/* 28 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyBaseBlock(float block, AbstractCard card) {
/* 33 */     if (card.baseBlock > 0) {
/* 34 */       return block * 1.5F;
/*    */     }
/* 36 */     return block;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 41 */     return (card.rarity != AbstractCard.CardRarity.BASIC && card.cost != -2 && (card.baseDamage > 0 || card.baseBlock > 0));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 46 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 51 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 56 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 61 */     return insertBeforeText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(2) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 66 */     addToTop((AbstractGameAction)new LoseHPAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, 2));
/* 67 */     addToTop((AbstractGameAction)new SFXAction("BLOOD_SPLAT", 0.8F));
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 72 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 77 */     return (AbstractCardModifier)new HemoMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 82 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\HemoMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */