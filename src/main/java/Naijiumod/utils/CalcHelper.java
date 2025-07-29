/*    */ package Naijiumod.utils;
/*    */ 

/*    */ import Naijiumod.card.DummyCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class CalcHelper {
/*  7 */   private static final DummyCard dummyCard = new DummyCard();
/*    */   
/*    */   public static int applyPowers(int damage) {
/* 10 */     dummyCard.setMultiDamage(false);
/* 11 */     dummyCard.baseDamage = damage;
/* 12 */     dummyCard.applyPowers();
/* 13 */     return dummyCard.damage;
/*    */   }
/*    */   
/*    */   public static int[] applyPowersMulti(int damage) {
/* 17 */     dummyCard.setMultiDamage(true);
/* 18 */     dummyCard.baseDamage = damage;
/* 19 */     dummyCard.applyPowers();
/* 20 */     return dummyCard.multiDamage;
/*    */   }
/*    */   
/*    */   public static int applyPowersToBlock(int block) {
/* 24 */     dummyCard.baseBlock = block;
/* 25 */     dummyCard.applyPowers();
/* 26 */     return dummyCard.block;
/*    */   }
/*    */   
/*    */   public static int calculateCardDamage(int damage, AbstractMonster mo) {
/* 30 */     dummyCard.setMultiDamage(false);
/* 31 */     dummyCard.baseDamage = damage;
/* 32 */     dummyCard.calculateCardDamage(mo);
/* 33 */     return dummyCard.damage;
/*    */   }
/*    */   
/*    */   public static int[] calculateCardDamageMulti(int damage) {
/* 37 */     dummyCard.setMultiDamage(true);
/* 38 */     dummyCard.baseDamage = damage;
/* 39 */     dummyCard.calculateCardDamage(null);
/* 40 */     return dummyCard.multiDamage;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugment\\util\CalcHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */