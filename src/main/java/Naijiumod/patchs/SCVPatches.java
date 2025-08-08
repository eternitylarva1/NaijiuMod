/*     */ package Naijiumod.patchs;

/*     */ import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.ItemMod;
import Naijiumod.helpers.ModHelper;
import Naijiumod.hook.LoadMySpireMod;
import basemod.abstracts.AbstractCardModifier;
/*     */ import basemod.helpers.CardModifierManager;
/*     */ import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.DisplayRunInfo;
import com.badlogic.gdx.graphics.Color;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.evacipated.cardcrawl.mod.stslib.ui.MultiUpgradeTree;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.cards.CardGroup;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
/*     */ import com.megacrit.cardcrawl.helpers.FontHelper;
/*     */ import com.megacrit.cardcrawl.helpers.Hitbox;
/*     */ import com.megacrit.cardcrawl.helpers.ImageMaster;
/*     */ import com.megacrit.cardcrawl.helpers.MathHelper;
/*     */ import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
/*     */ import com.megacrit.cardcrawl.helpers.input.InputHelper;
/*     */ import com.megacrit.cardcrawl.localization.UIStrings;
/*     */ import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
/*     */ import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class SCVPatches {
/*  32 */   private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("SCVScreen"));
/*  33 */   public static final String[] TEXT = uiStrings.TEXT;
/*  34 */   public static final Hitbox augmentHitbox = new Hitbox(320.0F * Settings.scale, 80.0F * Settings.scale);
/*     */   public static boolean viewingAugments = false;
/*  36 */   public static final CardGroup cardsToRender = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
/*  37 */   public static final PreviewScreen screen = new PreviewScreen();
/*     */   
/*     */   @SpirePatch2(clz = SingleCardViewPopup.class, method = "update")
/*     */   public static class UpdatePatch {
/*     */     @SpirePostfixPatch
/*     */     public static void updateTime(SingleCardViewPopup __instance, AbstractCard ___card) {
/*  43 */       if (SCVPatches.viewingAugments) {
/*  44 */         SCVPatches.screen.update();
/*     */       }
/*  46 */       SCVPatches.augmentHitbox.update();
/*  47 */       if (SCVPatches.augmentHitbox.hovered && InputHelper.justClickedLeft) {
/*  48 */         SCVPatches.augmentHitbox.clickStarted = true;
/*     */       }
/*     */       
/*  51 */       if (SCVPatches.augmentHitbox.clicked || CInputActionSet.proceed.isJustPressed()) {
/*  52 */         CInputActionSet.proceed.unpress();
/*  53 */         SCVPatches.augmentHitbox.clicked = false;
/*  54 */         SCVPatches.viewingAugments = !SCVPatches.viewingAugments;
/*  55 */         SCVPatches.cardsToRender.clear();
/*  56 */         if (SCVPatches.viewingAugments) {
/*  57 */           ArrayList<AbstractAugment> validAugments = ItemMod.getModifiers();
/*  58 */           Collections.sort(validAugments, Comparator.comparing(o -> o.identifier(null)));
/*  59 */           for (AbstractAugment a : validAugments) {
/*  60 */             AbstractCard copy = ___card.makeStatEquivalentCopy();
/*  61 */             CardModifierManager.addModifier(copy, a.makeCopy());
/*  62 */             copy.targetDrawScale = 0.75F;
/*  63 */             SCVPatches.cardsToRender.addToTop(copy);
/*     */           } 
/*  65 */           SCVPatches.screen.calculateScrollBounds();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
/*     */   public static class RenderPatch {
/*     */     @SpirePostfixPatch
/*     */     public static void renderTime(SingleCardViewPopup __instance, SpriteBatch sb) {
/*  75 */       if (SCVPatches.viewingAugments) {
/*  76 */         SCVPatches.screen.render(sb);
/*     */       }
/*     */       
/*  79 */       FontHelper.cardTitleFont.getData().setScale(1.0F);
/*  80 */       sb.setColor(Color.WHITE);
/*  81 */       sb.draw(ImageMaster.CHECKBOX, SCVPatches.augmentHitbox.cX - 120.0F * Settings.scale - 32.0F, SCVPatches.augmentHitbox.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
/*  82 */       if (SCVPatches.augmentHitbox.hovered) {
/*  83 */         FontHelper.renderFont(sb, FontHelper.cardTitleFont, SCVPatches.TEXT[0], SCVPatches.augmentHitbox.cX - 85.0F * Settings.scale, SCVPatches.augmentHitbox.cY + 10.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
/*     */       } else {
/*  85 */         FontHelper.renderFont(sb, FontHelper.cardTitleFont, SCVPatches.TEXT[0], SCVPatches.augmentHitbox.cX - 85.0F * Settings.scale, SCVPatches.augmentHitbox.cY + 10.0F * Settings.scale, Settings.GOLD_COLOR);
/*     */       } 
/*     */       
/*  88 */       if (SCVPatches.viewingAugments) {
/*  89 */         sb.setColor(Color.WHITE);
/*  90 */         sb.draw(ImageMaster.TICK, SCVPatches.augmentHitbox.cX - 120.0F * Settings.scale - 32.0F, SCVPatches.augmentHitbox.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
/*     */       } 
/*     */       
/*  93 */       SCVPatches.augmentHitbox.render(sb);
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatches2({@SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = {AbstractCard.class}), @SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = {AbstractCard.class, CardGroup.class})})
/*     */   public static class OpenPatch
/*     */   {
/*     */     @SpirePostfixPatch
/*     */     public static void openTime(SingleCardViewPopup __instance) {
/* 102 */       SCVPatches.augmentHitbox.move(155.0F * Settings.scale, 70.0F * Settings.scale);
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = SingleCardViewPopup.class, method = "close")
/*     */   public static class ClosePatch {
/*     */     @SpirePostfixPatch
/*     */     public static void closeTime(SingleCardViewPopup __instance) {
/* 110 */       SCVPatches.viewingAugments = false;
/* 111 */       SCVPatches.cardsToRender.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch2(clz = SingleCardViewPopup.class, method = "updateInput")
/*     */   public static class UpdateInputPatch {
/*     */     @SpirePrefixPatch
/*     */     public static SpireReturn<?> myButtonStuff(SingleCardViewPopup __instance) {
/* 119 */       if (InputHelper.justClickedLeft && (
/* 120 */         SCVPatches.augmentHitbox.hovered || SCVPatches.viewingAugments)) {
/* 121 */         return SpireReturn.Return();
/*     */       }
/*     */       
/* 124 */       return SpireReturn.Continue();
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTips")
/*     */   public static class TipsBeGone {
/*     */     @SpirePrefixPatch
/*     */     public static SpireReturn<?> stop(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
/* 132 */       if (SCVPatches.viewingAugments) {
/* 133 */         return SpireReturn.Return();
/*     */       }
/* 135 */       return SpireReturn.Continue();
/*     */     }
/*     */   }
/*     */   
/*     */   @SpirePatches2({@SpirePatch2(clz = MultiUpgradeTree.class, method = "update"), @SpirePatch2(clz = MultiUpgradeTree.class, method = "render")})
/*     */   public static class ClobberTime
/*     */   {
/*     */     @SpirePrefixPatch
/*     */     public static SpireReturn<?> ceaseAndDesist() {
/* 144 */       if (SCVPatches.viewingAugments) {
/* 145 */         return SpireReturn.Return();
/*     */       }
/* 147 */       return SpireReturn.Continue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PreviewScreen implements ScrollBarListener {
/* 152 */     private static final int CARDS_PER_LINE = (int)(Settings.WIDTH / (AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X * 3.0F));
/*     */     private static float drawStartX;
/* 154 */     private static final float drawStartY = Settings.HEIGHT * 0.8F;
/* 155 */     private static final float padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
/* 156 */     private static final float padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
/*     */     private boolean grabbedScreen = false;
/* 158 */     private float grabStartY = 0.0F;
/* 159 */     private float currentDiffY = 0.0F;
/*     */     private float scrollLowerBound;
/*     */     private float scrollUpperBound;
/*     */     private boolean justSorted;
/*     */     public AbstractCard hoveredCard;
/*     */     private ScrollBar scrollBar;
/*     */     
/*     */     public PreviewScreen() {
/* 167 */       this.scrollBar = new ScrollBar(this);
/* 168 */       this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
/* 169 */       this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
/*     */       
/* 171 */       drawStartX = Settings.WIDTH;
/* 172 */       drawStartX -= (CARDS_PER_LINE - 0) * AbstractCard.IMG_WIDTH * 0.75F;
/* 173 */       drawStartX -= (CARDS_PER_LINE - 1) * Settings.CARD_VIEW_PAD_X;
/* 174 */       drawStartX /= 2.0F;
/* 175 */       drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
/*     */     }
/*     */     
/*     */     public void update() {
/* 179 */       boolean isScrollBarScrolling = this.scrollBar.update();
/* 180 */       if (!isScrollBarScrolling) {
/* 181 */         updateScrolling();
/*     */       }
/* 183 */       updateCards();
/*     */     }
/*     */     
/*     */     public void render(SpriteBatch sb) {
/* 187 */       sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
/* 188 */       sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT - 64.0F * Settings.scale);
/* 189 */       this.scrollBar.render(sb);
/* 190 */       renderCards(sb);
/*     */     }
/*     */     
/*     */     private void updateCards() {
/* 194 */       this.hoveredCard = null;
/* 195 */       int lineNum = 0;
/* 196 */       ArrayList<AbstractCard> cards = SCVPatches.cardsToRender.group;
/*     */       
/* 198 */       for (int i = 0; i < cards.size(); i++) {
/* 199 */         int mod = i % CARDS_PER_LINE;
/* 200 */         if (mod == 0 && i != 0) {
/* 201 */           lineNum++;
/*     */         }
/*     */         
/* 204 */         ((AbstractCard)cards.get(i)).target_x = drawStartX + mod * padX;
/* 205 */         ((AbstractCard)cards.get(i)).target_y = drawStartY + this.currentDiffY - lineNum * padY;
/* 206 */         ((AbstractCard)cards.get(i)).update();
/* 207 */         ((AbstractCard)cards.get(i)).updateHoverLogic();
/* 208 */         if (((AbstractCard)cards.get(i)).hb.hovered) {
/* 209 */           this.hoveredCard = cards.get(i);
/*     */         }
/*     */       } 
/*     */       
/* 213 */       if (this.justSorted) {
        /*     */
        /* 215 */
        AbstractCard c;
        for (Iterator<AbstractCard> var5 = cards.iterator(); var5.hasNext(); c.current_y = c.target_y) {
            /* 216 */
            c = var5.next();
            /* 217 */
            c.current_x = c.target_x;
            /*     */
        }
        /*     */
        /* 220 */
        this.justSorted = false;
        /*     */
    }
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderCards(SpriteBatch sb) {
/* 226 */       for (AbstractCard c : SCVPatches.cardsToRender.group) {
/* 227 */         for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
/* 228 */           String str = m.identifier(c);
/*     */         }
/*     */       } 
/* 231 */       SCVPatches.cardsToRender.renderInLibrary(sb);
/* 232 */       SCVPatches.cardsToRender.renderTip(sb);
/* 233 */       if (this.hoveredCard != null) {
/* 234 */         this.hoveredCard.renderHoverShadow(sb);
/* 235 */         this.hoveredCard.renderInLibrary(sb);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void updateScrolling() {
/* 240 */       int y = InputHelper.mY;
/* 241 */       if (!this.grabbedScreen) {
/* 242 */         if (InputHelper.scrolledDown) {
/* 243 */           this.currentDiffY += Settings.SCROLL_SPEED;
/* 244 */         } else if (InputHelper.scrolledUp) {
/* 245 */           this.currentDiffY -= Settings.SCROLL_SPEED;
/*     */         } 
/*     */         
/* 248 */         if (InputHelper.justClickedLeft) {
/* 249 */           this.grabbedScreen = true;
/* 250 */           this.grabStartY = y - this.currentDiffY;
/*     */         } 
/* 252 */       } else if (InputHelper.isMouseDown) {
/* 253 */         this.currentDiffY = y - this.grabStartY;
/*     */       } else {
/* 255 */         this.grabbedScreen = false;
/*     */       } 
/*     */       
/* 258 */       resetScrolling();
/* 259 */       updateBarPosition();
/*     */     }
/*     */     
/*     */     public void calculateScrollBounds() {
/* 263 */       int size = SCVPatches.cardsToRender.size();
/* 264 */       int scrollTmp = 0;
/* 265 */       if (size > CARDS_PER_LINE * 2) {
/* 266 */         scrollTmp = size / CARDS_PER_LINE - 2;
/* 267 */         if (size % CARDS_PER_LINE != 0) {
/* 268 */           scrollTmp++;
/*     */         }
/*     */         
/* 271 */         this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * padY;
/*     */       } else {
/* 273 */         this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void resetScrolling() {
/* 279 */       if (this.currentDiffY < this.scrollLowerBound) {
/* 280 */         this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
/* 281 */       } else if (this.currentDiffY > this.scrollUpperBound) {
/* 282 */         this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void scrolledUsingBar(float newPercent) {
/* 288 */       this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
/* 289 */       updateBarPosition();
/*     */     }
/*     */     
/*     */     private void updateBarPosition() {
/* 293 */       float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
/* 294 */       this.scrollBar.parentScrolledToPercent(percent);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\patches\SCVPatches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */