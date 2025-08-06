package mojichimera.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import mojichimera.mojichimera;
import org.apache.commons.lang3.StringUtils;

public class EchoedEtherealMod extends AbstractCardModifier {
    public static final String ID = mojichimera.makeID(EchoedEtherealMod.class.getSimpleName());

    public EchoedEtherealMod() {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " " + StringUtils.capitalize(GameDictionary.ETHEREAL.NAMES[0]) + (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EchoedEtherealMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
