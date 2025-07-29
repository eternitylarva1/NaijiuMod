/*    */ package Naijiumod.patchs;
/*    */ 
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireField;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ 
/*    */ public class RolledModFieldPatches {
/*    */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*    */   public static class RolledModField {
/* 10 */     public static SpireField<Boolean> rolled = new SpireField(() -> Boolean.valueOf(false));
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
/*    */   public static class MakeStatEquivalentCopy {
/*    */     public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
/* 16 */       RolledModField.rolled.set(result, RolledModField.rolled.get(self));
/* 17 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\RolledModFieldPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */