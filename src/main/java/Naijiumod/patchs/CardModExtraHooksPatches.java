/*    */ package Naijiumod.patchs;
/*    */ 
/*    */
/*    */ import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.helpers.CardModifierManager;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.rooms.AbstractRoom;
/*    */ 
/*    */ public class CardModExtraHooksPatches {
/*    */   @SpirePatch2(clz = AbstractPlayer.class, method = "updateCardsOnDamage")
/*    */   public static class UpdateOnDamage {
/*    */     @SpirePostfixPatch
/*    */     public static void update(AbstractPlayer __instance) {
/* 18 */       if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
/* 19 */         for (AbstractCard c : __instance.hand.group) {
/* 20 */           update(c);
/*    */         }
/*    */         
/* 23 */         for (AbstractCard c : __instance.drawPile.group) {
/* 24 */           update(c);
/*    */         }
/*    */         
/* 27 */         for (AbstractCard c : __instance.discardPile.group) {
/* 28 */           update(c);
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/*    */     public static void update(AbstractCard c) {
/* 34 */       for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
/* 35 */         if (m instanceof AbstractAugment)
/* 36 */           ((AbstractAugment)m).onDamaged(c); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\CardModExtraHooksPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */