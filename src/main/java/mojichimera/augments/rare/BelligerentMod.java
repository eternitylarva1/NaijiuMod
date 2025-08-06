package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class BelligerentMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(BelligerentMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 25;
    private static final float MULTIPLIER = 1.25F;

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesDamage(card, 4)
                && AugmentHelper.isAttack(card)
                && card.target == AbstractCard.CardTarget.ENEMY
                && AugmentHelper.isPlayable(card);
    }

    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (target != null && target.getIntentBaseDmg() >= 0) {
            return damage * MULTIPLIER;
        }
        return damage;
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new BelligerentMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}