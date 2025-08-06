package mojichimera.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mojichimera.augments.rare.ForesightMod;
import mojichimera.util.MojiHelper;

/* original code
  private void endActionWithFollowUp() {
    this.isDone = true;
    if (this.followUpAction != null)
      addToTop(this.followUpAction);
  }
 */

public class ForesightDrawCardPatch {

    @SpirePatch(clz = DrawCardAction.class, method = "endActionWithFollowUp")
    public static class ForesightDrawCardPatch1 {
        @SpirePrefixPatch
        public static void Prefix(DrawCardAction __instance) {
            AbstractCard card = MojiHelper.getLastCardPlayedThisTurn();
            if (card != null && CardModifierManager.hasModifier(card, ForesightMod.ID)) {
                for (final AbstractCard c : __instance.drawnCards) {
                    c.setCostForTurn(Math.max(0, c.costForTurn - 1));
                }
            }
        }
    }

}