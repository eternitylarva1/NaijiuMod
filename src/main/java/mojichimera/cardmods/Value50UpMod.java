package mojichimera.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;

import static Naijiumod.cardModifier.AbstractAugment.cardCheck;
import static Naijiumod.cardModifier.AbstractAugment.doesntDowngradeMagic;


public class Value50UpMod extends AbstractCardModifier {
    public static final String ID = mojichimera.makeID(Value50UpMod.class.getSimpleName());
    private static final float MULTIPLIER = 1.5F;
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new Value50UpMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
