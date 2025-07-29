/*    */ package Naijiumod.utils;
/*    */ 

/*    */ import Naijiumod.helpers.ModHelper;
import basemod.ReflectionHacks;
/*    */ import com.badlogic.gdx.graphics.Color;
/*    */ import com.badlogic.gdx.graphics.g2d.BitmapFont;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import com.megacrit.cardcrawl.helpers.FontHelper;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class AugmentPreviewCard extends AbstractCard {
/* 17 */   public static final String[] MY_TEXT = (CardCrawlGame.languagePack.getUIString(ModHelper.makePath("AugmentPreviewCard"))).TEXT;
/* 18 */   private final Color typeColor = new Color(0.35F, 0.35F, 0.35F, 0.0F);
/*    */   
/*    */   public AugmentPreviewCard(String name, String rawDescription) {
/* 21 */     super(ModHelper.makePath("PreviewCard"), name, "colorless/skill/insight", -2, rawDescription, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
/* 22 */     this.renderColor = (Color)ReflectionHacks.getPrivateInherited(this, AugmentPreviewCard.class, "renderColor");
/*    */   }
/*    */ 
/*    */   
/*    */   private final Color renderColor;
/*    */   
/*    */   public void upgrade() {}
/*    */   
/*    */   public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}
/*    */   
/*    */   @SpireOverride
/*    */   public void renderType(SpriteBatch sb) {
/* 34 */     String text = MY_TEXT[0];
/* 35 */     BitmapFont font = FontHelper.cardTypeFont;
/* 36 */     font.getData().setScale(this.drawScale);
/* 37 */     this.typeColor.a = this.renderColor.a;
/* 38 */     FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y - 22.0F * this.drawScale * Settings.scale, 0.0F, -1.0F * this.drawScale * Settings.scale, this.angle, false, this.typeColor);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractCard makeCopy() {
/* 43 */     return new AugmentPreviewCard(this.name, this.rawDescription);
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugment\\util\AugmentPreviewCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */