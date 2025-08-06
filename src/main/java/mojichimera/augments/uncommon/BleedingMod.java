package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class BleedingMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(BleedingMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float EXTRA_MULTIPLIER = 0.20F;
    private static final int PERCENT = 20;
    private static int damagedTimes = 0;

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * getMultiplier();
        return damage;
    }

    @Override
    public void onDamaged(AbstractCard c) {
        damagedTimes++;
    }

    private float getMultiplier() {
        if (!MojiHelper.isInCombat())
            return 1.0F;
        return 1.0F + (EXTRA_MULTIPLIER * damagedTimes);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isAttack(card);
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new BleedingMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
