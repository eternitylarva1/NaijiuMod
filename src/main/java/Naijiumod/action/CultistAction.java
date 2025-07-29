/*    */ package Naijiumod.action;
/*    */ 
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.animations.TalkAction;
/*    */ import com.megacrit.cardcrawl.actions.animations.VFXAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
/*    */ import com.megacrit.cardcrawl.actions.utility.SFXAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ import com.megacrit.cardcrawl.powers.StrengthPower;
/*    */ import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
/*    */ import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
/*    */ 
/*    */ public class CultistAction extends AbstractGameAction {
/*    */   public void update() {
/* 19 */     if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2) {
/* 20 */       int amount = ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).cost;
/* 21 */       if (amount == -1) {
/* 22 */         amount = AbstractDungeon.player.energy.energyMaster;
/*    */       }
/* 24 */       addToTop((AbstractGameAction)new TalkAction(true, (CardCrawlGame.languagePack.getRelicStrings("CultistMask")).DESCRIPTIONS[1], 0.0F, 2.0F));
/* 25 */       if (amount > 0) {
/* 26 */         addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new StrengthPower((AbstractCreature)AbstractDungeon.player, amount), amount, true));
/*    */       }
/* 28 */       addToTop((AbstractGameAction)new VFXAction((AbstractCreature)AbstractDungeon.player, (AbstractGameEffect)new InflameEffect((AbstractCreature)AbstractDungeon.player), 0.0F));
/* 29 */       addToTop((AbstractGameAction)new SFXAction("VO_CULTIST_1A"));
/*    */     } 
/* 31 */     this.isDone = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\actions\CultistAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */