/*    */ package Naijiumod.patchs;
/*    */ 
/*    */
/*    */ import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.helpers.CardModifierManager;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ 
/*    */ public class CanPlayPatches {
/*    */   @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
/*    */   public static class CardModifierCanPlayCard {
/*    */     public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
/* 15 */       for (AbstractCard c : AbstractDungeon.player.hand.group) {
/* 16 */         for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
/* 17 */           if (m instanceof AbstractAugment &&
/* 18 */             !((AbstractAugment)m).betterCanPlay(c, __instance)) {
/* 19 */             return SpireReturn.Return(Boolean.valueOf(false));
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 24 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\CanPlayPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */