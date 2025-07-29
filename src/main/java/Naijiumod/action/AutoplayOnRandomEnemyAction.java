/*    */ package Naijiumod.action;
/*    */ 
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.CardQueueItem;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ 
/*    */ public class AutoplayOnRandomEnemyAction extends AbstractGameAction {
/*    */   AbstractCard card;
/*    */   
/*    */   public AutoplayOnRandomEnemyAction(AbstractCard card) {
/* 12 */     this.card = card;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 17 */     if (this.card != null && AbstractDungeon.actionManager.cardQueue.stream().noneMatch(i -> (i.card == this.card))) {
/* 18 */       this.card.targetAngle = 0.0F;
/* 19 */       AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this.card, AbstractDungeon.getRandomMonster()));
/*    */     } 
/*    */     
/* 22 */     this.isDone = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\actions\AutoplayOnRandomEnemyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */