/*    */ package Naijiumod.action;
/*    */ 
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ 
/*    */ public class ImmediateExhaustCardAction
/*    */   extends AbstractGameAction {
/*    */   private final AbstractCard toExhaust;
/*    */   
/*    */   public ImmediateExhaustCardAction(AbstractCard toExhaust) {
/* 12 */     this.actionType = ActionType.CARD_MANIPULATION;
/* 13 */     this.toExhaust = toExhaust;
/*    */   }
/*    */   
/*    */   public void update() {
/* 17 */     boolean found = false;
/* 18 */     for (AbstractCard card : AbstractDungeon.player.hand.group) {
/*    */       
/* 20 */       if (card == this.toExhaust) {
/* 21 */         found = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 25 */     if (found) {
/* 26 */       AbstractDungeon.player.hand.moveToExhaustPile(this.toExhaust);
/* 27 */       this.isDone = true;
/*    */       return;
/*    */     } 
/* 30 */     for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
/*    */       
/* 32 */       if (card == this.toExhaust) {
/* 33 */         found = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 37 */     if (found) {
/* 38 */       AbstractDungeon.player.drawPile.moveToExhaustPile(this.toExhaust);
/* 39 */       this.isDone = true;
/*    */       return;
/*    */     } 
/* 42 */     for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
/*    */       
/* 44 */       if (card == this.toExhaust) {
/* 45 */         found = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 49 */     if (found) {
/* 50 */       AbstractDungeon.player.discardPile.moveToExhaustPile(this.toExhaust);
/* 51 */       this.isDone = true;
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     this.isDone = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\actions\ImmediateExhaustCardAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */