//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Naijiumod.ui;

import Naijiumod.cardModifier.ItemMod;
import Naijiumod.helpers.ModHelper;
import Naijiumod.hook.LoadMySpireMod;
import Naijiumod.hook.MyModConfig;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;




public class FumoEffect extends AbstractGameEffect {

    public static final String[] TEXT;
    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor;

    public FumoEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }



        if (!AbstractDungeon.isScreenUp && ! LoadMySpireMod.gridCardSelectScreen1.selectedCards.isEmpty()) {



            for(AbstractCard c :   LoadMySpireMod.gridCardSelectScreen1.selectedCards) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                ++CardCrawlGame.metricData.campfire_upgraded;
                CardCrawlGame.metricData.addCampfireChoiceData("SMITH", c.getMetricID());
             ItemMod.addRandomModifier(c);
             ItemMod itemMod = (ItemMod)CardModifierManager.getModifiers(c, ItemMod.ID).get(0);
                itemMod.damo=0;
                c.initializeDescription();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeSameInstanceOf()));
            }

              LoadMySpireMod.gridCardSelectScreen1.selectedCards.clear();
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        }

        if (this.duration < 1.0F && !this.openedScreen) {
            CardGroup cardGroup=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
                if(CardModifierManager.hasModifier(c, ItemMod.ID)){
                    ItemMod itemMod    = (ItemMod)(CardModifierManager.getModifiers(c, ItemMod.ID)).get(0);
                    if (itemMod.damo >= MyModConfig.Damo){
                        cardGroup.addToTop(c);
                    }
                }
            }
            this.openedScreen = true;
      
              LoadMySpireMod.gridCardSelectScreen1.open(cardGroup, 1, TEXT[3], true, true, true, false);
/*
            for(AbstractRelic r : AbstractDungeon.player.relics) {
                r.onSmith();
            }*/
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
                RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
                CampfireUI cm=rest.campfireUI;
                cm.hidden=false;
                cm.somethingSelected=false;
                cm.update();

            }
        }

    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        if (AbstractDungeon.screen == CurrentScreen.GRID) {

        }

    }

    public void dispose() {
    }

    static {
        String ID = ModHelper.makePath("FumoOption");
        TEXT =( CardCrawlGame.languagePack.getUIString(ID)).TEXT;
    }
}
