/*    */ package Naijiumod.helpers;

/*    */ import Naijiumod.cardModifier.DynvarCarrier;
import basemod.BaseMod;
/*    */ import basemod.abstracts.AbstractCardModifier;
/*    */ import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardModifierManager;
/*    */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCustomDynamicVariable;
/*    */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCustomDynamicVariableCN;
/*    */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.SmithPreview;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import java.util.HashMap;
/*    */ import javassist.CannotCompileException;
/*    */ import javassist.expr.ExprEditor;
/*    */ import javassist.expr.MethodCall;

import static Naijiumod.helpers.ModHelper.makePath;

/*    */
/*    */ public class DynamicDynamicVariableManager extends DynamicVariable {
/* 20 */   public static DynamicDynamicVariableManager instance = new DynamicDynamicVariableManager();
/* 21 */   public static String workingKey = "";
/*    */ 
/*    */   
/*    */   public String key() {
/* 25 */     return makePath("Yn gvarManager");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified(AbstractCard card) {
/* 30 */     for (AbstractCardModifier m : CardModifierManager.modifiers(card)) {
/* 31 */       if (m instanceof DynvarCarrier && ((DynvarCarrier)m).key().equals(workingKey)) {
/* 32 */         return ((DynvarCarrier)m).modified(card);
/*    */       }
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int value(AbstractCard card) {
/* 40 */     for (AbstractCardModifier m : CardModifierManager.modifiers(card)) {
/* 41 */       if (m instanceof DynvarCarrier && ((DynvarCarrier)m).key().equals(workingKey)) {
/* 42 */         return ((DynvarCarrier)m).val(card);
/*    */       }
/*    */     } 
/* 45 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int baseValue(AbstractCard card) {
/* 50 */     for (AbstractCardModifier m : CardModifierManager.modifiers(card)) {
/* 51 */       if (m instanceof DynvarCarrier && ((DynvarCarrier)m).key().equals(workingKey)) {
/* 52 */         return ((DynvarCarrier)m).baseVal(card);
/*    */       }
/*    */     } 
/* 55 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean upgraded(AbstractCard card) {
/* 60 */     for (AbstractCardModifier m : CardModifierManager.modifiers(card)) {
/* 61 */       if (m instanceof DynvarCarrier && ((DynvarCarrier)m).key().equals(workingKey)) {
/* 62 */         return ((DynvarCarrier)m).upgraded(card);
/*    */       }
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public static void registerDynvarCarrier(DynvarCarrier dv) {
/* 69 */     BaseMod.cardDynamicVariableMap.put(dv.key(), instance);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SpirePatches2({@SpirePatch2(clz = SmithPreview.class, method = "ForEachDynamicVariable"), @SpirePatch2(clz = RenderCustomDynamicVariable.Inner.class, method = "subRenderDynamicVariable"), @SpirePatch2(clz = RenderCustomDynamicVariableCN.class, method = "Insert"), @SpirePatch2(clz = RenderCustomDynamicVariable.Inner.class, method = "subRenderDynamicVariable"), @SpirePatch2(clz = RenderCustomDynamicVariableCN.class, method = "Insert")})
/*    */   public static class GrabWorkingKey
/*    */   {
/*    */     @SpireInstrumentPatch
/*    */     public static ExprEditor patch() {
/* 80 */       return new ExprEditor()
/*    */         {
/*    */           public void edit(MethodCall m) throws CannotCompileException {
/* 83 */             if (m.getClassName().equals(HashMap.class.getName()) && m.getMethodName().equals("get")) {
/* 84 */               m.replace("{ $1 = " + GrabWorkingKey.class
/* 85 */                   .getName() + ".grabWorkingKey($1); $_ = $proceed($$);}");
/*    */             }
/*    */           }
/*    */         };
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Object grabWorkingKey(Object key) {
/* 94 */       if (key instanceof String) {
/* 95 */         DynamicDynamicVariableManager.workingKey = (String)key;
/*    */       }
/* 97 */       return key;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\dynvars\DynamicDynamicVariableManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */