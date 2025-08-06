//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Naijiumod.ui;

import Naijiumod.helpers.ModHelper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class FumoOption extends AbstractCampfireOption {
    public static final String[] TEXT;

    public FumoOption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.updateUsability(active);
    }

    public void updateUsability(boolean canUse) {
        this.description = canUse ? TEXT[1] : TEXT[2];
        this.img = ImageMaster.loadImage("NaijiuModResources/images/smith1.png");
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new FumoEffect());
            this.usable = false;
        }

    }

    static {
        String ID = ModHelper.makePath("FumoOption");
        TEXT =( CardCrawlGame.languagePack.getUIString(ID)).TEXT;

    }
}
