/*    */ package Naijiumod.card;

/*    */ import Naijiumod.helpers.ModHelper;
import basemod.abstracts.CustomCard;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class DummyCard extends CustomCard {
/*  9 */   public static final String ID = ModHelper.makePath("DummyCard");
/*    */   
/*    */   public DummyCard() {
/* 12 */     super(ID, "Name", "images/cards/locked_attack.png", 1, "Description", CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL);
/* 13 */     this.baseDamage = this.damage = 1;
/* 14 */     this.baseBlock = this.block = 1;
/* 15 */     this.baseMagicNumber = this.magicNumber = 1;
/*    */   }
/*    */   
/*    */   public void setMultiDamage(boolean var) {
/* 19 */     this.isMultiDamage = var;
/*    */   }
/*    */   
/*    */   public void upgrade() {}
/*    */   
/*    */   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cards\DummyCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */