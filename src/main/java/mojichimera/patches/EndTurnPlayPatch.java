package mojichimera.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.rare.AfterlifeMod;
import mojichimera.cardmods.CounterHelperMod;

public class EndTurnPlayPatch {

    @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class EndTurnPlayPatch1 {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
//            if (AbstractDungeon.actionManager.turnHasEnded &&
//                    (CardModifierManager.hasModifier(__instance, AfterlifeMod.ID) || CardModifierManager.hasModifier(__instance, CounterHelperMod.ID))) {
//                return SpireReturn.Return(true);
//            } else {
//                return SpireReturn.Continue();
//            }
            if (AbstractDungeon.actionManager.turnHasEnded) {
                return SpireReturn.Return(true);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

}