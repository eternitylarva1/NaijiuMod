package FanzhuanMod.patchs;
/*
import FanzhuanMod.cardModifier.CalmModifier;
import FanzhuanMod.cardModifier.RandomStanceModifier;
import FanzhuanMod.utils.Invoker;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.cards.purple.EmptyMind;
import com.megacrit.cardcrawl.cards.purple.LikeWater;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;

@SpirePatch(clz = ChangeStanceAction.class, method = "update")

public class ChangeStancePatch {

    @SpireInsertPatch(
            rloc=5
    )

    public static void Insertfix(ChangeStanceAction action, @ByRef AbstractStance[] ___newStance,String ___id) {
        if (___newStance[0] == null) {
            if (___id == null) {
                return;
            } else if (___id.equals("Calm")) {
                ___newStance[0] = AbstractStance.getStanceFromName("Wrath");
            } else if (___id.equals("Wrath")) {
                ___newStance[0] = AbstractStance.getStanceFromName("Calm");
            } else if (___id.equals("Neutral")) {
                String[] temp={"Calm","Wrath","Divinity"};
                ___newStance[0] = AbstractStance.getStanceFromName(temp[(int) AbstractDungeon.cardRandomRng.random(temp.length-1)]);
            }

        } else if (___newStance[0].ID == "Calm") {
            Invoker.setField(action, "id", "Calm");
            ___newStance[0] = AbstractStance.getStanceFromName("Calm");
        } else if (___newStance[0].ID == "Wrath") {
            Invoker.setField(action, "id", "Wrath");
            ___newStance[0] = AbstractStance.getStanceFromName("Wrath");
        } else if (___newStance[0].ID == "Neutral") {
            String[] temp={"Calm","Wrath","Divinity"};
            ___newStance[0] = AbstractStance.getStanceFromName(temp[(int) AbstractDungeon.cardRandomRng.random(temp.length-1)]);
        }

    }

}
*/