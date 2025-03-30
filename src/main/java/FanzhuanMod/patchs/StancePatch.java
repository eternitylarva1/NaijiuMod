package FanzhuanMod.patchs;

import FanzhuanMod.hook.MyModConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.purple.FearNoEvil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.LikeWaterPower;
import com.megacrit.cardcrawl.powers.watcher.RushdownPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;

@SpirePatch(clz = WrathStance.class, method = SpirePatch.CONSTRUCTOR)
public class StancePatch {

    @SpirePostfixPatch

    public static SpireReturn Insertfix(WrathStance stance) {
        if(MyModConfig.EnableCalm)
            stance.ID="Calm";

        return SpireReturn.Continue();
    }

}
