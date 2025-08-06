package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.patchs.CantUpgradeFieldPatches;
import Naijiumod.patchs.CantUpgradeFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class KnockoffMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(KnockoffMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public void onInitialApplication(AbstractCard card) {
        CantUpgradeFieldPatches.CantUpgradeField.preventUpgrades.set(card, Boolean.valueOf(true));
        card.cost--;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return !card.upgraded
                && card.canUpgrade()
                && doesntOverride(card, "canUpgrade", new Class[0])
                && AugmentHelper.hasStaticCost(card, 1);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new KnockoffMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}

