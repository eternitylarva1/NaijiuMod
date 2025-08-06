package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

public class BondMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(BondMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PER = 3;
    private static final int UPGRADE_PER = 2;
    private static final int EFFECT = 1;

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        return magic + EFFECT * (float) (Math.ceil((float) countPlayerBuff() / (card.upgraded ? UPGRADE_PER : PER)));
    }

    private int countPlayerBuff() {
        if (!MojiHelper.isInCombat())
            return 0;
        return (int) AbstractDungeon.player.powers.stream().filter(p -> p.type == AbstractPower.PowerType.BUFF).count();
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        card.initializeDescription();
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasMagic(card)
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], card.upgraded ? UPGRADE_PER : PER, EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new BondMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
