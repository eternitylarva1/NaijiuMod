/*    */ package Naijiumod.patchs;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.*;
/*    */
/*    */
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

/*    */
/*    */ public class CantUpgradeFieldPatches {
/*    */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*    */   public static class CantUpgradeField {
/* 10 */     public static SpireField<Boolean> preventUpgrades = new SpireField(() -> Boolean.valueOf(false));
/*    */   }
/*    */   
/*    */   public static boolean cantUpgradeCheck(AbstractCard card) {
/* 14 */     return ((Boolean)CantUpgradeField.preventUpgrades.get(card)).booleanValue();
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
/*    */   public static class MakeStatEquivalentCopy {
/*    */     public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
/* 20 */       CantUpgradeField.preventUpgrades.set(result, CantUpgradeField.preventUpgrades.get(self));
/* 21 */       return result;
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = AbstractCard.class, method = "canUpgrade")
/*    */   public static class BypassUpgradeLimit {
/*    */     @SpirePrefixPatch
/*    */     public static SpireReturn<?> plz(AbstractCard __instance) {
/* 29 */       if (((Boolean) CantUpgradeField.preventUpgrades.get(__instance)).booleanValue()) {
/* 30 */         return SpireReturn.Return(Boolean.valueOf(false));
/*    */       }
/* 32 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch2(clz = SingleCardViewPopup.class, method = "allowUpgradePreview")
/*    */   public static class RemoveUpgradeButton {
/*    */     @SpirePrefixPatch
/*    */     public static SpireReturn<?> plz(SingleCardViewPopup __instance, AbstractCard ___card) {
/* 40 */       if (((Boolean) CantUpgradeField.preventUpgrades.get(___card)).booleanValue()) {
/* 41 */         return SpireReturn.Return(Boolean.valueOf(false));
/*    */       }
/* 43 */       return SpireReturn.Continue();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\CantUpgradeFieldPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */