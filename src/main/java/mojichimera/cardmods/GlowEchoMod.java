package mojichimera.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mojichimera.mojichimera;

public class GlowEchoMod extends AbstractCardModifier {
    public static String ID = mojichimera.makeID(GlowEchoMod.class.getSimpleName());

    public GlowEchoMod() {
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GlowEchoMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
