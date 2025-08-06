package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

public class AccursedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(AccursedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int COST = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card) {
        if (!MojiHelper.isInCombat()) {
            return false;
        }

        boolean hasCurse = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                hasCurse = true;
                break;
            }
        }

        if (hasCurse) {
            card.cost -= COST;
            if (card.cost < 0) {
                card.cost = 0;
            }
            card.costForTurn = card.cost;
            card.isCostModified = true;
        }

        return hasCurse;
    }

    @Override
    public void onCreatedMidCombat(AbstractCard card) {
        atBattleStartPreDraw(card);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return hasACurse()
                && card.cost > 0
                && AugmentHelper.isNormal(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0]));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new AccursedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
