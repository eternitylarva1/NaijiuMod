/*    */ package Naijiumod.cardModifier;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;import basemod.abstracts.AbstractCardModifier;
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
/*    */ import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;
/*    */ 
/*    */ public class BerserkMod extends AbstractAugment {
/* 16 */   public static final String ID = ModHelper.makePath(BerserkMod.class.getSimpleName());
/* 17 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/* 18 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*    */   private static final float VFX_RATE = 0.1F;
/* 20 */   private float vfxTimer = 0.0F;
/*    */ 
/*    */   
/*    */   public boolean validCard(AbstractCard card) {
/* 24 */     return (card.baseDamage > 0 && card.type == AbstractCard.CardType.ATTACK);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 29 */     return TEXT[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 34 */     return TEXT[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAugmentDescription() {
/* 39 */     return TEXT[2];
/*    */   }
/*    */ 
/*    */   
/*    */   public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
/* 44 */     if (AbstractDungeon.player.isBloodied) {
/* 45 */       return damage * 1.5F;
/*    */     }
/* 47 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate(AbstractCard card) {
/* 52 */     if (CardCrawlGame.isInARun() && AbstractDungeon.player.isBloodied && AbstractDungeon.player.hand.contains(card)) {
/* 53 */       this.vfxTimer += Gdx.graphics.getDeltaTime();
/* 54 */       if (this.vfxTimer >= 0.1F) {
/* 55 */         this.vfxTimer = 0.0F;
/* 56 */         AbstractDungeon.effectsQueue.add(new FlameParticleEffect(card.current_x, card.current_y + card.hb.height / 3.0F));
/* 57 */         AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(card.current_x, card.current_y + card.hb.height / 3.0F));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 64 */     return insertAfterText(rawDescription, CARD_TEXT[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractAugment.AugmentRarity getModRarity() {
/* 69 */     return AbstractAugment.AugmentRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCardModifier makeCopy() {
/* 74 */     return (AbstractCardModifier)new BerserkMod();
/*    */   }
/*    */ 
/*    */   
/*    */   public String identifier(AbstractCard card) {
/* 79 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\BerserkMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */