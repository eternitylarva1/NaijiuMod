/*    */ package Naijiumod.action;

/*    */ import Naijiumod.utils.CalcHelper;
import com.badlogic.gdx.graphics.Color;
/*    */ import com.badlogic.gdx.math.MathUtils;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.DamageAction;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
/*    */ 
/*    */ public class AndTearAction extends AbstractGameAction {
/* 15 */   private Color color = Color.RED;
/* 16 */   private Color color2 = Color.GOLD;
/*    */   
/*    */   public AndTearAction(int baseDamage) {
/* 19 */     this.amount = baseDamage;
/* 20 */     this.source = (AbstractCreature)AbstractDungeon.player;
/* 21 */     this.actionType = ActionType.DAMAGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 26 */     AbstractMonster m = AbstractDungeon.getRandomMonster();
/* 27 */     if (m != null) {
/* 28 */       if (MathUtils.randomBoolean()) {
/* 29 */         CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
/*    */       } else {
/* 31 */         CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 36 */       if (MathUtils.randomBoolean()) {
/* 37 */         float baseAngle = 135.0F;
/* 38 */         AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX - 45.0F, m.hb.cY + 45.0F, -150.0F, -150.0F, baseAngle + MathUtils.random(-10.0F, 10.0F), this.color, this.color2));
/* 39 */         AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX, m.hb.cY, -150.0F, -150.0F, baseAngle + MathUtils.random(-10.0F, 10.0F), this.color, this.color2));
/* 40 */         AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX + 45.0F, m.hb.cY - 45.0F, -150.0F, -150.0F, baseAngle + MathUtils.random(-10.0F, 10.0F), this.color, this.color2));
/*    */       } else {
/* 42 */         float baseAngle = -135.0F;
/* 43 */         AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX - 45.0F, m.hb.cY - 45.0F, 150.0F, -150.0F, baseAngle + MathUtils.random(-10.0F, 10.0F), this.color, this.color2));
/* 44 */         AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX, m.hb.cY, 150.0F, -150.0F, baseAngle + MathUtils.random(-10.0F, 10.0F), this.color, this.color2));
/* 45 */         AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX + 40.0F, m.hb.cY + 40.0F, 150.0F, -150.0F, baseAngle + MathUtils.random(-10.0F, 10.0F), this.color, this.color2));
/*    */       } 
/*    */       
/* 48 */       addToTop((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)AbstractDungeon.player, CalcHelper.calculateCardDamage(this.amount, m), DamageInfo.DamageType.NORMAL), AttackEffect.NONE));
/*    */     } 
/* 50 */     this.isDone = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\actions\AndTearAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */