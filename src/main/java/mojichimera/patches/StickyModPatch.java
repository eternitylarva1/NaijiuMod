package mojichimera.patches;


import Naijiumod.cardModifier.StickyMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class StickyModPatch {

    @SpirePatch(clz = StickyMod.class, method = "validCard")
    public static class StickyModPrefixPatch {
        public static SpireReturn<Boolean> Prefix(StickyMod __instance, AbstractCard card) {
            if (card.isEthereal) {
                return SpireReturn.Return(false);
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
