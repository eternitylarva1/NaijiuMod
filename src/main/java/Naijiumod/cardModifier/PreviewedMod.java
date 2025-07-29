/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ 
/*    */ public class PreviewedMod extends AbstractCardModifier {
/*  8 */   public static final String ID = ModHelper.makePath(PreviewedMod.class.getSimpleName());
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 11 */     return ID;
/*    */   }
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 15 */     return new PreviewedMod();
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmod\\util\PreviewedMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */