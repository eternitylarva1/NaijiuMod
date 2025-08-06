package mojichimera.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mojichimera.util.OnManualDiscardSubscriber;

public class OnDiscardPatch {
    @SpirePatch(clz = AbstractCard.class, method = "triggerOnManualDiscard")
    public static class OnManualDiscardPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance) {
            for (AbstractCardModifier mod : CardModifierManager.modifiers(__instance)) {
                if (mod instanceof OnManualDiscardSubscriber) {
                    ((OnManualDiscardSubscriber) mod).onManualDiscard(__instance);
                }
            }
        }
    }

//    @SpirePatch(clz = AbstractCard.class, method = "onMoveToDiscard")
//    public static class OnMoveToDiscardPatch {
//        @SpirePostfixPatch
//        public static void Postfix(AbstractCard __instance) {
//            for (AbstractCardModifier mod : CardModifierManager.modifiers(__instance)) {
//                if (mod instanceof OnMoveToDiscardSubscriber) {
//                    ((OnMoveToDiscardSubscriber) mod).onMoveToDiscard(__instance);
//                }
//            }
//        }
//    }
}
