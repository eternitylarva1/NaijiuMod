package mojichimera.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mojichimera.mojichimera;

public class CounterHelperMod extends AbstractCardModifier {
    public static String ID = mojichimera.makeID(CounterHelperMod.class.getSimpleName());

    public CounterHelperMod() {
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CounterHelperMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
