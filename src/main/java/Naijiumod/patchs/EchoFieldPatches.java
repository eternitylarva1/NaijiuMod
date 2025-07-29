/*    */ package Naijiumod.patchs;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.*;
/*    */
/*    */
/*    */
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import javassist.CtBehavior;
/*    */ 
/*    */ public class EchoFieldPatches {
/*    */   @SpirePatch(clz = AbstractCard.class, method = "<class>")
/*    */   public static class EchoFields {
/* 16 */     public static SpireField<Integer> echo = new SpireField(() -> Integer.valueOf(0));
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
/*    */   public static class MakeStatEquivalentCopy {
/*    */     public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
/* 22 */       EchoFields.echo.set(result, EchoFields.echo.get(self));
/* 23 */       return result;
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
/*    */   public static class PlayExtraCopies {
/*    */     @SpireInsertPatch(locator = Locator.class)
/*    */     public static void withoutInfiniteLoopPls(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
/* 31 */       if (((Integer) EchoFields.echo.get(c)).intValue() > 0)
/* 32 */         for (int i = 0; i < ((Integer) EchoFields.echo.get(c)).intValue(); i++) {
/* 33 */           AbstractCard tmp = c.makeSameInstanceOf();
/* 34 */           AbstractDungeon.player.limbo.addToBottom(tmp);
/* 35 */           tmp.current_x = c.current_x;
/* 36 */           tmp.current_y = c.current_y;
/* 37 */           tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
/* 38 */           tmp.target_y = Settings.HEIGHT / 2.0F;
/* 39 */           if (monster != null) {
/* 40 */             tmp.calculateCardDamage(monster);
/*    */           }
/* 42 */           tmp.purgeOnUse = true;
/*    */           
/* 44 */           EchoFields.echo.set(tmp, Integer.valueOf(0));
/* 45 */           AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, monster, c.energyOnUse, true, true), true);
/*    */         }  
/*    */     }
/*    */     
/*    */     private static class Locator
/*    */       extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
/* 52 */         Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
/* 53 */         return LineFinder.findInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\EchoFieldPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */