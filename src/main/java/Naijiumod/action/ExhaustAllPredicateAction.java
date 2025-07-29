/*    */ package Naijiumod.action;
/*    */ 
/*    */ import com.badlogic.gdx.utils.Predicate;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ 
/*    */ public class ExhaustAllPredicateAction extends AbstractGameAction {
/*    */   Predicate<AbstractCard> pred;
/*    */   
/*    */   public ExhaustAllPredicateAction(Predicate<AbstractCard> pred) {
/* 14 */     this.pred = pred;
/* 15 */     this.actionType = ActionType.WAIT;
/* 16 */     this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
/*    */   }
/*    */   
/*    */   public void update() {
/* 20 */     if (this.duration == this.startDuration) {
/* 21 */       for (AbstractCard c : AbstractDungeon.player.hand.group) {
/* 22 */         if (this.pred.evaluate(c)) {
/* 23 */           addToTop((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
/*    */         }
/*    */       } 
/* 26 */       this.isDone = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\actions\ExhaustAllPredicateAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */