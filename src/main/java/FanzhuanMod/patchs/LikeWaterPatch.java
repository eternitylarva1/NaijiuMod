package FanzhuanMod.patchs;
/*
import FanzhuanMod.utils.Invoker;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.LikeWaterPower;
import com.megacrit.cardcrawl.powers.watcher.RushdownPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;

@SpirePatch(clz = LikeWaterPower.class, method = "atEndOfTurnPreEndTurnCards")
public class LikeWaterPatch {

    @SpireInsertPatch(
            rloc=0
    )

    public static SpireReturn Insertfix(LikeWaterPower power, boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = (AbstractPlayer)power.owner;
            if (p.stance.ID.equals(WrathStance.STANCE_ID)) {
                power.flash();
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(power.owner, power.owner, power.amount));
            }
        }
        return SpireReturn.Return();
    }

}
*/