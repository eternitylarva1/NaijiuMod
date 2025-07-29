/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.helpers.CardModifierManager;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ 
/*    */ public class FlatBaseDamageMod extends AbstractCardModifier {
/*  9 */   public static final String ID = ModHelper.makePath(FlatBaseDamageMod.class.getSimpleName());
/*    */   int amount;
/*    */   
/*    */   public FlatBaseDamageMod(int amount) {
/* 13 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInitialApplication(AbstractCard card) {
/* 18 */     card.baseDamage += this.amount;
/* 19 */     card.damage = card.baseDamage;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApply(AbstractCard card) {
/* 24 */     if (CardModifierManager.hasModifier(card, identifier(card))) {
/* 25 */       FlatBaseDamageMod m = (FlatBaseDamageMod) CardModifierManager.getModifiers(card, identifier(card)).get(0);
/* 26 */       m.amount += this.amount;
/* 27 */       card.baseDamage += this.amount;
/* 28 */       card.damage = card.baseDamage;
/* 29 */       return false;
/*    */     } 
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 36 */     return ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 41 */     return new FlatBaseDamageMod(this.amount);
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmod\\util\FlatBaseDamageMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */