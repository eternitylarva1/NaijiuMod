/*    */ package Naijiumod.power;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;
import Naijiumod.utils.FormatHelper;
import basemod.ReflectionHacks;
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.CardQueueItem;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.localization.PowerStrings;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
/*    */ import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class BombPower
/*    */   extends AbstractPower implements NonStackablePower {
/* 24 */   public static final String POWER_ID = ModHelper.makePath(BombPower.class.getSimpleName());
/* 25 */   private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
/*    */   private final AbstractCard card;
/*    */   private final int cards;
/* 28 */   private float flashTimer = -1.0F;
/*    */   private final ArrayList<AbstractGameEffect> array;
/*    */   
/*    */   public BombPower(AbstractCreature owner, int amount, int cards, AbstractCard card) {
/* 32 */     this.name = TEXT.NAME + card.name;
/* 33 */     this.ID = POWER_ID;
/* 34 */     this.amount = amount;
/* 35 */     this.cards = cards;
/* 36 */     this.owner = owner;
/* 37 */     this.card = card.makeStatEquivalentCopy();
/* 38 */     this.type = PowerType.BUFF;
/* 39 */     this.isTurnBased = false;
/* 40 */     loadRegion("the_bomb");
/* 41 */     updateDescription();
/* 42 */     this.array = (ArrayList<AbstractGameEffect>)ReflectionHacks.getPrivateInherited(this, BombPower.class, "effect");
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int slot) {
/* 47 */     super.update(slot);
/* 48 */     if (this.flashTimer != -1.0F) {
/* 49 */       this.flashTimer += Gdx.graphics.getDeltaTime();
/* 50 */       if (this.flashTimer > 1.0F) {
/* 51 */         this.array.add(new SilentGainPowerEffect(this));
/* 52 */         this.flashTimer = 0.0F;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateDescription() {
/* 59 */     if (this.amount == 1) {
/* 60 */       this.description = String.format(TEXT.DESCRIPTIONS[1], new Object[] { Integer.valueOf(this.cards), FormatHelper.prefixWords(this.card.name, "#y") });
/*    */     } else {
/* 62 */       this.description = String.format(TEXT.DESCRIPTIONS[0], new Object[] { Integer.valueOf(this.amount), Integer.valueOf(this.cards), FormatHelper.prefixWords(this.card.name, "#y") });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void atStartOfTurnPostDraw() {
/* 69 */     if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
/* 70 */       addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this, 1));
/* 71 */       if (this.amount == 1) {
/* 72 */         flash();
/* 73 */         addToBot(new AbstractGameAction()
/*    */             {
/*    */               public void update() {
/* 76 */                 for (int i = 0; i < BombPower.this.cards; i++) {
/* 77 */                   AbstractCard tmp = BombPower.this.card.makeSameInstanceOf();
/* 78 */                   AbstractDungeon.player.limbo.addToBottom(tmp);
/* 79 */                   tmp.current_x = BombPower.this.card.current_x;
/* 80 */                   tmp.current_y = BombPower.this.card.current_y;
/* 81 */                   tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
/* 82 */                   tmp.target_y = Settings.HEIGHT / 2.0F;
/*    */                   
/* 84 */                   tmp.purgeOnUse = true;
/* 85 */                   AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, BombPower.this.card.energyOnUse, true, true), true);
/*    */                 } 
/* 87 */                 this.isDone = true;
/*    */               }
/*    */             });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void atEndOfTurn(boolean isPlayer) {
/* 96 */     if (this.amount == 1)
/* 97 */       this.flashTimer = 1.0F; 
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\powers\BombPower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */