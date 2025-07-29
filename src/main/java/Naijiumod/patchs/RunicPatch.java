/*    */ package Naijiumod.patchs;

/*    */ import Naijiumod.power.RunicPower;
import basemod.abstracts.CustomMonster;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.*;
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.helpers.PowerTip;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import java.util.ArrayList;
/*    */ import javassist.CannotCompileException;
/*    */ import javassist.CtBehavior;
/*    */ import javassist.expr.ExprEditor;
/*    */ import javassist.expr.MethodCall;
/*    */ 
/*    */ public class RunicPatch {
/*    */   @SpirePatch(clz = AbstractMonster.class, method = "renderTip")
/*    */   public static class TipPatch {
/*    */     @SpireInsertPatch(locator = Locator.class)
/*    */     public static void Insert(AbstractMonster __instance, SpriteBatch sb, ArrayList<PowerTip> ___tips, PowerTip ___intentTip) {
/* 25 */       if (AbstractDungeon.player.hasPower(RunicPower.POWER_ID))
/* 26 */         ___tips.remove(___intentTip); 
/*    */     }
/*    */     
/*    */     public static class Locator extends SpireInsertLocator {
/*    */       public int[] Locate(CtBehavior ctBehavior) throws Exception {
/* 31 */         return LineFinder.findInOrder(ctBehavior, (Matcher)new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty"));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @SpirePatches({@SpirePatch(clz = AbstractMonster.class, method = "render"), @SpirePatch(clz = CustomMonster.class, method = "render")})
/*    */   public static class IntentPatch
/*    */   {
/*    */     @SpireInstrumentPatch
/*    */     public static ExprEditor Instrument() {
/* 41 */       return new ExprEditor()
/*    */         {
/*    */           public void edit(MethodCall m) throws CannotCompileException {
/* 44 */             if (m.getMethodName().equals("hasRelic"))
/* 45 */               m.replace("{$_ = $proceed($$) || com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hasPower(Naijiumod.power.RunicPower.POWER_ID);}");
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\RunicPatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */