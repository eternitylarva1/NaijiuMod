package mojichimera.augments.special;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.damagemods.WallopIntentDamage;
import mojichimera.mojichimera;

public class AdaptiveMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(AdaptiveMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 100;
    private static final float MULTIPLIER = 2.0F;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.costForTurn = ++card.cost;
        DamageModifierManager.addModifier(card, new WallopIntentDamage());
    }


    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isAttack(card)
                && card.target == AbstractCard.CardTarget.ENEMY
                && AugmentHelper.hasStaticCost(card);
    }

    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (target != null && !(target.getIntentBaseDmg() >= 0)) {
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.SPECIAL; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new AdaptiveMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}