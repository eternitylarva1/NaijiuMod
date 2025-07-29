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
/*    */ 
/*    */ public class UpdateDynvarValuesPatches {
/*    */   @SpirePatch2(clz = AbstractPlayer.class, method = "bottledCardUpgradeCheck")
/*    */   public static class FixDynVarModsOnUpgrade {
/*    */     @SpirePostfixPatch
/*    */     public static void plz(AbstractCard c) {
/* 16 */       for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
/* 17 */         if (m instanceof AbstractAugment)
/* 18 */           ((AbstractAugment)m).updateDynvar(c); 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = AbstractCard.class, method = "resetAttributes")
/*    */   public static class FixDynVarModsWhenResettingVars
/*    */   {
/*    */     @SpirePostfixPatch
/*    */     public static void plz(AbstractCard __instance) {
/* 28 */       for (AbstractCardModifier m : CardModifierManager.modifiers(__instance)) {
/* 29 */         if (m instanceof AbstractAugment)
/* 30 */           ((AbstractAugment)m).updateDynvar(__instance); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\UpdateDynvarValuesPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */