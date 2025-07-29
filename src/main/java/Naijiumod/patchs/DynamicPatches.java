/*    */ package Naijiumod.patchs;
/*    */ import com.evacipated.cardcrawl.modthespire.Loader;
/*    */ import com.evacipated.cardcrawl.modthespire.ModInfo;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireRawPatch;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import java.io.File;
/*    */ import java.net.URISyntaxException;
import java.util.ArrayList;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
import javassist.*;
/*    */
/*    */
/*    */
import org.clapper.util.classutil.*;
/*    */
/*    */
/*    */

/*    */
/*    */ public class DynamicPatches {
/*    */   @SpirePatch(clz = CardCrawlGame.class, method = "<ctor>")
/*    */   public static class AbstractCardDynamicPatch {
/*    */     @SpireRawPatch
/*    */     public static void patch(CtBehavior ctBehavior) throws NotFoundException {
/* 21 */       ClassFinder finder = new ClassFinder();
/* 22 */       finder.add(new File(Loader.STS_JAR));
/*    */       
/* 24 */       for (ModInfo modInfo : Loader.MODINFOS) {
/* 25 */         if (modInfo.jarURL != null) {
/*    */           try {
/* 27 */             finder.add(new File(modInfo.jarURL.toURI()));
/* 28 */           } catch (URISyntaxException uRISyntaxException) {}
/*    */         }
/*    */       } 
/*    */       
/* 32 */       AndClassFilter andClassFilter = new AndClassFilter(new ClassFilter[] { (ClassFilter)new NotClassFilter((ClassFilter)new InterfaceOnlyClassFilter()), (ClassFilter)new ClassModifiersClassFilter(1), (ClassFilter)new OrClassFilter(new ClassFilter[] { (ClassFilter)new SubclassClassFilter(AbstractCard.class), (classInfo, classFinder) -> classInfo.getClassName().equals(AbstractCard.class.getName()) }) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 41 */       ArrayList<ClassInfo> foundClasses = new ArrayList<>();
/* 42 */       finder.findClasses(foundClasses, (ClassFilter)andClassFilter);
/*    */       
/* 44 */       for (ClassInfo classInfo : foundClasses) {
/* 45 */         CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());
/*    */         try {
/* 47 */           CtMethod[] methods = ctClass.getDeclaredMethods();
/* 48 */           for (CtMethod m : methods) {
/* 49 */             if (m.getName().equals("upgrade")) {
/* 50 */               m.insertBefore(InfiniteUpgradesPatches.class.getName() + ".infCheck($0);");
/* 51 */               m.insertAfter(OnUpgradePatches.class.getName() + ".onUpgrade($0);");
/* 52 */               m.insertBefore("if(" + CantUpgradeFieldPatches.class.getName() + ".cantUpgradeCheck($0)) {return;}");
/*    */             } 
/*    */           } 
/* 55 */         } catch (CannotCompileException cannotCompileException) {}
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\DynamicPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */