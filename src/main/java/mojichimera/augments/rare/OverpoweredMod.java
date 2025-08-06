package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class OverpoweredMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(OverpoweredMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 2.3333334F;
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new EtherealMod());
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
        card.costForTurn = ++card.cost;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * MULTIPLIER;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * MULTIPLIER;
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * MULTIPLIER;
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card)
                && AugmentHelper.hasStaticCost(card)
                && cardCheck(card, c -> notRetain(card))
                && AugmentHelper.isNormal(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

//    @Override
//    public String modifyDescription(String rawDescription, AbstractCard card) {
//        return insertBeforeText(rawDescription, CARD_TEXT[0]);
//    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new OverpoweredMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
