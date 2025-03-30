package FanzhuanMod.patchs;
/*
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.purple.FearNoEvil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.LikeWaterPower;
import com.megacrit.cardcrawl.powers.watcher.RushdownPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

@SpirePatch(clz = RushdownPower.class, method = "onChangeStance")
public class RushDownPatch {

    @SpireInsertPatch(
            rloc=0
    )

    public static SpireReturn Insertfix(RushdownPower power, AbstractStance oldStance, AbstractStance newStance) {
        if (!oldStance.ID.equals(newStance.ID) && newStance.ID.equals("Calm")) {
            power.flash();
           AbstractDungeon.actionManager.addToBottom(new DrawCardAction(power.owner, power.amount));

        }
        return SpireReturn.Return();
    }

}
*/