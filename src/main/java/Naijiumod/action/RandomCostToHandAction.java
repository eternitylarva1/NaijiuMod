/*    */ package Naijiumod.action;
/*    */ 
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.CardGroup;
/*    */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ 
/*    */ public class RandomCostToHandAction
/*    */   extends AbstractGameAction {
/*    */   private AbstractPlayer p;
/*    */   private int costTarget;
/*    */   
/*    */   public RandomCostToHandAction(int costToTarget) {
/* 17 */     this.p = AbstractDungeon.player;
/* 18 */     setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, this.amount);
/* 19 */     this.actionType = ActionType.CARD_MANIPULATION;
/* 20 */     this.costTarget = costToTarget;
/*    */   }
/*    */   
/*    */   public void update() {
/* 24 */     CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
/* 25 */     for (AbstractCard c : this.p.discardPile.group) {
/* 26 */       if (c.cost == this.costTarget || c.freeToPlayOnce) {
/* 27 */         tmp.addToTop(c);
/*    */       }
/*    */     } 
/* 30 */     if (!tmp.isEmpty()) {
/* 31 */       addToTop((AbstractGameAction)new DiscardToHandAction(tmp.getRandomCard(true)));
/* 32 */       tmp.clear();
/*    */     } 
/* 34 */     this.isDone = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\actions\RandomCostToHandAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */