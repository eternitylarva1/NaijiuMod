/*
package FanzhuanMod.patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.InnerPeaceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.RushdownPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;

@SpirePatch(clz = InnerPeaceAction.class, method = "update")
public class InnerPeacePatch {

    @SpireInsertPatch(
            rloc=0
    )

    public static SpireReturn Insertfix(InnerPeaceAction action, int amount) {
        if (AbstractDungeon.player.stance.ID.equals(WrathStance.STANCE_ID)) {
           AbstractDungeon.actionManager.addToBottom(new DrawCardAction(action.amount));
        } else {
           AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Calm"));
        }
        action.isDone = true;
        return SpireReturn.Return();
    }

}
*/