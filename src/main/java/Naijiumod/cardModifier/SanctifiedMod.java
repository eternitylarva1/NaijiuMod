/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.helpers.CardBorderGlowManager;
/*    */ import com.badlogic.gdx.graphics.Color;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.UseCardAction;
/*    */ import com.megacrit.cardcrawl.actions.watcher.SanctityAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ 
/*    */ public class SanctifiedMod extends AbstractAugment {
/* 16 */   public static final String ID = ModHelper.makePath(SanctifiedMod.class.getSimpleName());
/* 17 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 18 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   
/*    */   private static final int CARDS = 2;
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 24 */     if (card instanceof com.megacrit.cardcrawl.cards.purple.Sanctity) {
/* 25 */       card.baseMagicNumber += 2;
/* 26 */       card.magicNumber = card.baseMagicNumber;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 32 */     return (card.cost > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 37 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 42 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 47 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 52 */     if (card instanceof com.megacrit.cardcrawl.cards.purple.Sanctity) {
/* 53 */       return rawDescription;
/*    */     }
/* 55 */     return insertAfterText(rawDescription, String.format(CARD_TEXT[0], new Object[] { Integer.valueOf(2) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
/* 60 */     if (!(card instanceof com.megacrit.cardcrawl.cards.purple.Sanctity)) {
/* 61 */       addToBot((AbstractGameAction)new SanctityAction(2));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 67 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 72 */     return (AbstractCardModifier)new SanctifiedMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 77 */     return ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public CardBorderGlowManager.GlowInfo getGlowInfo() {
/* 82 */     return new CardBorderGlowManager.GlowInfo()
/*    */       {
/*    */         public boolean test(AbstractCard abstractCard) {
/* 85 */           return (!(abstractCard instanceof com.megacrit.cardcrawl.cards.purple.Sanctity) && SanctifiedMod.this.hasThisMod(abstractCard) && SanctifiedMod.this.lastCardPlayedCheck(c -> (c.type == AbstractCard.CardType.SKILL)));
/*    */         }
/*    */ 
/*    */         
/*    */         public Color getColor(AbstractCard abstractCard) {
/* 90 */           return Color.GOLD.cpy();
/*    */         }
/*    */ 
/*    */         
/*    */         public String glowID() {
/* 95 */           return SanctifiedMod.ID + "Glow";
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\SanctifiedMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */