package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class EstablishedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(EstablishedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
//        card.selfRetain = true;
        CardModifierManager.addModifier(card, new RetainMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost > 0
                && AugmentHelper.isRetainValid(card);
    }

    @Override
    public void onRetained(AbstractCard card) {
        addToBot(new ReduceCostAction(card));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (rawDescription.contains(CARD_TEXT[0]) && !(card instanceof Meditate)) {
            if (rawDescription.contains(CARD_TEXT[2])) {
                return rawDescription.replace(CARD_TEXT[2], String.format(CARD_TEXT[3], EFFECT + 1));
            }
            return insertAfterText(rawDescription, String.format(CARD_TEXT[1], EFFECT));
//        }
//        return insertAfterText(insertBeforeText(rawDescription, CARD_TEXT[0]), String.format(CARD_TEXT[1], EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new EstablishedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
